package services;

import domain.Chirp;
import domain.Volume;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import utilities.AbstractTest;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import java.util.Collection;
import java.util.Date;

@Transactional
@ContextConfiguration(locations = {
        "classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class VolumeServiceTest extends AbstractTest{
    // The SUT
    // ====================================================

    @Autowired
    private VolumeService volumeService;

    // Tests
    // ====================================================

      /*  FUNCTIONAL REQUIREMENT:
            * An actor who is not authenticated must be able to:
               - List the volumes in the system and browse their newspapers
               as long as they are authorised (for instance, a private newspaper
               cannot be fully displayed to unauthenticated actors).
    */

    public void listVolume(final String username,final Class<?> expected) {
        Class<?> caught = null;
        startTransaction();
        try {
            this.authenticate(username);

            this.volumeService.findAll();

            this.unauthenticate();

        } catch (final Throwable oops) {
            caught = oops.getClass();

        }
        this.checkExceptions(expected, caught);
        rollbackTransaction();
    }

    /*  FUNCTIONAL REQUIREMENT:
        An actor who is not authenticated must be able to:
            * Create a volume with as many published newspapers as he
            * or she wishes. Note that the newspapers in a volume can
            * be added or removed at any time. The same newspaper may
            * be used to create different volumes.
     */

    public void volumeCreate(String username, String title, String description, Integer anyo, Class<?> expected) {

        Class<?> caught=null;
        startTransaction();
        try {
            this.authenticate(username);

            final Volume result;

            result = this.volumeService.create();

            result.setTitle(title);
            result.setDescription(description);
            result.setAnyo(anyo);

            this.volumeService.save(result);
            volumeService.flush();


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
    public void driverListVolumeTest() {

        final Object testingData[][] = {
                // Intentando ver los volumes estando deslogueado -> true
                {
                        null, null
                },
                // Intentando ver los volumes logueado como admin -> true
                {
                        "administrator", null
                },
                // Intentando ver los volumes logueado como customer -> true
                {
                        "customer1", null
                }

        };
        for (int i = 0; i < testingData.length; i++)
            this.listVolume((String) testingData[i][0], (Class<?>) testingData[i][1]);
    }

    @Test
    public void driverVolumeCreateTest() {

        final Object testingData[][] = {
                // Crear un volume logueado como usuario -> true
                {
                        "user1","title1" ,"description1", 2018, null
                },
                // Crear un volume sin estar logueado -> false
                {
                        null,"title1" ,"description1", 2018, IllegalArgumentException.class
                },
                // Crear un volume logueado como customer -> False
                {
                        "customer1","title1" ,"description1", 2018, IllegalArgumentException.class
                },
                // Crear un volume con creditCard a null y atributos vacios-> False
                {
                        "user1","" ,"", null, ConstraintViolationException.class
                }


        };
        for (int i = 0; i < testingData.length; i++)
            this.volumeCreate((String) testingData[i][0],(String)testingData[i][1] ,
                    (String)testingData[i][2],(Integer)testingData[i][3],(Class<?>) testingData[i][4]);
    }
}
