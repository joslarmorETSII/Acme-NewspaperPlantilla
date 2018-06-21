package controllers.Administrator;

import controllers.AbstractController;
import domain.Advertisement;
import domain.Agent;
import domain.NewsPaper;
import org.hibernate.annotations.SortNatural;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.AdvertisementService;
import services.AgentService;
import services.NewsPaperService;

import java.util.Collection;

@Controller
@RequestMapping("/advertisement/administrator")
public class AdvertisementAdministratorController extends AbstractController{

    // Services --------------------------------------------

    @Autowired
    private AdvertisementService advertisementService;

    @Autowired
    private AgentService agentService;

    @Autowired
    private NewsPaperService newsPaperService;

    // Constructor --------------------------------------------


    public AdvertisementAdministratorController() { super();}

    // Listing -------------------------------------------------------

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list() {
        ModelAndView result;
        Collection<Advertisement> tabooAdvertisement;
        Collection<Advertisement> allAdvertisement;

        result = new ModelAndView("advertisement/list");
        tabooAdvertisement = advertisementService.findTabooAdvertisement();
        allAdvertisement = advertisementService.findAll();

        result.addObject("advertisements", allAdvertisement);
        result.addObject("tabooAdvertisements",tabooAdvertisement);
        result.addObject("requestURI","advertisement/administrator/list.do");


        return result;
    }



    // Delete ------------------------------------------------------------

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ModelAndView delete(@RequestParam int advertisementId) {
        ModelAndView result;
        Advertisement advertisement;
        advertisement =  advertisementService.findOne(advertisementId);
        try {

            advertisementService.delete(advertisement);
            result = new ModelAndView("redirect:list.do");
        } catch (Throwable oops) {
            result = createEditModelAndView(advertisement, "advertisement.commit.error");
        }

        return result;
    }

    // Ancillary methods ------------------------------------------------------

    protected ModelAndView createEditModelAndView(final Advertisement advertisement) {
        ModelAndView result;

        result = this.createEditModelAndView(advertisement, null);
        return result;
    }

    protected ModelAndView createEditModelAndView(final Advertisement advertisement, final String messageCode) {
        ModelAndView result;
        Agent agent = agentService.findByPrincipal();

        Collection<NewsPaper> newsPapers = newsPaperService.findPublishedNewsPaper();

        result = new ModelAndView("advertisement/edit");
        result.addObject("advertisement", advertisement);
        result.addObject("newsPapers", newsPapers);
        result.addObject("actionUri", "advertisement/edit.do");
        result.addObject("message", messageCode);

        return result;
    }
}
