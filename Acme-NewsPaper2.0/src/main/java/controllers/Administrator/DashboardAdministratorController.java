package controllers.Administrator;

import controllers.AbstractController;
import domain.Article;
import domain.NewsPaper;
import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import services.AdministratorService;

import java.util.Collection;

@Controller
@RequestMapping("/administrator")
public class DashboardAdministratorController extends AbstractController{

    // Services --------------------------------------------

    @Autowired
    private AdministratorService administratorService;

    // Constructor -----------------------------------------
    public DashboardAdministratorController() {
        super();
    }

    // Dashboard -------------------------------------------------------------
    @RequestMapping("/dashboard")
    public ModelAndView dashboard() {
        ModelAndView result;

        result = new ModelAndView("administrator/dashboard");

        Double[]  avgStdOfNewspapersPerUser;
        Double[]  avgStdOfArticles;
        Double[]  avgStdOfArticlesPerNewspaper;
        Collection<NewsPaper> newspapersWith10PercentMoreArticlesThanAvg;
        Collection<NewsPaper> newspapersWith10PercentFewerArticlesThanAvg;
        String ratioOfUsersThatCreatedNewspaper;
        Double ratioOfUserCreatingArticle;
        Double avgFollowUpsPerArticle;
        Double avgFollowUpsPerArticleAfter1weekNewspaprerPublished;
        Double avgFollowUpsPerArticleAfter2weekNewspaprerPublished;
        Double[] avgStdChirpsPerUser;
        Double ratioUsersWith75PercentMoreChirpsPostedThanAVG;
        Double ratioPublicVSPrivateNewspapers;
        Double avgArticlesPerNewsPapersPrivate;
        Double avgArticlesPerNewsPapersPublic;
        Double ratioPrivateNewsPapersVsCustomers;
        Double ratioPrivateNewsPapersVsPublicPerPublisher;

        avgStdOfNewspapersPerUser = this.administratorService.avgStdOfNewspapersPerUser();
        avgStdOfArticles = this.administratorService.avgStdOfArticles();
        avgStdOfArticlesPerNewspaper = this.administratorService.avgStdOfArticlesPerNewspaper();
        newspapersWith10PercentMoreArticlesThanAvg = this.administratorService.newspapersWith10PercentMoreArticlesThanAvg();
        newspapersWith10PercentFewerArticlesThanAvg = this.administratorService.newspapersWith10PercentFewerArticlesThanAvg();
        ratioOfUsersThatCreatedNewspaper = this.administratorService.ratioOfUsersThatCreatedNewspaper();
        ratioOfUserCreatingArticle = this.administratorService.ratioOfUserCreatingArticle();
        avgFollowUpsPerArticle = this.administratorService.avgFollowUpsPerArticle();
        avgFollowUpsPerArticleAfter1weekNewspaprerPublished = this.administratorService.avgFollowUpsPerArticleAfter1weekNewspaprerPublished();
        avgFollowUpsPerArticleAfter2weekNewspaprerPublished = this.administratorService.avgFollowUpsPerArticleAfter2weekNewspaprerPublished();
        avgStdChirpsPerUser = this.administratorService.avgStdChirpsPerUser();
        ratioUsersWith75PercentMoreChirpsPostedThanAVG = this.administratorService.ratioUsersWith75PercentMoreChirpsPostedThanAVG();
        ratioPublicVSPrivateNewspapers = this.administratorService.ratioPublicVSPrivateNewspapers();
        avgArticlesPerNewsPapersPrivate = this.administratorService.avgArticlesPerNewsPapersPrivate();
        avgArticlesPerNewsPapersPublic = this.administratorService.avgArticlesPerNewsPapersPublic();
        ratioPrivateNewsPapersVsCustomers = this.administratorService.ratioPrivateNewsPapersVsCustomers();
        ratioPrivateNewsPapersVsPublicPerPublisher = this.administratorService.ratioPrivateNewsPapersVsPublicPerPublisher();


        result.addObject("avgStdOfNewspapersPerUser", avgStdOfNewspapersPerUser);
        result.addObject("avgStdOfArticles", avgStdOfArticles);
        result.addObject("avgStdOfArticlesPerNewspaper", avgStdOfArticlesPerNewspaper);
        result.addObject("newspapersWith10PercentMoreArticlesThanAvg", newspapersWith10PercentMoreArticlesThanAvg);
        result.addObject("newspapersWith10PercentFewerArticlesThanAvg", newspapersWith10PercentFewerArticlesThanAvg);
        result.addObject("ratioOfUsersThatCreatedNewspaper", ratioOfUsersThatCreatedNewspaper);
        result.addObject("ratioOfUserCreatingArticle", ratioOfUserCreatingArticle);
        result.addObject("avgFollowUpsPerArticle", avgFollowUpsPerArticle);
        result.addObject("avgFollowUpsPerArticleAfter1weekNewspaprerPublished", avgFollowUpsPerArticleAfter1weekNewspaprerPublished);
        result.addObject("avgFollowUpsPerArticleAfter2weekNewspaprerPublished", avgFollowUpsPerArticleAfter2weekNewspaprerPublished);
        result.addObject("avgStdChirpsPerUser", avgStdChirpsPerUser);
        result.addObject("ratioUsersWith75PercentMoreChirpsPostedThanAVG",ratioUsersWith75PercentMoreChirpsPostedThanAVG);
        result.addObject("ratioPublicVSPrivateNewspapers",ratioPublicVSPrivateNewspapers);
        result.addObject("avgArticlesPerNewsPapersPrivate",avgArticlesPerNewsPapersPrivate);
        result.addObject("avgArticlesPerNewsPapersPublic",avgArticlesPerNewsPapersPublic);
        result.addObject("ratioPrivateNewsPapersVsCustomers",ratioPrivateNewsPapersVsCustomers);
        result.addObject("avgServisesInEachCategory",ratioPrivateNewsPapersVsPublicPerPublisher);

        //  Newspaper 2.0 -------------------------------------------------

        result.addObject("ratioNpAdvertisementsVsNpWithOut",administratorService.ratioNpAdvertisementsVsNpWithOut());
        result.addObject("ratioAdverTabooVsAdvertisement",administratorService.ratioAdverTabooVsAdvertisement());
        result.addObject("avgNewsPapersPerVolume",administratorService.avgNewsPapersPerVolume());
        result.addObject("rationSubscribedNewsPVsSubscribedVolume",administratorService.rationSubscribedNewsVsSubscribedVolume());

        return result;
    }
}
