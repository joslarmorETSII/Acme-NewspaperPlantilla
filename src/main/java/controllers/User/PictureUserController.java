package controllers.User;

import controllers.AbstractController;
import domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/picture/user")
public class PictureUserController extends AbstractController {
    // Services --------------------------------------------

    @Autowired
    private UserService userService;

    @Autowired
    private PictureService pictureService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private FollowUpService followUpService;

    // Constructor --------------------------------------------

    public PictureUserController() {
        super();
    }

    // Creation ------------------------------------------------------

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create(@RequestParam(value = "articleId",  required = false) Integer articleId,@RequestParam(value = "followUpId", required = false) Integer followUpId,HttpServletRequest request) {
        ModelAndView result;
        Picture picture ;
        Article article;
        FollowUp followUp;
        User principal = userService.findByPrincipal();
        picture = this.pictureService.create();
        if(articleId!=null){
           article= articleService.findOne(articleId);
           picture.setArticle(article);
           Assert.isTrue(article.getNewsPaper().getPublisher().equals(principal));
        }else{
            followUp = followUpService.findOne(followUpId);
            picture.setFollowUp(followUp);
            Assert.isTrue(followUp.getArticle().getNewsPaper().getPublisher().equals(principal));

        }

        result = this.createEditModelAndView(picture,request);

        return result;
    }

    //  Edition ----------------------------------------------------------------

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(@RequestParam int pictureId, HttpServletRequest request) {
        final ModelAndView result;
        Picture picture;
        picture = this.pictureService.findOneToEdit(pictureId);
        Assert.notNull(picture);
        result = this.createEditModelAndView(picture, request);
        return result;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
    public ModelAndView save(@Valid Picture picture, BindingResult binding, HttpServletRequest request) {
        ModelAndView result;
        if (binding.hasErrors())
            result = this.createEditModelAndView(picture,request);
        else
            try {
                if(picture.getArticle() != null) {
                    this.pictureService.savePictureArticle(picture, picture.getArticle());
                    result = new ModelAndView("redirect: ../../article/user/list.do");

                }else{
                    this.pictureService.savePictureFollowUp(picture, picture.getFollowUp());
                    result = new ModelAndView("redirect: ../../followUp/user/list.do");
                }
            } catch (final Throwable oops) {
                result = this.createEditModelAndView(picture, "general.commit.error",request);
            }
        return result;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
    public ModelAndView edit(Picture picture,HttpServletRequest request) {
        ModelAndView result;

        try {
            pictureService.delete(picture);
            result = new ModelAndView("redirect:list.do");
        } catch (Throwable oops) {
            result = createEditModelAndView(picture, "general.commit.error",request);
        }

        return result;
    }

    // Ancillary methods ------------------------------------------------------

    protected ModelAndView createEditModelAndView(final Picture picture, HttpServletRequest request) {
        ModelAndView result;

        result = this.createEditModelAndView(picture, null, request);
        return result;
    }

    protected ModelAndView createEditModelAndView(final Picture picture, final String messageCode, HttpServletRequest request) {
        ModelAndView result;

        result = new ModelAndView("picture/edit");
        result.addObject("picture", picture);
        result.addObject("actionUri", "picture/user/edit.do");
        result.addObject("message", messageCode);
        result.addObject("cancelUriSession", request.getSession().getAttribute("cancelUriSession"));

        return result;
    }
}