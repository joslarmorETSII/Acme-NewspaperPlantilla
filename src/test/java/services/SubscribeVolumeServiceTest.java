package services;

import domain.CreditCard;
import domain.Customer;
import domain.SubscribeVolume;
import domain.Volume;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import utilities.AbstractTest;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

@Transactional
@ContextConfiguration(locations = {
        "classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class SubscribeVolumeServiceTest extends AbstractTest{

    // The SUT
    // ====================================================

    @Autowired
    private SubscribeVolumeService subscribeVolumeService;

    @Autowired
    private VolumeService volumeService;

    @Autowired
    private CustomerService customerService;

    // Tests
    // ====================================================

      /*  FUNCTIONAL REQUIREMENT:
        An actor who is authenticated as a customer must be able to:
            * Subscribe to a volume by providing a credit card.
    */

    public void subscribeVolume(String username, CreditCard creditCard, String subscribeVolumeBean, String customerBean, Class<?> expected) {

        Class<?> caught=null;
        startTransaction();
        try {
            this.authenticate(username);

            Customer customer = customerService.findOne(getEntityId(customerBean));
            SubscribeVolume subscribeVolume = subscribeVolumeService.findOne(getEntityId(subscribeVolumeBean));

            volumeService.findNotSubscribesVolumes(customer.getId());
            subscribeVolume.setCreditCard(creditCard);
            subscribeVolumeService.create();

            subscribeVolumeService.save(subscribeVolume);

            subscribeVolumeService.flush();


        } catch (final Throwable oops) {
            caught = oops.getClass();
        }
        this.checkExceptions(expected, caught);
        rollbackTransaction();
    }

    //Drivers
    // ===================================================

    @Test
    public void driverSubscribeVolumeTest() {

        CreditCard creditCard = new CreditCard();

        creditCard.setHolder("Holder 2");
        creditCard.setBrand("visa");
        creditCard.setCvv(123);
        creditCard.setExpirationMonth(12);
        creditCard.setExpirationYear(2018);
        creditCard.setNumber("4785530860520625");

        final Object testingData[][] = {
                // Subscribirse a un volume logueado como customer -> true
                {
                        "customer1",creditCard,"subscribeVolume1" ,"customer1", null
                },
                // Subscribirse logueado como user -> false
                {
                        "user1",creditCard ,"subscribeVolume1" ,"customer1", IllegalArgumentException.class
                },
                // Subscribirse sin estar logueado -> False
                {
                        null,creditCard ,"subscribeVolume1" ,"customer1", IllegalArgumentException.class
                },
                // Subscribirse con creditCard a null -> False
                {
                        "customer1",null ,"subscribeVolume1" ,"customer1", ConstraintViolationException.class
                }


        };
        for (int i = 0; i < testingData.length; i++)
            this.subscribeVolume((String) testingData[i][0],(CreditCard) testingData[i][1] ,
                    (String)testingData[i][2],(String) testingData[i][3],(Class<?>) testingData[i][4]);
    }
}
