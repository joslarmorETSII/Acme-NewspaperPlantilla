

package controllers.Agent;

import controllers.AbstractController;

import domain.*;
import forms.RegisterAdvertisementForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.AdvertisementService;
import services.AgentService;

import services.NewsPaperService;

import javax.validation.Valid;
import java.util.Collection;

@Controller
@RequestMapping("/advertisement/agent")
public class AdvertisementAgentController extends AbstractController {

    //Services -------------------------------------------------------

    @Autowired
    private AdvertisementService advertisementService;

    @Autowired
    private AgentService agentService;


    @Autowired
    private NewsPaperService newsPaperService;

    public AdvertisementAgentController() {
        super();
    }


    // Creation ------------------------------------------------------

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create() {
        ModelAndView result;

        result = new ModelAndView("advertisement/registerAdvertisementForm");
        RegisterAdvertisementForm registerAdvertisementForm;
        registerAdvertisementForm = new RegisterAdvertisementForm();
        result.addObject("registerAdvertisementForm", registerAdvertisementForm);
        result.addObject("newsPapers", newsPaperService.findPublishedNewsPaper());

        return result;
    }

    //  Edition ----------------------------------------------------------------

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(@RequestParam int advertisementId) {
        final ModelAndView result;
        Advertisement advertisement;
        advertisement = this.advertisementService.findOne(advertisementId);
        Assert.notNull(advertisement);
        result = this.createEditModelAndView(advertisement);
        return result;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
    public ModelAndView save(@Valid RegisterAdvertisementForm registerAdvertisementForm, BindingResult binding) {
        ModelAndView result;
        Agent agent;
        NewsPaper newsPaper;
        try {
            CreditCard creditCard = advertisementService.reconstruct(registerAdvertisementForm,binding);
            Advertisement advertisement = agentService.reconstructRegisterAdvertisement(registerAdvertisementForm, binding);


            if (binding.hasErrors())
                result = createEditModelAndView2(registerAdvertisementForm);
            else {
                advertisement.setCreditCard(creditCard);
                Advertisement savedAdd = advertisementService.save(advertisement);
                result = new ModelAndView("redirect: list.do");
                newsPaper = savedAdd.getNewsPaper();
                newsPaper.getAdvertisements().add(savedAdd);

                newsPaperService.save(newsPaper);

            }
        } catch (final Throwable oops) {
            result = this.createEditModelAndView2(registerAdvertisementForm, "general.commit.error");
        }
        return result;
    }


    @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
    public ModelAndView edit(Advertisement advertisement) {
        ModelAndView result;

        try {
            advertisementService.delete(advertisement);
            result = new ModelAndView("redirect:/welcome/index.do");
        } catch (Throwable oops) {
            result = createEditModelAndView(advertisement, "advertisement.commit.error");
        }

        return result;
    }

    // Listing ------------------------------------------------------

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list() {
        ModelAndView result;
        Agent principal;

        principal = agentService.findByPrincipal();
        result = new ModelAndView("advertisement/list");
        result.addObject("advertisements",principal.getAdvertisements());
        result.addObject("requestURI","advertisement/agent/list.do");
        return result;
    }


    // Ancillary methods ------------------------------------------------------

    protected ModelAndView createEditModelAndView(Advertisement advertisement) {
        ModelAndView result;

        result = this.createEditModelAndView(advertisement, null);
        return result;
    }

    protected ModelAndView createEditModelAndView( Advertisement advertisement, String messageCode) {
        ModelAndView result;

        Collection<NewsPaper> newsPapers = newsPaperService.findPublishedNewsPaper();

        result = new ModelAndView("advertisement/edit");
        result.addObject("advertisement", advertisement);
        result.addObject("newsPapers", newsPapers);
        result.addObject("actionUri", "advertisement/edit.do");
        result.addObject("message", messageCode);

        return result;
    }

    private ModelAndView createEditModelAndView2(RegisterAdvertisementForm registerAdvertisementForm) {
        return createEditModelAndView2(registerAdvertisementForm, null);
    }

    private ModelAndView createEditModelAndView2(RegisterAdvertisementForm registerAdvertisementForm,String messageCode) {
        ModelAndView result;

        result = new ModelAndView("advertisement/registerAdvertisementForm");
        result.addObject("registerAdvertisementForm", registerAdvertisementForm);
        result.addObject("newsPapers", newsPaperService.findPublishedNewsPaper());
        result.addObject("message", messageCode);

        return result;


    }
}
