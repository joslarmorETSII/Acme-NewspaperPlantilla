package services;

import com.sun.xml.internal.bind.v2.TODO;
import domain.Article;
import domain.FollowUp;
import domain.NewsPaper;
import domain.Picture;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
public class PictureServiceTest extends AbstractTest{

    // The SUT
    // ====================================================

    @Autowired
    private PictureService pictureService;

    @Autowired
    private NewsPaperService newsPaperService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private FollowUpService followUpService;

    // Tests
    // ====================================================
      /*  FUNCTIONAL REQUIREMENT:
        An actor who is authenticated as a user must be able to:
            . Write Articles, For each article, the system must store a title, the moment when it is published, a summary, a piece of text (the body),
             and some optional PICTURES.
    */

    public void addPictureToArticle(String username,String url, String articleTosubscribeBean, Class<?> expected) {

        Class<?> caught=null;
        startTransaction();
        try {
            this.authenticate(username);

            Article article = articleService.findOne(getEntityId(articleTosubscribeBean));
            Picture picture = pictureService.create();

            picture.setUrl(url);
            picture.setArticle(article);
            pictureService.savePictureArticle(picture,article);

            pictureService.flush();


        } catch (final Throwable oops) {
            caught = oops.getClass();
        }
        this.checkExceptions(expected, caught);
        rollbackTransaction();
    }

    //Drivers
    // ===================================================

    @Test
    public void driverAddPictureToArticle() {



        final Object testingData[][] = {
                // 1. Añadir picture a un articulo logueado como user1 -> true
                {
                        "user1","http://www.banner.com/image.jpg","article4", null
                },
                // 2. Añadir picture a un articulo de otro usuario  -> false
                {
                        "user2","http://www.banner.com/image.jpg","article4", IllegalArgumentException.class
                },
                // 3. Añadir picture a un articulo logueado como Customer -> true
                {
                        "customer1","http://www.banner.com/image.jpg","article4", IllegalArgumentException.class
                },

        };
        for (int i = 0; i < testingData.length; i++)
            this.addPictureToArticle((String) testingData[i][0],(String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
    }

    /*
        Add Picture to FollowUps
     */
    public void addPictureToFollowUp(String username,String url, String followUpTosubscribeBean, Class<?> expected) {

        Class<?> caught=null;
        startTransaction();
        try {
            this.authenticate(username);

            FollowUp followUp = followUpService.findOne(getEntityId(followUpTosubscribeBean));
            Picture picture = pictureService.create();

            picture.setUrl(url);
            picture.setFollowUp(followUp);
            pictureService.savePictureFollowUp(picture,followUp);

            pictureService.flush();


        } catch (final Throwable oops) {
            caught = oops.getClass();
        }
        this.checkExceptions(expected, caught);
        rollbackTransaction();
    }

    //Drivers
    // ===================================================

    @Test
    public void driverAddPictureToFollowUp() {



        final Object testingData[][] = {
                // 1. Añadir picture a un followUp logueado como user1 -> true
                {
                        "user1","http://www.banner.com/image.jpg","followUp1", null
                },
                // 2. Añadir picture a un followUp de otro usuario  -> false
                {
                        "user2","http://www.banner.com/image.jpg","followUp1", IllegalArgumentException.class
                },
                // 3. Añadir picture a un followUp introduciendo un script -> true
                {
                        "user1","<sql>","followUp1", ConstraintViolationException.class
                },

        };
        for (int i = 0; i < testingData.length; i++)
            this.addPictureToFollowUp((String) testingData[i][0],(String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
    }


}
