package services;

import domain.*;
import forms.RegisterAdvertisementForm;
import forms.SubscribeForm;
import forms.UserForm;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import repositories.AgentRepository;
import repositories.ArticleRepository;
import security.LoginService;
import security.UserAccount;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;

@Service
@Transactional
public class AgentService {

    // Managed repository -----------------------------------------------------

    @Autowired
    private AgentRepository agentRepository;

    // Supporting services ----------------------------------------------------

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private ActorService actorService;



    @Autowired
    private AdvertisementService advertisementService;

    @Autowired
    private NewsPaperService newsPaperService;




    // Constructors -----------------------------------------------------------

    public AgentService() { super();
    }

    // Simple CRUD methods ----------------------------------------------------

    public Agent create() {
        Agent res=null;

        res  = new Agent();

        res.setAdvertisements(new ArrayList<Advertisement>());
        res.setUserAccount(userAccountService.create("AGENT"));

        return res;
    }

    public Agent save(Agent agent){
        Assert.notNull(agent);
        Agent res = null;
        Collection<Folder> folders;

        if (agent.getId() == 0) {
            res = this.agentRepository.save(agent);
            folders = actorService.generateFolders(res);
            res.setFolders(folders);
            res = agentRepository.save(res);
        } else
            res = this.agentRepository.save(agent);

        return res;
    }

    public Collection<Agent> findAll(){
        Collection<Agent> res= null;
        res= this.agentRepository.findAll();
        return  res;
    }

    public Agent findOne(int agentId){
        Agent res= null;
        res= this.agentRepository.findOne(agentId);
        return  res;
    }

    public void delete(Agent agent){
        Assert.notNull(agent);

        this.agentRepository.delete(agent);
    }

    // Other business methods

    public Agent findByPrincipal() {

        Agent result;
        final UserAccount agentAccount = LoginService.getPrincipal();
        Assert.notNull(agentAccount);
        result = this.findByAgentAccountId(agentAccount.getId());
        Assert.notNull(result);
        return result;
    }

    public Agent findByAgentAccountId(final int agentAccountId) {

        Agent result;
        result = this.agentRepository.findByAgentAccountId(agentAccountId);
        return result;
    }

    public Agent reconstruct(UserForm userForm, final BindingResult binding) {

        Agent result;

        result = this.create();
        result.getUserAccount().setUsername(userForm.getUsername());
        result.setName(userForm.getName());
        result.setSurname(userForm.getSurname());
        result.setPhone(userForm.getPhone());
        result.setEmail(userForm.getEmail());
        result.setPostalAddresses(userForm.getPostalAddresses());
        result.getUserAccount().setPassword(new Md5PasswordEncoder().encodePassword(userForm.getPassword(), null));

        this.comprobarContrasena(userForm.getPassword(), userForm.getRepeatPassword(), binding);

        return result;
    }

    private boolean comprobarContrasena(final String password, final String passwordRepeat, final BindingResult binding) {
        FieldError error;
        String[] codigos;
        boolean result;

        if (StringUtils.isNotBlank(password) && StringUtils.isNotBlank(passwordRepeat))
            result = password.equals(passwordRepeat);
        else
            result = false;

        if (!result && password.length()>=4 && passwordRepeat.length()>=4) {
            codigos = new String[1];
            codigos[0] = "agent.password.mismatch";
            error = new FieldError("userForm", "repeatPassword", password, false, codigos, null, "password mismatch");
            binding.addError(error);
        }

        return result;
    }

    public Advertisement reconstructRegisterAdvertisement(RegisterAdvertisementForm registerAdvertisementForm, final BindingResult binding) {
        Advertisement advertisement = advertisementService.create();
        NewsPaper newsPaper;
        advertisement.setTitle(registerAdvertisementForm.getTitle());
        advertisement.setBanner(registerAdvertisementForm.getBanner());
        advertisement.setTargetPage(registerAdvertisementForm.getTargetPage());


        if (registerAdvertisementForm.getNewsPaperId() != null){
            newsPaper = newsPaperService.findOne(registerAdvertisementForm.getNewsPaperId());
            advertisement.setCreditCard(new CreditCard());
            advertisement.setNewsPaper(newsPaper);
        }
        return advertisement;
    }

    public void flush() {
        agentRepository.flush();
    }
}
