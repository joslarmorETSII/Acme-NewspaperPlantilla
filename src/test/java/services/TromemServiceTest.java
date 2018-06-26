package services;

import domain.Chirp;
import domain.Tromem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import utilities.AbstractTest;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Transactional
@ContextConfiguration(locations = {
        "classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class TromemServiceTest extends AbstractTest {

    // The SUT ---------------------------------
    @Autowired
    private TromemService tromemService;

    @Autowired
    private UserService userService;

    /*  FUNCTIONAL REQUIREMENT:
     * An administrator writes a Tromem, saves it in draft mode, then changes it, and saves it in final mode.  */

    public void tromemCreateEditTest(String username, String title, String moment, String description,
                                boolean  finalMode, Integer gauge, Class<?> expected) {
        Class<?> caught=null;
        startTransaction();
        try {
            this.authenticate(username);

            Tromem result = tromemService.create();

            result.setTitle(title);
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            result.setDisplayMoment(formatter.parse(moment));
            result.setDescription(description);
            result.setFinalMode(finalMode);
            result.setGauge(gauge);

            Tromem saved= tromemService.save(result);
            tromemService.flush();

            // editar
            saved.setFinalMode(true);
            tromemService.save(saved);
            tromemService.flush();

        } catch (final Throwable oops) {
            caught = oops.getClass();
        }
        checkExceptions(expected, caught);
        rollbackTransaction();
        unauthenticate();
    }

    // Drivers
    // ===================================================

    @Test
    public void driverTromemCreateEditTest() {

        final Date date = new Date();

        final Object testingData[][] = {
                // Crear una nota logueado como admin y editarla -> true
                {
                        "administrator","title1","12/03/2020 12:00","description1", false, 2, null
                },
                // Crear una nota logueado como admin con un script y editarla -> false
                {
                        "administrator","<sql>","12/03/2020 12:00","description1", false, 3, ConstraintViolationException.class
                },
                // Crear una nota sin loguearse y editarla -> false
                {
                        null,"Title","12/03/2020 12:00","description1", false, 2, IllegalArgumentException.class
                },
                // Crear una nota con el momento en el pasado logueado como admin y editarla -> false
                {
                        "admin2","Title","12/03/2017 12:00","description1", false, 1, ConstraintViolationException.class
                },

        };
        for (int i = 0; i < testingData.length; i++)
            tromemCreateEditTest((String) testingData[i][0],(String)testingData[i][1] ,
                    (String)testingData[i][2],(String)testingData[i][3],(Boolean)testingData[i][4],
                    (Integer) testingData[i][5],(Class<?>) testingData[i][6]);
    }


}
