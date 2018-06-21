package services;

import domain.Article;
import domain.FollowUp;
import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import repositories.ArticleRepository;
import repositories.FollowUpRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Service
@Transactional
public class FollowUpService {

    // Managed repository -----------------------------------------------------

    @Autowired
    private FollowUpRepository followUpRepository;

    // Supporting services ----------------------------------------------------

    @Autowired
    private UserService userService;

    @Autowired
    private AdministratorService administratorService;

    // Constructors -----------------------------------------------------------

    public FollowUpService() { super();
    }

    //Simple CRUD methods ----------------------------------------------------

    public FollowUp create(){
        FollowUp res= null;
        Collection<Article> articles= new ArrayList<>();
        User publisher= userService.findByPrincipal();

        res= new FollowUp();
        //res.getArticle().getNewsPaper().setPublisher(publisher);
         return res;
    }

    public FollowUp save(FollowUp followUp){
        Assert.notNull(followUp);
        Assert.isTrue(checkByPrincipal(followUp));
        Assert.isTrue(followUp.getArticle().getFinalMode());
        Assert.isTrue(followUp.getArticle().getNewsPaper().getPublished());
        followUp.setMoment(new Date());
        return followUpRepository.save(followUp);
    }

    public Collection<FollowUp> findAll(){
        return followUpRepository.findAll();
    }

    public FollowUp findOne(int followUpId){
        FollowUp res= null;
        res= followUpRepository.findOne(followUpId);
        return res;
    }

    public void deleteAll(Collection<FollowUp> followUps){
        followUpRepository.delete(followUps);
    }

    // Other business methods -------------------------------------------------


    public boolean checkByPrincipal(FollowUp followUp) {
        Boolean res = null;
        User principal = null;

        res = false;
        principal = this.userService.findByPrincipal();

        if (followUp.getArticle().getNewsPaper().getPublisher().equals(principal))
            res = true;

        return res;
    }

    public Collection<FollowUp> findFollowupsByUserId(int id) {
        return followUpRepository.findFollowupsByUserId(id);
    }

    public void flush() {
        followUpRepository.flush();
    }
}
