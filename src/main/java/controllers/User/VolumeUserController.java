
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
import javax.servlet.http.HttpSession;
import java.util.Collection;

@Controller
@RequestMapping("/volume/user")
public class VolumeUserController extends AbstractController{
    // Services --------------------------------------------

    @Autowired
    private VolumeService volumeService;

    @Autowired
    private UserService userService;

    @Autowired
    private NewsPaperService newsPaperService;

    @Autowired
    private CustomerService customerService;

    // Constructor --------------------------------------------

    public VolumeUserController() {
        super();
    }

    // Creation ------------------------------------------------------

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create() {
        ModelAndView result;
        Volume volume = null ;
        volume = this.volumeService.create();
        result = this.createEditModelAndView(volume);

        return result;
    }

    //  Edition ----------------------------------------------------------------

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(@RequestParam int volumeId) {
        final ModelAndView result;
        Volume volume;
        volume = this.volumeService.findOneToEdit(volumeId);
        Assert.notNull(volume);
        result = this.createEditModelAndView(volume);
        return result;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
    public ModelAndView save(Volume volumePruned, final BindingResult binding) {
        ModelAndView result;


            try {
                Volume volume = this.volumeService.reconstructS(volumePruned,binding);

                if (binding.hasErrors()) {
                    result = this.createEditModelAndView(volumePruned);
                }
                else {
                    this.volumeService.save(volume);
                    result = new ModelAndView("redirect:list.do");
                }
            } catch (final Throwable oops) {
                if (binding.hasErrors()) {
                    result = this.createEditModelAndView(volumePruned);
                }else{
                    result = this.createEditModelAndView(volumePruned, "volume.commit.error");
                }
            }
        return result;
    }


    @RequestMapping(value = "/addNewsPaper", method = RequestMethod.GET)
    public ModelAndView editN(@RequestParam int volumeId) {
        final ModelAndView result;
        Volume volume;
        volume = this.volumeService.findOneToEdit(volumeId);
        Assert.notNull(volume);
        result = this.createEditModelAndView2(volume);
        return result;
    }
    @RequestMapping(value = "/addNewsPaper", method = RequestMethod.POST, params = "save")
    public ModelAndView saveN(Volume volumePruned, final BindingResult binding) {
        ModelAndView result;
        Collection<NewsPaper> newsPapers;

            try {
                Volume volume = this.volumeService.reconstructAddNewsPaper(volumePruned,binding);

                if (binding.hasErrors()){
                    result = this.createEditModelAndView2(volumePruned);
                }
                else{
                    this.volumeService.save(volume);
                    result = new ModelAndView("redirect:list.do");
                }

            } catch (final Throwable oops) {
                if (binding.hasErrors()){
                    result = this.createEditModelAndView2(volumePruned);
                }else{
                    result = this.createEditModelAndView2(volumePruned, "volume.commit.error");
                }
            }
        return result;
    }

    @RequestMapping(value = "/removeNewsPaper", method = RequestMethod.GET)
    public ModelAndView editND(@RequestParam int volumeId) {
        final ModelAndView result;
        Volume volume;
        volume = this.volumeService.findOneToEdit(volumeId);
        Assert.notNull(volume);
        result = this.createEditModelAndView3(volume);
        return result;
    }
    @RequestMapping(value = "/removeNewsPaper", method = RequestMethod.POST, params = "delete")
    public ModelAndView saveND(Volume volumePruned, final BindingResult binding) {
        ModelAndView result;
        Collection<NewsPaper> newsPapersToRemove;
        try {
            Volume volume = this.volumeService.reconstructRemoveNewsPaper(volumePruned,binding);

            if (binding.hasErrors()){
                result = this.createEditModelAndView3(volumePruned);
            }
            else{
                newsPapersToRemove = volumePruned.getNewsPapersVolume();
                volume.getNewsPapersVolume().removeAll(newsPapersToRemove);

                this.volumeService.save(volume);
                result = new ModelAndView("redirect:list.do");
            }

        } catch (final Throwable oops) {
            if (binding.hasErrors()){
                result = this.createEditModelAndView3(volumePruned);
            }else{
                result = this.createEditModelAndView3(volumePruned, "volume.commit.error");
            }
        }
        return result;
    }

    // Listing -------------------------------------------------------

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(HttpServletRequest request) {
        ModelAndView result;
        User user;
        Collection<Volume> volumes = null;

        user = userService.findByPrincipal();
        volumes = user.getVolumes();
        HttpSession session = request.getSession();
        session.setAttribute("cancelUriSession",request.getRequestURI());

        result = new ModelAndView("volume/list");
        result.addObject("volumes", volumes);
        result.addObject("user", user);
        result.addObject("requestURI","volume/user/list.do");

        return result;

    }

    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    public ModelAndView listAll(HttpServletRequest request) {
        ModelAndView result;
        User user;

        HttpSession session = request.getSession();
        session.setAttribute("cancelUriSession",request.getRequestURI());

        user = userService.findByPrincipal();
        result = new ModelAndView("volume/list");
        result.addObject("volumes", volumeService.findAll());
        result.addObject("user", user);
        result.addObject("requestURI","volume/user/listAll.do");

        return result;

    }

    // Ancillary methods ------------------------------------------------------

    protected ModelAndView createEditModelAndView(final Volume volume) {
        ModelAndView result;

        result = this.createEditModelAndView(volume, null);
        return result;
    }

    protected ModelAndView createEditModelAndView(final Volume volume, final String messageCode) {
        ModelAndView result;

        result = new ModelAndView("volume/edit");
        result.addObject("volume", volume);
        result.addObject("actionUri","volume/user/edit.do");
        result.addObject("message", messageCode);

        return result;
    }

    protected ModelAndView createEditModelAndView2(final Volume volume) {
        ModelAndView result;

        result = this.createEditModelAndView2(volume, null);
        return result;
    }
    protected ModelAndView createEditModelAndView2(final Volume volume, final String messageCode) {
        ModelAndView result;
        User principal;

        principal = userService.findByPrincipal();
        Collection<NewsPaper> newsPapers= newsPaperService.findPublishedNewsPaper();
        newsPapers.retainAll(principal.getNewsPapers());
        Collection<NewsPaper> newsPapers1 = volume.getNewsPapersVolume();
        newsPapers.removeAll(newsPapers1);

        result = new ModelAndView("volume/addNewsPaper");
        result.addObject("volume", volume);
        result.addObject("newsPapers",newsPapers);
        result.addObject("actionUri","volume/user/addNewsPaper.do");
        result.addObject("message", messageCode);

        return result;
    }

    protected ModelAndView createEditModelAndView3(final Volume volume) {
        ModelAndView result;

        result = this.createEditModelAndView3(volume, null);
        return result;
    }
    protected ModelAndView createEditModelAndView3(final Volume volume, final String messageCode) {
        ModelAndView result;

        result = new ModelAndView("volume/removeNewsPaper");
        result.addObject("volume", volume);
        result.addObject("newsPapers",volume.getNewsPapersVolume());
        result.addObject("actionUri","volume/user/removeNewsPaper.do");
        result.addObject("message", messageCode);

        return result;
    }
}

