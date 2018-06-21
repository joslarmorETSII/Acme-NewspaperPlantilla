package services;

import domain.NewsPaper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import utilities.AbstractTest;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

@Transactional
@ContextConfiguration(locations = {"classpath:spring/datasource.xml",
        "classpath:spring/config/packages.xml",
        "classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class UseCaseEditNewspaper extends AbstractTest{

    // The SUT ---------------------------------
    @Autowired private NewsPaperService newsPaperService;

    // Tests ------------------------------------------------------------------

    /*  CASO DE USO:
     *
     *   Un Actor se authentifica como User, lista todos las Newspapers ,
     *   elige uno de los que creado para editar lo edita y lo guarda.
     *
     *
     *
     * COMO SE VA HA HACER?
     *
     * En este caso de uso vamos a hacer tests positivos y negativos:
     *
     * Como caso positivo:
     *
     * � Editar uno de las Newspapers que ha creado logueado como user,
     *   proporcionando datos validos.
     *
     * Para forzar el error pueden darse varios casos negativos, como son:
     *
     * � Intentar editar un Newspaper siendo autenticado como de customer.
     * . Intentar editar un Newspaper de otro user.
     * � Introducir la descripcion y el titulo vac�os.
     * � Introducir un script.
     * � Introducir una url invalida para la picture.
     * �
     */

    public void newspaperEditTest( String username, String title, String description, String picture, Boolean modePrivate, String newspaperBean, Class<?> expected) {

        Class<?> caught = null;
        NewsPaper newspaperToEdit;


        try {
            startTransaction();
            authenticate(username);

            newsPaperService.findAll();
            newspaperToEdit = newsPaperService.findOneToEdit(super.getEntityId(newspaperBean));
            newspaperToEdit.setTitle(title);
            newspaperToEdit.setDescription(description);
            newspaperToEdit.setPicture(picture);
            newspaperToEdit.setModePrivate(modePrivate);
            newsPaperService.save(newspaperToEdit);
            newsPaperService.flush();

            this.unauthenticate();

        } catch (final Throwable oops) {

            caught = oops.getClass();

        }

        this.checkExceptions(expected, caught);
        rollbackTransaction();
    }


    @Test
    public void driverServiseEdit() {

        final Object testingData[][] = {

                // 1. Editar una newspaperp estando logueado como User  -> true

                {
                        "user1", "title edited", "description1", "http://www.picture.com",false,"newsPaper4", null
                },
                // 2. Editar una newspaper estando logueado como User sin especificar la foto -> true
                {
                        "user1", "title edited", "description1", null, false, "newsPaper4", null
                },
                // 3. Editar una newspaper sin loguearse -> false
                {
                        null, "title edited", "description1", "http://www.picture.com", true, "newsPaper4", IllegalArgumentException.class
                },
                // 4. Editar una newspaper sin rellenar un campo obligatorio -> false
                {
                        "user1", "", "description1", null, false, "newsPaper4", ConstraintViolationException.class
                },

                // 5. Editar una newspaper estando logueado como customer -> false
                {
                        "customer1", "title1", "description1", null, true, "newsPaper4", IllegalArgumentException.class
                },

                // 6. Editar una newspaper de otro usuario -> false
                {
                        "user3", "title2", "description1", "http://www.picture.com", false, "newsPaper4", IllegalArgumentException.class
                },

                // 7. Editar una news paper estando logueado como customer -> false
                {
                        "user3", "title2", "description1", "http://www.picture3.com", false, "newsPaper4", IllegalArgumentException.class
                },

                // 8. Editar una newspaper proporcionando una url no valida de la picture-> false
                {
                        "user1", "title edited", "description1", "htpt://www.picture.com",false,"newsPaper4", ConstraintViolationException.class
                },

                // 9. Editar una newspaper con un srcipt-> false
                {
                        "user1", "<script>", "description1", "http://www.picture.com",false,"newsPaper4", ConstraintViolationException.class
                },
                // 10. Editar una newspaper publica y ponerla privada -> true
                {
                        "user1", "Dailymail", "news paper privada", "http://www.picture.com",true,"newsPaper4", null
                },


        };
        for (int i = 0; i < testingData.length; i++)
            this.newspaperEditTest((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Boolean)testingData[i][4],
                    (String) testingData[i][5], (Class<?>) testingData[i][6]);
    }

}