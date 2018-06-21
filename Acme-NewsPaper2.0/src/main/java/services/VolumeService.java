package services;

import domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import repositories.VolumeRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;

@Service
@Transactional
public class VolumeService {

    //Repositories -----------------------------------------------------------

    @Autowired
    private VolumeRepository volumeRepository;

    @Autowired
    private Validator validator;

    //Services ----------------------------------------------------------------

    @Autowired
    private UserService userService;

    @Autowired
    private ActorService actorService;

    //Constructores -----------------------------------------------------------

    public VolumeService() { super();
    }

    // Simple CRUD methods ----------------------------------------------------


    //create

    public Volume create(){
        Assert.isTrue(actorService.isUser());
        Volume res= null;
        User user = this.userService.findByPrincipal();

        Collection<NewsPaper> newsPapers= new ArrayList<>();

        res= new Volume();
        res.setUser(user);

        res.setNewsPapersVolume(newsPapers);
        res.setSubscriptionVolumes(new ArrayList<SubscribeVolume>());
        return res;
    }

    //save

    public Volume save (Volume volume){
        Volume res;

        res= volumeRepository.save(volume);
        return res;

    }

    public Collection<Volume> findAll(){
        return volumeRepository.findAll();
    }

    public Volume findOne(int volumeId){
        Volume res= null;
        res= volumeRepository.findOne(volumeId);
        return res;
    }

    public Volume findOneToEdit(int volumeId){
        Volume res= null;
        res= volumeRepository.findOne(volumeId);
        Assert.isTrue(checkByPrincipal(res));
        return res;
    }

    public void delete(NewsPaper newsPaper){
        this.volumeRepository.delete(newsPaper.getVolumes());
    }

    //Anthiliaries methodes

    public Collection<NewsPaper> findPublishedAndNotPrivateNewsPaperPerVolume(int volumeId){
        return volumeRepository.findPublishedAndNotPrivateNewsPaperPerVolume(volumeId);
    }

    public Collection<NewsPaper> findPublishedNewsPaperPerVolume(int volumeId){
        return volumeRepository.findPublishedNewsPaperPerVolume(volumeId);
    }

    public boolean checkByPrincipal(Volume volume) {
        Boolean res = null;
        User principal = null;

        res = false;
        principal = this.userService.findByPrincipal();

        if (volume.getUser().equals(principal))
            res = true;

        return res;
    }

    public Volume reconstructS(final Volume volumePruned, final BindingResult binding) {
        Volume res;
        if(volumePruned.getId()==0) {
            res = this.create();
        }else{
            res = this.findOne(volumePruned.getId());
        }
        res.setTitle(volumePruned.getTitle());
        res.setDescription(volumePruned.getDescription());
        res.setAnyo(volumePruned.getAnyo());

        this.validator.validate(res,binding);

        return res;
    }

    public Volume reconstructAddNewsPaper(final Volume volumePruned, final BindingResult binding) {
        Volume res;
        res = this.findOne(volumePruned.getId());
        Collection<NewsPaper> newsPapers = new ArrayList<>();
        newsPapers = res.getNewsPapersVolume();
        newsPapers.addAll(volumePruned.getNewsPapersVolume());

        res.setNewsPapersVolume(newsPapers);

        this.validator.validate(res,binding);

        return res;
    }

    public Volume reconstructRemoveNewsPaper(final Volume volumePruned, final BindingResult binding) {
        Volume res;
        res = this.findOne(volumePruned.getId());
        Collection<NewsPaper> newsPapers = new ArrayList<>();
        newsPapers = res.getNewsPapersVolume();
        newsPapers.removeAll(volumePruned.getNewsPapersVolume());

        res.setNewsPapersVolume(newsPapers);

        this.validator.validate(res,binding);

        return res;
    }

    public Collection<NewsPaper> findPrivateNewsPapersByVolume(int volumeId) {
        return volumeRepository.findPrivateNewspapersByVolumeId(volumeId);
    }

    public void flush() {
        volumeRepository.flush();
    }

    public Collection<Volume> findVolumeByCustomer(int customerId) {
        return volumeRepository.findVolumeByCustomer(customerId);
    }

    public Collection<Volume> findNotSubscribesVolumes(int id) {
        return volumeRepository.findNotSubscribesVolumes(id);
    }
}
