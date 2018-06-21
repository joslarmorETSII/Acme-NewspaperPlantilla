package services;

import domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import repositories.ChirpRepository;
import repositories.NewsPaperRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
public class ChirpService  {

    // Managed repository -----------------------------------------------------

    @Autowired
    private ChirpRepository chirpRepository;

    // Supporting services ----------------------------------------------------

    @Autowired
    private UserService userService;

    @Autowired
    private AdministratorService administratorService;

    @Autowired
    private ActorService actorService;

    @Autowired
    private ConfigurationService configurationService;

    // Constructors -----------------------------------------------------------

    public ChirpService() {
        super();
    }

    // Simple CRUD methods ----------------------------------------------------

    public Chirp create() {
        Chirp res=null;
        User user= null;
        user=userService.findByPrincipal();
        res= new Chirp();
        res.setUser(user);

        return res;
    }

    public Chirp save(Chirp chirp){
       Chirp res = null;
        Assert.isTrue(checkByPrincipal(chirp));
        if(isTabooChirp(chirp)){
            chirp.setTaboo(true);
        }
        res= chirpRepository.save(chirp);
        return res;
    }

    public Collection<Chirp> findAll(){
        Collection<Chirp> res= null;
        res= this.chirpRepository.findAll();
        return  res;
    }

    public Chirp findOne(int chirpId){
        Chirp res= null;
        res= this.chirpRepository.findOne(chirpId);
        return  res;
    }
    public Chirp findOneToEdit(int chirpId){
        Chirp res= null;

        res= this.chirpRepository.findOne(chirpId);
        Assert.isTrue(!res.getPosted());
        Assert.isTrue(checkByPrincipal(res) || checkByPrincipalAdmin(res));
        return  res;
    }

    public void delete(Chirp chirp){
        Assert.notNull(chirp);
        Assert.isTrue(checkByPrincipalAdmin(chirp) || checkByPrincipal(chirp));
        this.chirpRepository.delete(chirp);
    }




    // Other business methods -------------------------------------------------


    private boolean isTabooChirp(final Chirp chirp) {
        boolean result = false;
        Pattern p;
        Matcher isAnyMatcherTitle;
        Matcher isAnyMatcherDescription;

        p = this.tabooWords();
        isAnyMatcherTitle = p.matcher(chirp.getTitle());
        isAnyMatcherDescription = p.matcher(chirp.getDescription());

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


    public boolean checkByPrincipal(Chirp chirp) {
        Boolean res = null;
        User principal = null;

        res = false;
        principal = this.userService.findByPrincipal();

        if (chirp.getUser().equals(principal))
            res = true;

        return res;
    }

    public boolean checkByPrincipalAdmin(Chirp chirp){
        Boolean res= false;
        Administrator administrator = administratorService.findByPrincipal();
        if(administrator!=null) {
            Collection<Authority> authorities = administrator.getUserAccount().getAuthorities();
            String authority = authorities.toArray()[0].toString();
            res = authority.equals("ADMINISTRATOR");
        }
        return res;

    }

    public Collection<Chirp> findChirpsByUserId(int userId){
        return chirpRepository.findChirpsByUserId(userId);
    }

    public Collection<Chirp> findAllChirpsByFollowings(int userId){
        Assert.isTrue(actorService.isUser());
        return chirpRepository.findAllChirpsByFollowings(userId);
    }

    public void findOneToPublish(Chirp chirp){
            Assert.isTrue(!chirp.getPosted());
            if(!chirp.getPosted()){
                chirp.setPosted(true);
                chirp.setMoment(new Date());
            }
    }

    public Collection<Chirp> findTabooChirps() {
        Assert.isTrue(actorService.isAdministrator());
        return chirpRepository.findfindTabooChirps();
    }

    public Collection<Chirp> findPublishedChirps() {
        return chirpRepository.findPublishedChirps();
    }

    public void flush() {
        chirpRepository.flush();
    }
}
