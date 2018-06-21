package controllers.Administrator;

import controllers.AbstractController;
import domain.Administrator;
import domain.Article;
import domain.NewsPaper;
import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.AdministratorService;
import services.ArticleService;
import services.NewsPaperService;
import services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

@Controller
@RequestMapping("/article/administrator")
public class ArticleAdministratorController extends AbstractController {
    // Services --------------------------------------------

    @Autowired
    private ArticleService articleService;

    @Autowired
    private AdministratorService administratorService;

    @Autowired
    private UserService userService;

    @Autowired
    private  NewsPaperService newsPaperService;


    // Constructor --------------------------------------------

    public ArticleAdministratorController() {
        super();
    }

    // Listing -------------------------------------------------------

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(HttpServletRequest request) {
        ModelAndView result;
        Collection<Article> articlesTaboo = articleService.findArticleByTabooIsTrue();
        Collection<Article> allArticles = articleService.findPublishArticles();
        SimpleDateFormat formatterEs;
        SimpleDateFormat formatterEn;
        String momentEs;
        String momentEn;

        formatterEs = new SimpleDateFormat("dd/MM/yyyy");
        momentEs = formatterEs.format(new Date());
        formatterEn = new SimpleDateFormat("yyyy/MM/dd");
        momentEn = formatterEn.format(new Date());

        HttpSession session = request.getSession();
        session.setAttribute("cancelUriSession", request.getRequestURI());

        result = new ModelAndView("article/list");
        result.addObject("articles", articlesTaboo);
        result.addObject("allArticles", allArticles);
        result.addObject("requestURI","article/administrator/list.do");

        result.addObject("momentEs", momentEs);
        result.addObject("momentEn", momentEn);

        return result;

    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(@RequestParam int articleId){
        ModelAndView result;
        Article article= articleService.findOne(articleId);
        try{

            articleService.delete(article);
            result = new ModelAndView("redirect:list.do");
        }catch (Throwable oops){
            result = createEditModelAndView(article,"article.commit.error");
        }

        return result;
    }

    // Ancillary methods ------------------------------------------------------

    protected ModelAndView createEditModelAndView(final Article article) {
        ModelAndView result;

        result = this.createEditModelAndView(article, null);
        return result;
    }

    protected ModelAndView createEditModelAndView(final Article article, final String messageCode) {
        ModelAndView result;
        User user = userService.findByPrincipal();

        Collection<NewsPaper> newsPapers = this.newsPaperService.findAllNewsPaperByUserAndNotPublished(user.getId());

        result = new ModelAndView("article/edit");
        result.addObject("article", article);
        result.addObject("newsPapers", newsPapers);
        result.addObject("actionUri","article/administrator/edit.do");
        result.addObject("message", messageCode);

        return result;
    }
}