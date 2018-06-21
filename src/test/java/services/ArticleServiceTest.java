package services;

import domain.*;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.ActorService;
import services.ArticleService;
import services.NewsPaperService;
import services.UserService;
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
public class ArticleServiceTest extends AbstractTest {


    @Autowired
    private ArticleService articleService;

    @Autowired
    private ActorService actorService;

    @Autowired
    private NewsPaperService newsPaperService;

    @Autowired
    private UserService userService;

// Tests
// ====================================================

 /*  FUNCTIONAL REQUIREMENT:
            * An actor who is authenticated as a user must be able to:
               -. Write an article and attach it to any newspaper that has not been published, yet.
                   Note that articles may be saved in draft mode, which allows to modify them later, or
                   final model, which freezes them forever.
    */

    @SuppressWarnings("deprecation")
    public void articleCreateTest(final String username, final String title, String body, String summary1, String moment1, Boolean res1, Boolean res2, final Class<?> expected) {
        Class<?> caught = null;
        startTransaction();
        try {

            this.authenticate(username);
            User user = userService.findByPrincipal();

            final Article result = this.articleService.create();

            result.setTitle(title);
            result.setSummary(summary1);

            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            result.setMoment(formatter.parse(moment1));
            result.setBody(body);

            result.setFinalMode(res1);
            result.setTaboo(res2);


            NewsPaper newsPaper = newsPaperService.findAllNewsPaperByUserAndNotPublished(user.getId()).iterator().next();
            Article a = this.articleService.save(result, newsPaper);
            a = result;

           this.unauthenticate();

        } catch (final Throwable oops) {

            caught = oops.getClass();

        }

        this.checkExceptions(expected, caught);
        rollbackTransaction();
    }

      /*  FUNCTIONAL REQUIREMENT:
            * An actor who is authenticated as a admin must be able to:
                -.Remove an article that he or she thinks is inappropriate.

    */

    public void deleteArticleTest(final String username, String articleBean,Boolean finalMode, final Class<?> expected) {
        Class<?> caught = null;
        startTransaction();

        try {
            Article result= articleService.findOne(super.getEntityId(articleBean));

            this.authenticate(username);


            this.articleService.delete(result);

            articleService.flush();
            this.unauthenticate();

        } catch (final Throwable oops) {

            caught = oops.getClass();

        }

        this.checkExceptions(expected, caught);
        rollbackTransaction();
    }

    /*  FUNCTIONAL REQUIREMENT:
       * An actor who is authenticated as an administrator must be able to:
               - List the articles that contain taboo words.
    */

    public void listArticleTabooWords(final String username,final Class<?> expected){
        Class<?> caught = null;
        startTransaction();
        try {
            this.authenticate(username);

            this.articleService.findArticleByTabooIsTrue();

            articleService.flush();
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
    public void driverArticleCreateTest() {


        final Object testingData[][] = {
                // Crear un articulo estando logueado como user -> true
                {
                        "user1", "title1","body","summary","12/03/2020 12:00",true,false, null
                },
                // Crear un articulo sin estar logueado --> false
                {
                        null,"title1","body","summary","12/03/2020 12:00",true,false, IllegalArgumentException.class
                },
                // Crear un articulo logueado como manager  -> false
                {
                        "manager1","title1","body","summary","12/03/2020 12:00",true,false, IllegalArgumentException.class
                },


        };
        for (int i = 0; i < testingData.length; i++)
            this.articleCreateTest((String) testingData[i][0], (String) testingData[i][1],(String) testingData[i][2],(String)testingData[i][3] , (String)testingData[i][4],(Boolean) testingData[i][5],(Boolean) testingData[i][6], (Class<?>) testingData[i][7]);

    }

    @Test
    public void driverDeleteArticleTest() {

        final Object testingData[][] = {
                // Borrar un articulo estando logueado como admin -> true
                {
                        "administrator", "article1",true, null
                },
                // Borrar un articulo sin estar logueado -> false
                {
                        null, "article1",true, IllegalArgumentException.class
                },
                // Borrar un articulo que no esta en modo final -> false
                {
                        "administrator1","article1",false, IllegalArgumentException.class
                },

        };
        for (int i = 0; i < testingData.length; i++)
            this.deleteArticleTest((String) testingData[i][0], (String) testingData[i][1],(Boolean) testingData[i][2], (Class<?>) testingData[i][3]);
    }

    @Test
    public void driverListArticlesTabooWordsTest() {

        final Object testingData[][] = {
                // Intentando ver los articulos taboo como administrator -> true
                {
                        "administrator", null
                },
                // Intentando ver los articulos taboo como user2 -> false
                {
                        "user2", IllegalArgumentException.class
                },
                // Intentando ver los articulos taboo como customer1 -> false
                {
                        "customer1", IllegalArgumentException.class
                }

        };
        for (int i = 0; i < testingData.length; i++)
            this.listArticleTabooWords((String) testingData[i][0], (Class<?>) testingData[i][1]);
    }
}