package services;

import domain.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import repositories.NewsPaperRepository;
import security.Authority;

import javax.transaction.Transactional;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.validation.Validator;

@Service
@Transactional
public class NewsPaperService {
    // Managed repository -----------------------------------------------------

    @Autowired
    private NewsPaperRepository newsPaperRepository;

    // Supporting services ----------------------------------------------------

    @Autowired
    private UserService userService;

    @Autowired
    private AdministratorService administratorService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ConfigurationService configurationService;

    @Autowired
    private ActorService actorService;

    @Autowired
    private Validator validator;

    @Autowired
    private AgentService agentService;

    @Autowired
    private AdvertisementService advertisementService;

    @Autowired
    private VolumeService volumeService;

    @Autowired
    private SubscribeNewsPaperService subscribeNewsPaperService;

    @Autowired
    private SubscribeVolumeService subscribeVolumeService;

    // Constructors -----------------------------------------------------------

    public NewsPaperService() {
        super();
    }

    // Simple CRUD methods -----------------------------------------------------

    public NewsPaper create() {
        NewsPaper res;
        User publisher;
        Collection<Article> articles= new ArrayList<>();
        Collection<Advertisement> advertisements = new ArrayList<>();
        Collection<Volume> volumes = new ArrayList<>();

        publisher=userService.findByPrincipal();
        res= new NewsPaper();

        res.setPublisher(publisher);
        res.setArticles(articles);
        res.setSubscriptions(new ArrayList<SubscribeNewsPaper>());
        res.setAdvertisements(advertisements);
        res.setVolumes(volumes);
        return res;
    }

    public NewsPaper save(NewsPaper newsPaper){
        NewsPaper res= null;
        Assert.isTrue(checkByPrincipal(newsPaper) || checkByPrincipalCustomer() || checkByPrincipalAgent());
        if(isTabooNewsPaper(newsPaper)){
            newsPaper.setTaboo(true);
        }
        res= newsPaperRepository.save(newsPaper);
        return res;
    }

    public Collection<NewsPaper> findAll(){
        Collection<NewsPaper> res= null;
        res= this.newsPaperRepository.findAll();
        return  res;
    }

    public NewsPaper findOne(int newsPaperId){
        NewsPaper res= null;
        res= this.newsPaperRepository.findOne(newsPaperId);
        return  res;
    }
    public NewsPaper findOneToEdit(int newsPaperId){
        NewsPaper res= null;

        res= this.newsPaperRepository.findOne(newsPaperId);
        Assert.isTrue(!res.getPublished());
        Assert.isTrue(checkByPrincipal(res) || checkByPrincipalAdmin(res));
        return  res;
    }

    public void delete(NewsPaper newsPaper){
        Assert.notNull(newsPaper);
        Assert.isTrue(checkByPrincipalAdmin(newsPaper) || checkByPrincipal(newsPaper));
        Collection<SubscribeNewsPaper> subscriptions =newsPaper.getSubscriptions();
        if(subscriptions.size()>0) {
            for(SubscribeNewsPaper s : subscriptions){
                s.setCustomer(null);
                s.setNewsPaper(null);
                this.subscribeNewsPaperService.delete(s);
            }
        }
        Collection<Volume> volumes = newsPaper.getVolumes();
        for(Volume v : volumes){
            for(SubscribeVolume v1 : v.getSubscriptionVolumes()){
                v.getSubscriptionVolumes().remove(newsPaper);
                this.subscribeVolumeService.deleteCustomerVolume(v1);
            }
        }
        this.advertisementService.deleteAll(newsPaper);
        this.volumeService.delete(newsPaper);
        this.articleService.deleteAll(newsPaper.getArticles());
        this.newsPaperRepository.delete(newsPaper);
    }

    // Other business methods -------------------------------------------------

    public void unsuscribe(NewsPaper newsPaper){
        Customer customer = this.customerService.findByPrincipal();
        SubscribeNewsPaper subscription = findSubscriptionNewsPaperByCustomer(customer.getId(),newsPaper.getId());
        newsPaper.getSubscriptions().remove(subscription);
        customer.getSubscriptionsToNewspapers().remove(subscription);
        subscribeNewsPaperService.delete(subscription);

    }

    public void findOneToPublish(NewsPaper newsPaper){
        Collection<Article> articles= newsPaper.getArticles();
        for(Article a:articles){
            if(a.getFinalMode()){
                newsPaper.setPublished(true);
                newsPaper.setPublicationDate(new Date());
                a.setMoment(new Date());
            }else{
                newsPaper.setPublished(false);
            }
        }
    }

    public boolean checkByPrincipal(NewsPaper newsPaper) {
        Boolean res = null;
        User principal = null;

        res = false;
        principal = this.userService.findByPrincipal();

        if (newsPaper.getPublisher().equals(principal))
            res = true;

        return res;
    }

    public boolean checkByPrincipalCustomer() {
        Boolean res = false;
        Customer principal = null;
        principal= customerService.findByPrincipal();
        if(principal!=null) {
            Collection<Authority> authorities = principal.getUserAccount().getAuthorities();
            String authority = authorities.toArray()[0].toString();
            res = authority.equals("CUSTOMER");
        }

        return res;
    }

    public boolean checkByPrincipalAdmin(NewsPaper newsPaper){
        Boolean res= false;
        Administrator administrator = administratorService.findByPrincipal();
        if(administrator!=null) {
            Collection<Authority> authorities = administrator.getUserAccount().getAuthorities();
            String authority = authorities.toArray()[0].toString();
            res = authority.equals("ADMINISTRATOR");
        }
        return res;

    }

    public boolean checkByPrincipalAgent() {
        Boolean res = false;
        Agent principal = null;
        principal= agentService.findByPrincipal();
        if(principal!=null) {
            Collection<Authority> authorities = principal.getUserAccount().getAuthorities();
            String authority = authorities.toArray()[0].toString();
            res = authority.equals("AGENT");
        }

        return res;
    }

    public Collection<NewsPaper> findPublishedNewsPaper(){
        return newsPaperRepository.findPublishedNewsPaper();
    }

    public Collection<NewsPaper> findPublishedAndNotPrivateNewsPaper(){
        return newsPaperRepository.findPublishedAndNotPrivateNewsPaper();
    }

    public Collection<NewsPaper> findAllNewsPaperByUserAndNotPublished(int userId) {
        return this.newsPaperRepository.findAllNewsPaperByUserAndNotPublished(userId);
    }

    private boolean isTabooNewsPaper(final NewsPaper newsPaper) {
        boolean result = false;
        Pattern p;
        Matcher isAnyMatcherTitle;
        Matcher isAnyMatcherDescription;

        p = this.tabooWords();
        isAnyMatcherTitle = p.matcher(newsPaper.getTitle());
        isAnyMatcherDescription = p.matcher(newsPaper.getDescription());

        if (isAnyMatcherTitle.find() || isAnyMatcherDescription.find())
            result = true;

        return result;
    }

    public Pattern tabooWords() {
        Pattern result;
        List<String> tabooWords;

        final Collection<String> taboolist = this.configurationService.findAll().iterator().next().getTabooWords();
        tabooWords = new ArrayList<>(taboolist);

        String str = ".*\\b(";
        for (int i = 0; i <= tabooWords.size(); i++)
            if (i < tabooWords.size())
                str += tabooWords.get(i) + "|";
            else
                str += tabooWords.iterator().next() + ")\\b.*";

        result = Pattern.compile(str, Pattern.CASE_INSENSITIVE);

        return result;
    }

    public Collection<NewsPaper> findNewsPaperByTabooIsTrue(){
        Assert.isTrue(actorService.isAdministrator());
        return this.newsPaperRepository.findNewsPaperByTabooIsTrue();
    }

    public Collection<NewsPaper> searchNewspapers(String keyword) {
        return newsPaperRepository.searchNewspapers(keyword,keyword);
    }

    public void flush() {
        newsPaperRepository.flush();
    }

    public NewsPaper reconstructS(final NewsPaper newsPaperPruned, final BindingResult binding) {
        NewsPaper res;
        if(newsPaperPruned.getId()==0) {
            res = this.create();
        }else{
            res = this.findOne(newsPaperPruned.getId());

        }
        res.setTitle(newsPaperPruned.getTitle());
        res.setDescription(newsPaperPruned.getDescription());
        res.setPicture(newsPaperPruned.getPicture());
        res.setModePrivate(newsPaperPruned.isModePrivate());

        this.validator.validate(res,binding);

        return res;
    }

    public Collection<NewsPaper> findPublishedAndPrivateNewsPaper(){
        return this.newsPaperRepository.findPublishedAndPrivateNewsPaper();
    }

    public Collection<NewsPaper> findNewsPaperPlacedAdvertisement(int agentId) {
        return newsPaperRepository.findNewsPaperPlacedAdvertisement(agentId);
    }

    public Collection<NewsPaper> newsPapersWithNoAdds(){
        return newsPaperRepository.newsPapersWithNoAdds();
    }

    public Advertisement selectRandomAdd(Article article) {
        List<Advertisement> adds = new ArrayList<>(article.getNewsPaper().getAdvertisements());
        Advertisement res =null;
        if(adds.size()>0){
            int index = (int) (Math.random() * adds.size());
            res = adds.get(index);
        }
        return res;
    }


    public Collection<Customer> customerOfVolume(int customerId){
        return newsPaperRepository.customerOfVolume(customerId);
    }

    public SubscribeNewsPaper findSubscriptionNewsPaperByCustomer(int customerId,int newspaperId){
        return newsPaperRepository.findSubscriptionToNewsPaperByCustomer(customerId, newspaperId);
    }

    public Collection<Customer> customerSubscribedToNewspaper(int customerId, int newsPaperId) {
        return newsPaperRepository.customerSubscribedToNewspaper(customerId,newsPaperId);
    }

    public Customer isCustomerSubscribedToNewspaperViaVolume(int customerId, int newsPaperId){
        return newsPaperRepository.isCustomerSubscribedToNewspaperViaVolume(customerId,newsPaperId);
    }

}


