package services;

import domain.*;
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
public class SubscribeNewsPaperServiceTest extends AbstractTest{


    // The SUT
    // ====================================================

    @Autowired
    private SubscribeVolumeService subscribeVolumeService;

    @Autowired
    private VolumeService volumeService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private NewsPaperService newsPaperService;

    @Autowired
    private SubscribeNewsPaperService subscribeNewsPaperService;

    // Tests
    // ====================================================
      /*  FUNCTIONAL REQUIREMENT:
        An actor who is authenticated as a customer must be able to:
            *  An actor who is authenticated as a customer can:
              . Subscribe to a private newspaper by providing a valid credit card.
    */

    public void subscribeVolume(String username, String holder, String brand, String number, Integer month, Integer year, Integer cvv
            , String newspaperTosubscribeBean, Class<?> expected) {

        Class<?> caught=null;
        startTransaction();
        try {
            this.authenticate(username);

            NewsPaper newsPaper = newsPaperService.findOne(getEntityId(newspaperTosubscribeBean));
            SubscribeNewsPaper subscribeNewsPaper = subscribeNewsPaperService.create();
            CreditCard creditCard1 = new CreditCard();

            creditCard1.setHolder(holder);
            creditCard1.setBrand(brand);
            creditCard1.setNumber(number);
            creditCard1.setExpirationMonth(month);
            creditCard1.setExpirationYear(year);
            creditCard1.setCvv(cvv);

            subscribeNewsPaper.setCreditCard(creditCard1);
            subscribeNewsPaper.setNewsPaper(newsPaper);

            subscribeNewsPaperService.save(subscribeNewsPaper);

            subscribeNewsPaperService.flush();


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

        final Object testingData[][] = {
                // 1. Subscribirse a una newspaper privada logueado como customer1 -> true
                {
                        "customer1","holder","brand","6011611468820491",12,2020,111 ,"newsPaper2", null
                },
                 // 2. Subscribirse a una newspaper privada rellenando con texto el numero de la creditcard-> false
                {
                        "customer1","holder","brand","Numero tarjeta",12,2020,111 ,"newsPaper2", ConstraintViolationException.class
                },
                // 2. Subscribirse a una newspaper publica -> false
                {
                        "customer1","holder","brand","6011611468820491",12,2020,111 ,"newsPaper1", IllegalArgumentException.class
                },



        };
        for (int i = 0; i < testingData.length; i++)
            this.subscribeVolume((String) testingData[i][0],(String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3],
                    (Integer) testingData[i][4], (Integer) testingData[i][5], (Integer) testingData[i][6],(String) testingData[i][7]
                    ,(Class<?>) testingData[i][8]);
    }




}
