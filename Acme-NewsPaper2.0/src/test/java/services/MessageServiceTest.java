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
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

@Transactional
@ContextConfiguration(locations = {
        "classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)


public class MessageServiceTest extends AbstractTest {

    // The SUT
    // ====================================================

    @Autowired
    private MessageService messageService;

    @Autowired
    private AdministratorService administratorService;

    @Autowired
    private ActorService actorService;


    /*  FUNCTIONAL REQUIREMENT:
            *  An actor who is authenticated as an administrator must be able to:
                - Broadcast a message to the actors of the system.
    */

    public void broadCastMessage(String username, String subject,String body,String priority, Class<?> expected) {
        Class<?> caught=null;
        startTransaction();
        try {
            this.authenticate(username);
            Administrator admin = administratorService.findByPrincipal();
            Collection<Actor> recipients = actorService.findAll();
            recipients.remove(admin);

            Message message = messageService.create();
            message.setSubject(subject);
            message.setBody(body);
            message.setPriority(priority);
            message.setActorReceivers(recipients);
            messageService.sendBroadcast(message);

            messageService.flush();
            this.unauthenticate();

        } catch (final Throwable oops) {
            caught = oops.getClass();
        }
        this.checkExceptions(expected, caught);
        rollbackTransaction();
    }


    @Test
    public void driverBroadcastMessageTest() {

        final Object testingData[][] = {

                // Intentar mandar un broadcast logueado como admin -> true
                {
                        "administrator","subject","body","HIGH", null
                },
                // Intentar mandar un broadcast sin rellenar el body -> false
                {
                        "administrator","subject","","HIGH", ConstraintViolationException.class
                },
                // Intentar mandar un broadcast logueado como user1 -> false
                {
                        "user1","subject","body","HIGH", IllegalArgumentException.class
                },


        };
        for (int i = 0; i < testingData.length; i++)
            this.broadCastMessage((String) testingData[i][0],(String)testingData[i][1] ,(String)testingData[i][2],
                    (String)testingData[i][3],(Class<?>) testingData[i][4]);
    }

}
