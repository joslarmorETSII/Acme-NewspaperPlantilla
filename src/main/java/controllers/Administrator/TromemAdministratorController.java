package controllers.Administrator;

import controllers.AbstractController;

import domain.Administrator;
import domain.Tromem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.*;


import javax.validation.Valid;
import java.util.Collection;

@Controller
@RequestMapping("/tromem/administrator")
public class TromemAdministratorController extends AbstractController {

    // Services --------------------------------------------

    @Autowired
    private TromemService tromemService;

    @Autowired
    private NewsPaperService newsPaperService;


    @Autowired
    private AdministratorService administratorService;

    // Constructor --------------------------------------------

    public TromemAdministratorController() {
        super();
    }

    // Creation ------------------------------------------------------

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create() {
        ModelAndView result;
        Tromem tromem;

        tromem = tromemService.create();
        result = createEditModelAndView(tromem);

        return result;
    }

    // Listing -------------------------------------------------------

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list() {
        ModelAndView result;
        Administrator administrator;
        Collection<Tromem> tromems;

        administrator = administratorService.findByPrincipal();
        tromems = administrator.getTromems();
        result = new ModelAndView("tromem/list");
        result.addObject("tromems", tromems);
        result.addObject("requestURI","tromem/administrator/list.do");

        return result;

    }

    //  Edition ----------------------------------------------------------------

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView save(@RequestParam int tromemId) {
        ModelAndView result;
        Tromem tromem;

        tromem = tromemService.findOneToEdit(tromemId);
        result = new ModelAndView("tromem/edit");
        result.addObject("tromem",tromem);
        result.addObject("newsPapers", newsPaperService.findPublishedNewsPaper());

        return result;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
    public ModelAndView save(@Valid Tromem tromem, BindingResult binding) {
        ModelAndView result;
        tromemService.checkTitle(tromem,binding);
        tromemService.checkDescription(tromem,binding);
        if (binding.hasErrors())
            result = this.createEditModelAndView(tromem);
        else
            try {
                tromemService.save(tromem);
                result = new ModelAndView("redirect:list.do");
            } catch (final Throwable oops) {
                result = this.createEditModelAndView(tromem, "article.commit.error");
            }
        return result;
    }

    // Add to newspaper ------------------------------------------------------

    @RequestMapping(value = "/addToNewsPaper", method = RequestMethod.GET)
    public ModelAndView addToNewsPaper(@RequestParam int tromemId) {
        ModelAndView result;
        Tromem tromem;

        tromem = tromemService.findOne(tromemId);
        Assert.isTrue(tromem.getFinalMode(),"must be on final mode");
        Assert.isNull(tromem.getNewsPaper(),"Assigned to newspaper");
        result = new ModelAndView("tromem/addToNewsPaper");
        result.addObject("tromem",tromem);
        result.addObject("newsPapers", newsPaperService.findPublishedNewsPaper());

        return result;
    }

    @RequestMapping(value = "/addToNewsPaper", method = RequestMethod.POST,params = "save")
    public ModelAndView addToNewsPaper(@Valid Tromem tromem, BindingResult binding) {
        ModelAndView result;

        tromemService.save(tromem);
        result = new ModelAndView("redirect:list.do");

        return result;
    }

    // delete
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ModelAndView delete(@RequestParam int tromemId) {
        ModelAndView result;
        Tromem tromem;

        tromem = tromemService.findOne(tromemId);
        tromemService.delete(tromem);
        result = new ModelAndView("redirect:list.do");

        return result;
    }
    // Ancillary methods ------------------------------------------------------

    protected ModelAndView createEditModelAndView(Tromem tromem) {
        ModelAndView result;

        result = this.createEditModelAndView(tromem, null);
        return result;
    }

    protected ModelAndView createEditModelAndView(Tromem tromem, String messageCode) {
        ModelAndView result;

        result = new ModelAndView("tromem/edit");
        result.addObject("tromem", tromem);
        result.addObject("newsPapers", newsPaperService.findPublishedNewsPaper());
        result.addObject("message", messageCode);

        return result;
    }
}
