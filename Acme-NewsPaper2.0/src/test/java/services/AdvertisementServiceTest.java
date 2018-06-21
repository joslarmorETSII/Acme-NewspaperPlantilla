package services;

import domain.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
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

public class AdvertisementServiceTest extends AbstractTest {

    // The SUT
    // ====================================================

    @Autowired
    private AdvertisementService advertisementService;

    @Autowired
    private NewsPaperService newsPaperService;


    /*  FUNCTIONAL REQUIREMENT:
     *
     *   An actor who is authenticated as an administrator must be able to:
     *      - List the advertisements that contain taboo words in its title.
     */

    public void listAdvertisementsTabooWords(final String username,final Class<?> expected) {
        Class<?> caught = null;
        startTransaction();
        try {
            this.authenticate(username);

            this.advertisementService.findTabooAdvertisement();

            advertisementService.flush();
            this.unauthenticate();

        } catch (final Throwable oops) {
            caught = oops.getClass();

        }
        this.checkExceptions(expected, caught);
        rollbackTransaction();
    }

     /*  FUNCTIONAL REQUIREMENT:
            *  An actor who is authenticated as an administrator must be able to:
                - Remove an advertisement that he or she thinks is inappropriate.
    */


    public void deleteAdvertisementInappropiate(final String username, final Class<?> expected) {
        Class<?> caught = null;
        startTransaction();

        try {

            this.authenticate(username);

            Advertisement result= advertisementService.findAll().iterator().next();

            this.advertisementService.delete(result);

            advertisementService.flush();
            this.unauthenticate();

        } catch (final Throwable oops) {

            caught = oops.getClass();

        }

        this.checkExceptions(expected, caught);
        rollbackTransaction();
    }

     /*  FUNCTIONAL REQUIREMENT:
            *  An actor who is authenticated as an agent must be able to:
                - Register an advertisement and place it in a newspaper.
    */

    public void advertisementRegister(final String username, final String title, final String banner, final String targetPage, CreditCard creditCard, String newsPaperBean, final Class<?> expected) {
        Class<?> caught = null;
        startTransaction();

        try {

            this.authenticate(username);

            final Advertisement result = this.advertisementService.create();
            NewsPaper newsPaper = newsPaperService.findOne(getEntityId(newsPaperBean));

            result.setTitle(title);
            result.setBanner(banner);
            result.setTargetPage(targetPage);

            result.setCreditCard(creditCard);
            result.setNewsPaper(newsPaper);

            this.advertisementService.save(result);
            advertisementService.flush();

            this.unauthenticate();

        } catch (final Throwable oops) {

            caught = oops.getClass();

        }

        this.checkExceptions(expected, caught);
        rollbackTransaction();
    }


    //Drivers
    // ===================================================

    @Test
    public void driverListAdvertisementsTabooWordsTest() {

        final Object testingData[][] = {
                // Intentando ver los advertisements taboo como administrator -> true
                {
                        "administrator", null
                },
                // Intentando ver los advertisements taboo como user2 -> false
                {
                        "user2", IllegalArgumentException.class
                },
                // Intentando ver los advertisements taboo como customer1 -> false
                {
                        "customer1", IllegalArgumentException.class
                }

        };
        for (int i = 0; i < testingData.length; i++)
            this.listAdvertisementsTabooWords((String) testingData[i][0], (Class<?>) testingData[i][1]);
    }

    @Test
    public void driverDeleteAdvertisementInappropiateTest() {

        final Object testingData[][] = {
                // Borrar un advertisement que considere inapropiado como admin -> true
                {
                        "administrator", null
                },
                // Borrar un advertisement que considere inapropiado como null -> false
                {
                        null, IllegalArgumentException.class
                },
                // Borrar un advertisement que considere inapropiado como customer -> false
                {
                        "customer1", IllegalArgumentException.class
                },

        };
        for (int i = 0; i < testingData.length; i++)
            this.deleteAdvertisementInappropiate((String) testingData[i][0], (Class<?>) testingData[i][1]);
    }

    @Test
    public void driverAdvertisementRegisterTest() {

        CreditCard creditCard = new CreditCard();

        creditCard.setHolder("Holder 2");
        creditCard.setBrand("visa");
        creditCard.setCvv(123);
        creditCard.setExpirationMonth(12);
        creditCard.setExpirationYear(2018);
        creditCard.setNumber("4785530860520625");


        final Object testingData[][] = {
                // Registrar advertisement logueado como agent -> true
                {
                        "agent1", "advertisement1", "http://sprudge.com/advertise-2", "http://sprudge.com/advertise-2", creditCard, "newsPaper1", null
                },
                // Registar advertisement sin loguearse -> false
                {
                        null, "advertisement1", "http://sprudge.com/advertise-2", "http://sprudge.com/advertise-2", creditCard, "newsPaper1", IllegalArgumentException.class
                },
                // Registrar advertisement con creditCard a null -> false
                {
                        "agent1", "advertisement1", "http://sprudge.com/advertise-2", "http://sprudge.com/advertise-2", null, "newsPaper1", ConstraintViolationException.class
                },

        };
        for (int i = 0; i < testingData.length; i++)
            this.advertisementRegister((String) testingData[i][0], (String) testingData[i][1],
                    (String) testingData[i][2],(String) testingData[i][3],
                    (CreditCard) testingData[i][4], (String) testingData[i][5],(Class<?>) testingData[i][6]);
    }
}