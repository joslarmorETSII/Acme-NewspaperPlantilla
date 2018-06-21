package controllers.User;

import controllers.AbstractController;
import domain.Article;
import domain.NewsPaper;
import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.ArticleService;
import services.NewsPaperService;
import services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

@Controller
@RequestMapping("/article/user")
public class ArticleUserController extends AbstractController{
    // Services --------------------------------------------

    @Autowired
    private ArticleService articleService;

    @Autowired
    private UserService userService;

    @Autowired
    private NewsPaperService newsPaperService;

    // Constructor --------------------------------------------

    public ArticleUserController() {
        super();
    }

    // Creation ------------------------------------------------------

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create() {
        ModelAndView result;
        Article article= null ;
        article = this.articleService.create();
        result = this.createEditModelAndView(article);

        return result;
    }

    // Listing -------------------------------------------------------

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(HttpServletRequest request) {
        ModelAndView result;
        User user;
        Collection<Article> articles=null;

        SimpleDateFormat formatterEs;
        SimpleDateFormat formatterEn;
        String momentEs;
        String momentEn;

        HttpSession session = request.getSession();
        session.setAttribute("cancelUriSession",request.getRequestURI());

        formatterEs = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        momentEs = formatterEs.format(new Date());
        formatterEn = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        momentEn = formatterEn.format(new Date());

        user = userService.findByPrincipal();
        articles=this.articleService.findArticleByUser(user);

        result = new ModelAndView("article/list");
        result.addObject("articles", articles);
        result.addObject("requestURI","article/user/list.do");
        result.addObject("momentEs", momentEs);
        result.addObject("momentEn", momentEn);
        result.addObject("allArticlesView",true);
        result.addObject("cancelUri", "welcome/index.do");

        return result;

    }

    @RequestMapping(value = "/display", method = RequestMethod.GET)
    public ModelAndView display(@RequestParam final int articleId, HttpServletRequest request) {
        ModelAndView result;
        Article article;
        SimpleDateFormat formatterEs;
        SimpleDateFormat formatterEn;
        String momentEs;
        String momentEn;
        User user = userService.findByPrincipal();

        formatterEs = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        momentEs = formatterEs.format(new Date());
        formatterEn = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        momentEn = formatterEn.format(new Date());

        article = this.articleService.findOne(articleId);
        Assert.notNull(article);

        formatterEs = new SimpleDateFormat("dd/MM/yyyy");
        momentEs = formatterEs.format(article.getMoment());
        formatterEn = new SimpleDateFormat("yyyy/MM/dd");
        momentEn = formatterEn.format(article.getMoment());

        result = new ModelAndView("article/display");
        result.addObject("article", article);
        result.addObject("user", user);
        result.addObject("advertisement",newsPaperService.selectRandomAdd(article));
        result.addObject("momentEs", momentEs);
        result.addObject("momentEn", momentEn);
        result.addObject("cancelUriSession", request.getSession().getAttribute("cancelUriSession"));

        return result;
    }

    //  Edition ----------------------------------------------------------------

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(@RequestParam int articleId) {
        final ModelAndView result;
        Article article;
        article = this.articleService.findOneToEdit(articleId);
        Assert.isTrue(!article.getFinalMode());
        Assert.notNull(article);
        result = this.createEditModelAndView(article);
        return result;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
    public ModelAndView save(@Valid Article article, BindingResult binding) {
        ModelAndView result;
        if (binding.hasErrors())
            result = this.createEditModelAndView(article);
        else
            try {
                this.articleService.save(article, article.getNewsPaper());
                result = new ModelAndView("redirect:list.do");
            } catch (final Throwable oops) {
                result = this.createEditModelAndView(article, "article.commit.error");
            }
        return result;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST,params = "delete")
    public ModelAndView edit(Article article){
        ModelAndView result;

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
        result.addObject("actionUri","article/user/edit.do");
        result.addObject("message", messageCode);
        result.addObject("cancelUri","article/user/list.do");

        return result;
    }
}