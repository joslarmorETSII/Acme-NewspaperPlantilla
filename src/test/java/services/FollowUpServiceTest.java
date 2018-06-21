package services;

import domain.Article;
import domain.FollowUp;
import domain.Picture;
import domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import utilities.AbstractTest;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Collection;

@Transactional
@ContextConfiguration(locations = {
        "classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class FollowUpServiceTest extends AbstractTest{
    // The SUT
    // ====================================================

    @Autowired
    private ArticleService articleService;

    @Autowired
    private UserService userService;

    @Autowired
    private FollowUpService followUpService;

    @Autowired
    private PictureService pictureService;
    // Tests
    // ====================================================

     /*  FUNCTIONAL REQUIREMENT:
            . The writer of an article may write follow-ups on it.
        */


    public void followUpCreateTest(String username, String title,String summary,String text,String url,String articleBean , Class<?> expected){
        Class<?> caught = null;
        startTransaction();
    try {

        this.authenticate(username);
        Article article = articleService.findOne(getEntityId(articleBean));
        Collection<Picture> pictures = new ArrayList<>();
        FollowUp followUp = followUpService.create();
        Picture picture1 = pictureService.create();
        picture1.setUrl(url);
        pictures.add(picture1);

        followUp.setTitle(title);
        followUp.setSummary(summary);
        followUp.setText(text);
        followUp.setArticle(article);
        followUp.setPictures(pictures);

        followUpService.save(followUp);
        followUpService.flush();

        this.unauthenticate();

    }   catch (final Throwable oops) {
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
            // Crear un followUp estando logueado como user1 -> true
            {
                    "user1", "title1","summary","text","https://www.picture.es/image.jpg","article2", null
            },
            // Crear un followUp si rellenar el titulo -> false
            {
                    "user1", "","summary","text","https://www.picture.es/image.jpg","article1", ConstraintViolationException.class
            },
            // Crear un followUp sin loguearse -> true
            {
                    null, "title1","summary","text","https://www.picture.es/image.jpg","article2", IllegalArgumentException.class
            },


        };
        for (int i = 0; i < testingData.length; i++)
            this.followUpCreateTest((String) testingData[i][0], (String) testingData[i][1],(String) testingData[i][2],(String)testingData[i][3] , (String)testingData[i][4]
                    , (String)testingData[i][5],(Class<?>) testingData[i][6]);

    }

}
