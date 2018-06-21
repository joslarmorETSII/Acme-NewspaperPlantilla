package services;

import controllers.AbstractController;
import domain.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import services.AdministratorService;
import services.ConfigurationService;
import utilities.AbstractTest;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;

@Transactional
@ContextConfiguration(locations = {
        "classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class AdministratorServiceTest extends AbstractTest{

    @Autowired
    private AdministratorService administratorService;

    @Autowired
    private ConfigurationService   configurationService;

    // Tests ====================================================

    /*  FUNCTIONAL REQUIREMENT:
            * An actor who is authenticated as an administrator must be able to:
               - Manager a list of taboo words.
    */

    @SuppressWarnings("deprecation")
    public void listConfigurationTabooWordsTest(final String username, final Class<?> expected) {
        Class<?> caught = null;
        startTransaction();
        try {

            this.authenticate(username);
            Administrator administrator = administratorService.findByPrincipal();

            Configuration configuration = configurationService.findOneToEdit(configurationService.findAll().iterator().next().getId());

            Collection<String>  words = configuration.getTabooWords();

            this.unauthenticate();


        } catch (final Throwable oops) {

            caught = oops.getClass();

        }

        this.checkExceptions(expected, caught);
        rollbackTransaction();
    }

    //Driver ====================================================

    @Test
    public void driverListConfigurationTabooWordsTest() {

        final Object testingData[][] = {
                // Intentando ver las palabras taboo como administrator -> true
                {
                        "administrator", null
                },
                // Integnado ver las palabras taboo como user2 -> false
                {
                        "user2", IllegalArgumentException.class
                },
                // Integnado ver las palabras taboo como customer1 -> false
                {
                        "customer1", IllegalArgumentException.class
                }

        };
        for (int i = 0; i < testingData.length; i++)
            this.listConfigurationTabooWordsTest((String) testingData[i][0], (Class<?>) testingData[i][1]);
    }
}
