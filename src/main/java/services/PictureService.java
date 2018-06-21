package services;

import domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import repositories.ChirpRepository;
import repositories.PictureRepository;
import security.Authority;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
public class PictureService {

    // Managed repository -----------------------------------------------------

    @Autowired
    private PictureRepository pictureRepository;

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

    public PictureService() {
        super();
    }

    // Simple CRUD methods ----------------------------------------------------

    public Picture create() {
        Picture res=null;
        res= new Picture();

        return res;
    }

    public Picture savePictureArticle(Picture picture, Article article){
        Picture res = null;
        Assert.isTrue(checkByPrincipal(picture));

        res= pictureRepository.save(picture);
        return res;
    }

    public Picture savePictureFollowUp(Picture picture, FollowUp followUp){
        Picture res = null;
        Assert.isTrue(checkByPrincipal(picture));

        res= pictureRepository.save(picture);
        return res;
    }

    public Collection<Picture> findAll(){
        Collection<Picture> res= null;
        res= this.pictureRepository.findAll();
        return  res;
    }

    public Picture findOne(int pictureId){
        Picture res= null;
        res= this.pictureRepository.findOne(pictureId);
        return  res;
    }
    public Picture findOneToEdit(int pictureId){
        Picture res= null;

        res= this.pictureRepository.findOne(pictureId);
        Assert.isTrue(checkByPrincipal(res));
        return  res;
    }

    public void delete(Picture picture){
        Assert.notNull(picture);
        Assert.isTrue(checkByPrincipal(picture) || checkByPrincipalAdmin(picture));
        this.pictureRepository.delete(picture);
    }

    public void deleteAll(FollowUp followUp){
        this.pictureRepository.delete(followUp.getPictures());
    }

    // Other business methods -------------------------------------------------

    public boolean checkByPrincipal(Picture picture) {
        Boolean res = null;
        User principal = null;
        User publisher;
        User writer;

        res = false;
        principal = this.userService.findByPrincipal();
        if(picture.getArticle()!=null) {
            publisher = picture.getArticle().getNewsPaper().getPublisher();
            res = publisher.equals(principal);
        }
        else if(picture.getFollowUp()!=null) {
            writer = picture.getFollowUp().getArticle().getNewsPaper().getPublisher();
            res = writer.equals(principal);
        }

        return res;
    }

    public boolean checkByPrincipalAdmin(Picture picture){
        Boolean res= false;
        Administrator administrator = administratorService.findByPrincipal();
        if(administrator!=null) {
            Collection<Authority> authorities = administrator.getUserAccount().getAuthorities();
            String authority = authorities.toArray()[0].toString();
            res = authority.equals("ADMINISTRATOR");
        }
        return res;

    }

    public void flush() {
        pictureRepository.flush();
    }
}
