package services;

import domain.Agent;
import domain.User;
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

public class AgentServiceTest extends AbstractTest {

    // The SUT
    // ====================================================

    @Autowired
    private AgentService agentService;

    // Tests
// ====================================================

     /*  FUNCTIONAL REQUIREMENT:
            * An actor who is not authenticated must be able to:
               -. Register to the system as an agent.
    */

    public void agentRegisterTest(final String username, final String password, final String passwordRepeat, final String name, final String surname, final String phone, final String email, final String postalAddress,  final Class<?> expected) {
        Class<?> caught = null;
        startTransaction();
        try {

            final Agent result;

            result = this.agentService.create();

            result.getUserAccount().setUsername(username);
            result.setName(name);
            result.setSurname(surname);
            result.setPhone(phone);
            result.setEmail(email);
            result.setPostalAddresses(postalAddress);

            result.getUserAccount().setPassword(new Md5PasswordEncoder().encodePassword(password, null));

            this.agentService.save(result);
            agentService.flush();

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
    public void driverAgentRegisterTest() {

        final Object testingData[][] = {
                // Alguien sin registrar/logueado -> true
                {
                        "agent33", "agent33", "agent33", "agentTestName", "agentTestSurname", "+34123456789", "agentTest@agentTest.com", "addressAgent",  null
                },
                // Todos los campos como null --> false
                {
                        null, null, null, null, null, null, null, null, ConstraintViolationException.class
                },
                // Todos los campos completados, excepto la direccion postal -> true
                {
                        "agentTest3", "agentTest3", "agentTest3", "agentTestName3", "agentTestSurname3","+34123456789", "agentTest@agentTest.com", "",  null
                },
                // Username size menor que 5-> false
                {
                        "age", "agentTest3", "agentTest3", "agentTestName3", "agentTestSurname3","+34123456789", "agentTest@agentTest.com", "",  ConstraintViolationException.class
                },
                // Todos los campos completados, introduciendo un <script> en el nombre -> false
                {
                        "agent343", "agent343", "agent343", "<script>", "agentTestSurname43","+34123456789", "agentTest@agentTest.com", "",  ConstraintViolationException.class
                },

        };
        for (int i = 0; i < testingData.length; i++)
            this.agentRegisterTest((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
                    (String) testingData[i][7], (Class<?>) testingData[i][8]);
    }

}