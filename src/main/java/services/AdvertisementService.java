package services;

import domain.*;
import forms.RegisterAdvertisementForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import repositories.AdvertisementRepository;
import repositories.AgentRepository;
import repositories.ArticleRepository;

import javax.transaction.Transactional;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
public class AdvertisementService {

    // Managed repository -----------------------------------------------------

    @Autowired
    private AdvertisementRepository advertisementRepository;

    // Supporting services ----------------------------------------------------

    @Autowired
    private AgentService agentService;

    @Autowired
    private NewsPaperService    newsPaperService;

    @Autowired
    private ConfigurationService configurationService;

    @Autowired
    private ActorService actorService;

    // Constructors -----------------------------------------------------------

    public AdvertisementService() { super();
    }

    // Simple CRUD methods ----------------------------------------------------

    public Advertisement create() {
        Advertisement res=null;
        Agent agent = null;
        agent = agentService.findByPrincipal();

        res  = new Advertisement();
        res.setAgent(agent);

        return res;
    }

    public Advertisement save(Advertisement advertisement){
        Advertisement res = null;
        Assert.isTrue(checkByPrincipal(advertisement));

        if(isTabooAdvertisement(advertisement)){
            advertisement.setTaboo(true);
        }
        res= advertisementRepository.save(advertisement);
        return res;
    }

    public Collection<Advertisement> findAll(){
        Collection<Advertisement> res= null;
        res= this.advertisementRepository.findAll();
        return  res;
    }

    public Advertisement findOne(int advertisementId){
        Advertisement res= null;
        res= this.advertisementRepository.findOne(advertisementId);
        return  res;
    }

    public void delete(Advertisement advertisement){
        Assert.notNull(advertisement);
        Assert.isTrue(actorService.isAdministrator());
        Collection<NewsPaper> newsPapers = newsPaperService.findAll();

        for(NewsPaper n: newsPapers){
            n.getAdvertisements().remove(advertisement);
        }
        this.advertisementRepository.delete(advertisement);
    }

    public void deleteAll(NewsPaper newsPaper){
        for(Advertisement a : newsPaper.getAdvertisements()){
            this.advertisementRepository.delete(a);
        }
    }

    // Other business methods -------------------------------------------------

    public boolean checkByPrincipal(Advertisement advertisement) {
        Boolean res = null;
        Agent principal = null;

        res = false;
        principal = this.agentService.findByPrincipal();

        if (advertisement.getAgent().equals(principal))
            res = true;

        return res;
    }

    private boolean isTabooAdvertisement(final Advertisement advertisement) {
        boolean result = false;
        Pattern p;
        Matcher isAnyMatcherTitle;

        p = this.tabooWords();
        isAnyMatcherTitle = p.matcher(advertisement.getTitle());

        if (isAnyMatcherTitle.find() )
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

    public Collection<Advertisement> findTabooAdvertisement() {
        Assert.isTrue(actorService.isAdministrator());
        return advertisementRepository.findTabooAdvertisement();
    }

    public CreditCard reconstruct(RegisterAdvertisementForm registerAdvertisementForm, BindingResult binding) {
        CreditCard creditCard= new CreditCard();

        creditCard.setBrand(registerAdvertisementForm.getBrand());
        creditCard.setCvv(registerAdvertisementForm.getCvv());
        creditCard.setExpirationMonth(registerAdvertisementForm.getExpirationMonth());
        creditCard.setExpirationYear(registerAdvertisementForm.getExpirationYear());
        creditCard.setHolder(registerAdvertisementForm.getHolder());
        creditCard.setNumber(registerAdvertisementForm.getNumber());

        checkMonth(registerAdvertisementForm.getExpirationMonth(),registerAdvertisementForm.getExpirationYear(),binding);
        return creditCard;
    }

    private boolean checkMonth(Integer month, Integer year, BindingResult binding) {
        FieldError error;
        String[] codigos;
        boolean result;
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        Integer actualMonth = c.get(Calendar.MONTH)+1;
        Integer actualYear = c.get(Calendar.YEAR);


        if (month!=null)
            result = month.equals(actualMonth) && actualYear.equals(year);
        else
            result = false;

        if (result) {
            codigos = new String[1];
            codigos[0] = "creditCard.month.expired";
            error = new FieldError("registerAdvertisementForm", "expirationMonth", month, false, codigos, null, "must not expire this month");
            binding.addError(error);
        }if (!result && actualYear.equals(year) && month<actualMonth){
            codigos = new String[1];
            codigos[0] = "creditCard.month.invalid";
            error = new FieldError("registerAdvertisementForm", "expirationMonth", month, false, codigos, null, "should not be in the past");
            binding.addError(error);
        }

        return result;
    }

    public void flush() {
        advertisementRepository.flush();
    }

}
