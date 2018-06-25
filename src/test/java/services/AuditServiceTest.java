
package services;



import domain.Audit;
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
public class AuditServiceTest extends AbstractTest {

    // The SUT ---------------------------------
    @Autowired
    private AuditService auditService;

    @Autowired
    private UserService userService;

    /*  FUNCTIONAL REQUIREMENT:
     * An administrator writes a Audit, saves it in draft mode, then changes it, and saves it in final mode.  */

    public void auditCreateTest(String username, String title, String moment, String description,
                                   boolean  finalMode, Integer gauge, Class<?> expected) {
        Class<?> caught=null;
        startTransaction();
        try {
            this.authenticate(username);

            Audit result = auditService.create();

            result.setTitle(title);
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            result.setMoment(formatter.parse(moment));
            result.setDescription(description);
            result.setFinalMode(finalMode);
            result.setGauge(gauge);

            auditService.save(result);
            auditService.flush();

        } catch (final Throwable oops) {
            caught = oops.getClass();
        }
        checkExceptions(expected, caught);
        rollbackTransaction();
        unauthenticate();
    }

    /*  FUNCTIONAL REQUIREMENT:
     * An administrator writes a Audit, saves it in draft mode, then changes it, and saves it in final mode.  */

    public void auditEditTest(final String username,Integer gauge,String title, String description,String moment,
                               String auditBean, final Class<?> expected) {
        Class<?> caught = null;

        try {

            this.authenticate(username);

            Audit result= auditService.findOneToEdit(super.getEntityId(auditBean));

            result.setGauge(gauge);
            result.setTitle(title);
            result.setDescription(description);

            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            result.setMoment(formatter.parse(moment));

            this.auditService.save(result);

            this.unauthenticate();

        } catch (final Throwable oops) {

            caught = oops.getClass();

        }

        this.checkExceptions(expected, caught);
    }

    // Drivers
    // ===================================================

    @Test
    public void driverAuditCreateTest() {

        final Date date = new Date();

        final Object testingData[][] = {
                // Crear una nota logueado como admin -> true
                {
                        "administrator","title1","12/03/2020 12:00","description1", false, 2, null
                },
                // Crear una nota logueado como admin con un script -> false
                {
                        "administrator","<script>","12/03/2020 12:00","description1", false, 3, ConstraintViolationException.class
                },
            

        };
        for (int i = 0; i < testingData.length; i++)
            auditCreateTest((String) testingData[i][0],(String)testingData[i][1] ,
                    (String)testingData[i][2],(String)testingData[i][3],(Boolean)testingData[i][4],
                    (Integer) testingData[i][5],(Class<?>) testingData[i][6]);
    }

    @Test
    public void driverAuditEditTest() {

        final Date date = new Date();

        final Object testingData[][] = {
                // Recuperar una nota con modo final a false y editarla -> true
                {
                        "administrator", 2, "description1", "title1","12/03/2020 12:00", "audit2", null
                },
                // Recuperar una nota con modo final a true y editarla -> false
                {
                        "administrator", 3, "description1", "title1","12/03/2020 12:00", "audit3", IllegalArgumentException.class
                },


        };
        for (int i = 0; i < testingData.length; i++)
            auditEditTest((String) testingData[i][0],(Integer)testingData[i][1] ,
                    (String)testingData[i][2],(String)testingData[i][3],(String)testingData[i][4],
                    (String) testingData[i][5], (Class<?>) testingData[i][6]);
    }

}