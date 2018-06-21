package services;

import domain.Chirp;
import domain.NewsPaper;
import domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import utilities.AbstractTest;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Transactional
@ContextConfiguration(locations = {
        "classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class ChirpServiceTest extends AbstractTest{

    // The SUT ---------------------------------
    @Autowired
    private ChirpService chirpService;

    @Autowired
    private UserService userService;

    /*  FUNCTIONAL REQUIREMENT:
     * Post a chirp. Chirps may not be changed or deleted once they are posted.  */

    public void chirpCreateTest(String username, String title, Date moment, String description,
                                boolean posted, boolean taboo, Class<?> expected) {
        Class<?> caught=null;
        startTransaction();
        try {
            this.authenticate(username);

            final Chirp result = this.chirpService.create();

            result.setTitle(title);
            result.setMoment(moment);
            result.setDescription(description);
            result.setPosted(posted);
            result.setTaboo(taboo);

            Chirp c= this.chirpService.save(result);
            chirpService.findOneToPublish(result);
            c = result;

            chirpService.flush();
            this.unauthenticate();


        } catch (final Throwable oops) {
            caught = oops.getClass();
        }
        this.checkExceptions(expected, caught);
        rollbackTransaction();
    }

    /*  FUNCTIONAL REQUIREMENT:
       * An actor who is authenticated as an administrator must be able to:
               - List the chirps that contain taboo words.
    */

    public void listChirpsTabooWords(final String username,final Class<?> expected){
        Class<?> caught = null;
        startTransaction();
        try {
            this.authenticate(username);

            this.chirpService.findTabooChirps();

            chirpService.flush();
            this.unauthenticate();

        } catch (final Throwable oops) {

            caught = oops.getClass();

        }

        this.checkExceptions(expected, caught);
        rollbackTransaction();
    }

     /*  FUNCTIONAL REQUIREMENT:
            * An actor who is authenticated as a admin must be able to:
                -.Remove an chirp that he or she thinks is inappropriate.
    */

    public void deleteChirpInappropiateTest(final String username, final Class<?> expected) {
        Class<?> caught = null;
        startTransaction();

        try {

            this.authenticate(username);

            Chirp result= chirpService.findAll().iterator().next();

            this.chirpService.delete(result);

            chirpService.flush();
            this.unauthenticate();

        } catch (final Throwable oops) {

            caught = oops.getClass();

        }

        this.checkExceptions(expected, caught);
        rollbackTransaction();
    }

    /*  FUNCTIONAL REQUIREMENT:
     *
     *  An actor who is authenticated as a user must be able to:
     *    -   Display a stream with the chirps posted by all of the users that he or she follows.
     */

    public void displayStream(final String username, String userBean,
                              final Class<?> expected) {
        Class<?> caught = null;
        startTransaction();
        try {

            authenticate(username);

            User user = userService.findOne(getEntityId(userBean));
            chirpService.findAllChirpsByFollowings(user.getId());

            chirpService.flush();
            this.unauthenticate();


        } catch (final Throwable oops) {
            caught = oops.getClass();
        }

        this.checkExceptions(expected, caught);
        rollbackTransaction();
    }

    // Drivers
    // ===================================================

    @Test
    public void driverCreateChirpTest() {

        final Date date = new Date();

        final Object testingData[][] = {
                // Crear un chirp logueado como usuario -> true
                {
                        "user1","title1",date ,"description1", false, false, null
                },
                // Crear un chirp sin estar logueado -> false
                {
                        null, "title1",date ,"description1", false, false, IllegalArgumentException.class
                },
                // Crear un chirp con taboo y posted a true ->false
                {
                        "user1","title1", date,"description1", true, true, IllegalArgumentException.class
                }

        };
        for (int i = 0; i < testingData.length; i++)
            this.chirpCreateTest((String) testingData[i][0],(String)testingData[i][1] ,
                    (Date)testingData[i][2],(String)testingData[i][3],(Boolean)testingData[i][4],
                    (Boolean)testingData[i][5],(Class<?>) testingData[i][6]);
    }

    @Test
    public void driverListChirpsTabooWordsTest() {

        final Object testingData[][] = {
                // Intentando ver los chirps taboo como administrator -> true
                {
                        "administrator", null
                },
                // Intentando ver los chirps taboo como user2 -> false
                {
                        "user2", IllegalArgumentException.class
                },
                // Intentando ver los chirps taboo como customer1 -> false
                {
                        "customer1", IllegalArgumentException.class
                }

        };
        for (int i = 0; i < testingData.length; i++)
            this.listChirpsTabooWords((String) testingData[i][0], (Class<?>) testingData[i][1]);
    }

    @Test
    public void driverDeleteChirpInappropiateTest() {

        final Object testingData[][] = {
                // Borrar un chirp que considere inapropiado como admin -> true
                {
                        "administrator", null
                },
                // Borrar un chirp que considere inapropiado como null -> false
                {
                        null, IllegalArgumentException.class
                },
                // Borrar un chirp que considere inapropiado como customer -> false
                {
                        "customer1", IllegalArgumentException.class
                },

        };
        for (int i = 0; i < testingData.length; i++)
            this.deleteChirpInappropiateTest((String) testingData[i][0], (Class<?>) testingData[i][1]);
    }

    @Test
    public void driverDisplayStream() {

        final Object testingData[][] = {
                // Stream de chirp de user2 -> true
                {
                        "user1","user2",  null
                },
                // El usuario a null --> false
                {
                        null, "user1", IllegalArgumentException.class
                },
                // Stream de chirp logueado como admin-> false
                {
                        "admin", "user1", IllegalArgumentException.class
                }

        };
        for (int i = 0; i < testingData.length; i++)
            this.displayStream((String) testingData[i][0],(String) testingData[i][1],
                    (Class<?>) testingData[i][2]);
    }

}