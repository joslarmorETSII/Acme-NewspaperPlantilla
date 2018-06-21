package controllers.User;

import controllers.AbstractController;
import domain.Article;
import domain.Chirp;
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
import services.ChirpService;
import services.NewsPaperService;
import services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

@Controller
@RequestMapping("/chirp/user")
public class ChirpUserController extends AbstractController {
    // Services --------------------------------------------

    @Autowired
    private UserService userService;

    @Autowired
    private ChirpService chirpService;

    // Constructor --------------------------------------------

    public ChirpUserController() {
        super();
    }

    // Creation ------------------------------------------------------

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create() {
        ModelAndView result;
        Chirp chirp = null;
        chirp = this.chirpService.create();
        result = this.createEditModelAndView(chirp);

        return result;
    }

    // Listing -------------------------------------------------------

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(HttpServletRequest request) {
        ModelAndView result;
        User user;
        Collection<Chirp> chirps = null;
        Collection<Chirp> chirpsFollowing = null;

        SimpleDateFormat formatterEs;
        SimpleDateFormat formatterEn;
        String momentEs;
        String momentEn;


        formatterEs = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        momentEs = formatterEs.format(new Date());
        formatterEn = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        momentEn = formatterEn.format(new Date());

        user = userService.findByPrincipal();
        chirps = this.chirpService.findChirpsByUserId(user.getId());
        chirpsFollowing = this.chirpService.findAllChirpsByFollowings(user.getId());

        HttpSession session = request.getSession();
        session.setAttribute("cancelUriSession", request.getRequestURI());

        result = new ModelAndView("chirp/list");
        result.addObject("chirps", chirps);
        result.addObject("chirpsFollowing", chirpsFollowing);
        result.addObject("requestURI", "chirp/user/list.do");
        result.addObject("momentEs", momentEs);
        result.addObject("momentEn", momentEn);
        result.addObject("cancelUri","welcome/index.do");

        return result;

    }

    @RequestMapping(value = "/post", method = RequestMethod.GET)
    public ModelAndView post(@RequestParam  int chirpId) {
        final ModelAndView result;
        Chirp chirp;
        Chirp res;
        chirp = this.chirpService.findOneToEdit(chirpId);
        Assert.notNull(chirp);
        this.chirpService.findOneToPublish(chirp);
        result = new ModelAndView("redirect:list.do");
        return result;
    }

    // Display ----------------------------------------------------------------

    @RequestMapping(value = "/display", method = RequestMethod.GET)
    public ModelAndView display(@RequestParam final int chirpId) {
        ModelAndView result;
        Chirp chirp;

        chirp = this.chirpService.findOne(chirpId);

        SimpleDateFormat formatterEs;
        SimpleDateFormat formatterEn;
        String momentEs;
        String momentEn;

        formatterEs = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        momentEs = formatterEs.format(new Date());
        formatterEn = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        momentEn = formatterEn.format(new Date());

        result = new ModelAndView("chirp/display");
        result.addObject("chirp", chirp);
        result.addObject("momentEs", momentEs);
        result.addObject("momentEn", momentEn);


        return result;
    }

    //  Edition ----------------------------------------------------------------

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(@RequestParam int chirpId) {
        final ModelAndView result;
        Chirp chirp;
        chirp = this.chirpService.findOneToEdit(chirpId);
        Assert.isTrue(!chirp.getPosted());
        Assert.notNull(chirp);
        result = this.createEditModelAndView(chirp);
        return result;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
    public ModelAndView save(@Valid Chirp chirp, BindingResult binding) {
        ModelAndView result;
        if (binding.hasErrors())
            result = this.createEditModelAndView(chirp);
        else
            try {
                this.chirpService.save(chirp);
                result = new ModelAndView("redirect:list.do");
            } catch (final Throwable oops) {
                result = this.createEditModelAndView(chirp, "chirp.commit.error");
            }
        return result;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
    public ModelAndView edit(Chirp chirp) {
        ModelAndView result;

        try {
            chirpService.delete(chirp);
            result = new ModelAndView("redirect:list.do");
        } catch (Throwable oops) {
            result = createEditModelAndView(chirp, "chirp.commit.error");
        }

        return result;
    }

    // Ancillary methods ------------------------------------------------------

    protected ModelAndView createEditModelAndView(final Chirp chirp) {
        ModelAndView result;

        result = this.createEditModelAndView(chirp, null);
        return result;
    }

    protected ModelAndView createEditModelAndView(final Chirp chirp, final String messageCode) {
        ModelAndView result;
        User user = userService.findByPrincipal();

        result = new ModelAndView("chirp/edit");
        result.addObject("chirp", chirp);
        result.addObject("actionUri", "chirp/user/edit.do");
        result.addObject("message", messageCode);

        return result;
    }
}
