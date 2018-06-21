package services;

import domain.Actor;
import domain.Administrator;
import domain.Configuration;
import domain.Folder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import utilities.AbstractTest;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import java.util.Collection;

@Transactional
@ContextConfiguration(locations = {
        "classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class FolderServiceTest extends AbstractTest{

    // The SUT
    // ====================================================

    @Autowired
    private PictureService pictureService;

    @Autowired
    private FolderService folderService;

    @Autowired
    private ActorService actorService;

    @Autowired
    private FollowUpService followUpService;

    // Tests
    // ====================================================


     /*  FUNCTIONAL REQUIREMENT:
            *  An actor who is authenticated must be able to:
                - Manage his or her message folders, except for the system folders.
    */


    // Create A Folder
    public void createFolder(String username,String folderName, Class<?> expected) {

        Class<?> caught=null;
        startTransaction();
        try {
            this.authenticate(username);
            Actor principal = actorService.findByPrincipal();
            Folder folder = folderService.create(principal);

            folder.setName(folderName);
            folderService.save(folder);
            folderService.flush();

        } catch (final Throwable oops) {
            caught = oops.getClass();
        }
        this.checkExceptions(expected, caught);
        rollbackTransaction();
    }

    // Edit A Folder
    public void editFolder(String username,String folderName,String folderBean, Class<?> expected) {

        Class<?> caught=null;
        startTransaction();
        try {
            this.authenticate(username);
            Folder folder = folderService.findOne(getEntityId(folderBean));

            folder.setName(folderName);
            folderService.save(folder);
            folderService.flush();

        } catch (final Throwable oops) {
            caught = oops.getClass();
        }
        this.checkExceptions(expected, caught);
        rollbackTransaction();
    }

    //Drivers
    // ===================================================

    @Test
    public void driverCreateFolder() {

        final Object testingData[][] = {
                // 1. Crear  un Folder logueado como user1 -> true
                {
                        "user1","newFolder", null
                },
                // 2. Crear  un Folder logueado sin proporcionar nombre -> true
                {
                        "agent1","", ConstraintViolationException.class
                },
                // 3. Crear  un Folder sin loguearse -> true
                {
                        null,"newFolder", IllegalArgumentException.class
                },

        };
        for (int i = 0; i < testingData.length; i++)
            this.createFolder((String) testingData[i][0],(String) testingData[i][1], (Class<?>) testingData[i][2]);
    }

    @Test
    public void driverEditFolder() {

        final Object testingData[][] = {
                // 1. Editar un Folder que ha creado el usuario estando logueado -> true
                {
                        "user4","newFolder","folderTest", null
                },
                // 1. Editar un Folder de otro usuario -> true
                {
                        "customer","edited Folder","folderTest", IllegalArgumentException.class
                },
                // 1. Editar un Folder del systema -> true
                {
                        "user4","newFolder","inbox2", IllegalArgumentException.class
                },

        };
        for (int i = 0; i < testingData.length; i++)
            this.editFolder((String) testingData[i][0],(String) testingData[i][1],(String) testingData[i][2],
                    (Class<?>) testingData[i][3]);
    }
}
