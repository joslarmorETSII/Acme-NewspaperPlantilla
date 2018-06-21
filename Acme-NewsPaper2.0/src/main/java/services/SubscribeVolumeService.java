package services;

import domain.CreditCard;
import domain.Customer;
import domain.SubscribeVolume;
import domain.Volume;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import repositories.SubscribeVolumeRepository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Date;

@Service
@Transactional
public class SubscribeVolumeService {
    // Managed repository -----------------------------------------------------

    @Autowired
    private SubscribeVolumeRepository subscribeVolumeRepository;

    // Supporting services ----------------------------------------------------

    @Autowired
    private NewsPaperService newsPaperService;

    @Autowired
    private UserService userService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ActorService actorService;

    // Constructors -----------------------------------------------------------

    public SubscribeVolumeService() {
        super();
    }

    // Simple CRUD methods ----------------------------------------------------

    public SubscribeVolume create(){
        Assert.isTrue(actorService.isCustomer());
        SubscribeVolume res;

        res = new SubscribeVolume();
        res.setCustomer(customerService.findByPrincipal());

        return res;
    }

    public SubscribeVolume findOne(int id){
        return subscribeVolumeRepository.findOne(id);
    }

    public Collection<SubscribeVolume> findAll(){
        return subscribeVolumeRepository.findAll();
    }

    public void delete(SubscribeVolume subscribeNewsPaper){
        Customer customer = subscribeNewsPaper.getCustomer();
        Volume volume = subscribeNewsPaper.getVolume();
        customer.getSubscriptionsToVolumes().remove(subscribeNewsPaper);
        volume.getSubscriptionVolumes().remove(subscribeNewsPaper);
        subscribeVolumeRepository.delete(subscribeNewsPaper);
    }

    public SubscribeVolume save(SubscribeVolume subscribeNewsPaper){
        subscribeNewsPaper.setMoment(new Date());
        return subscribeVolumeRepository.save(subscribeNewsPaper);
    }

    public SubscribeVolume findSubscriptionToAVolume(int volumeId, int customerId) {
        return subscribeVolumeRepository.findSubscriptionToAVolume(volumeId,customerId);
    }

    public void deleteCustomerVolume(SubscribeVolume subscribeVolume){
        subscribeVolume.setCustomer(null);
        this.subscribeVolumeRepository.delete(subscribeVolume);
    }
    // Other business methods -------------------------------------------------

    public void flush() {
        subscribeVolumeRepository.flush();
    }
}
