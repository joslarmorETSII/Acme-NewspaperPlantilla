package controllers;

import domain.*;
import forms.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Controller
@RequestMapping("/user")
public class UserController extends AbstractController{

    // Services --------------------------------------------

    @Autowired
    private UserService userService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private NewsPaperService    newsPaperService;

    @Autowired
    private ActorService actorService;

    @Autowired
    private ConfigurationService configurationService;

    // Constructor -----------------------------------------
    public UserController() {
        super();
    }

//Edition --------------------------------------------------------------------

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView edit() {

        ModelAndView result;
        result = new ModelAndView("user/edit");

        result.addObject("userForm", new UserForm());

        return result;
    }

    // Save ------------------------------------------------------------------------

    @RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
    public ModelAndView save(@Valid final UserForm userForm, final BindingResult binding) {
        ModelAndView result;
        User user;

        try {
            user = this.userService.reconstruct(userForm, binding);

            if (binding.hasErrors())
                result = createEditModelAndView(userForm);
            else {
                result = new ModelAndView("redirect:/welcome/index.do");

                user = this.userService.save(user);

            }
        } catch (final Throwable oops) {
            if(oops.getCause().getCause().getMessage().contains("Duplicate entry"))
                result = createEditModelAndView(userForm,"user.duplicated.username");
            else
                result = this.createEditModelAndView(userForm, "user.commit.error");
        }

        return result;
    }

    // Listing -------------------------------------------------------


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(HttpServletRequest request) {
        ModelAndView result;

        HttpSession session = request.getSession();
        session.setAttribute("cancelUriSession", request.getRequestURI());

        result = new ModelAndView("user/list");
        result.addObject("users", userService.findAll());
        result.addObject("requestURI", "user/list.do");
        return result;

    }

    @RequestMapping(value = "/display", method = RequestMethod.GET)
    public ModelAndView display(@RequestParam Integer userId) {
        ModelAndView result;
        User user;
        Collection<Article> articles;

        user = userService.findOne(userId);
        articles = articleService.findPublishArticlesByUserId(userId);
        result = new ModelAndView("user/display");
        result.addObject("user", user);
        result.addObject("articles", articles);
        result.addObject("chirps",user.getChirps());
        result.addObject("requestURI", "user/display.do");

        return result;
    }
    // Search ---------------------------------------------------------------

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ModelAndView search(@RequestParam String keyword){
        ModelAndView result;

        result = new ModelAndView("article/search");

        result.addObject("keyword", keyword);
        result.addObject("articles", articleService.findbyTitleAndBodyAndSummary(keyword));
        result.addObject("newsPapers",newsPaperService.searchNewspapers(keyword));
        result.addObject("requestURI", "user/search.do");
        result.addObject("cancelURI", "welcome/index.do");

        return result;
    }


    // Ancillary methods ------------------------------------------------------------

    private ModelAndView createEditModelAndView( UserForm userForm) {
        return createEditModelAndView(userForm,null);
    }

        private ModelAndView createEditModelAndView( UserForm userForm,  String message) {

        ModelAndView result = new ModelAndView("user/edit");

        result.addObject("userForm", userForm);
        result.addObject("message", message);
        return result;
    }






}