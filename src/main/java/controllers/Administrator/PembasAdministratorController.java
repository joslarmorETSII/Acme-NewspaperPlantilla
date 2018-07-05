package controllers.Administrator;

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
import services.AdministratorService;
import services.PembasService;
import services.NewsPaperService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;


@Controller
@RequestMapping("/pembas/administrator")
public class PembasAdministratorController extends AbstractController {

    // Services --------------------------------------------

    @Autowired
    private AdministratorService administratorService;

    @Autowired
    private PembasService pembasService;

    @Autowired
    private NewsPaperService newsPaperService;

    // Constructor --------------------------------------------

    public PembasAdministratorController() { super();}

    // Creation ------------------------------------------------------

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create() {
        ModelAndView result;
        Pembas pembas= null ;
        pembas = this.pembasService.create();
        result = this.createEditModelAndView(pembas);

        return result;
    }

    // Listing -------------------------------------------------------

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list() {
        ModelAndView result;
        Collection<Pembas> pembass;
        Administrator administrator = administratorService.findByPrincipal();


        result = new ModelAndView("pembas/list");

        pembass = administrator.getPembass();

        result.addObject("pembass", pembass);
        result.addObject("requestURI","pembas/administrator/list.do");



        return result;
    }

    //  Edition ----------------------------------------------------------------

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(@RequestParam int pembasId) {
        final ModelAndView result;
        Pembas pembas;
        pembas = this.pembasService.findOneToEdit(pembasId);
        Assert.isTrue(!pembas.getFinalMode());
        Assert.notNull(pembas);

        result = this.createEditModelAndView(pembas);
        return result;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
    public ModelAndView save(@Valid final  Pembas pembas, final BindingResult binding) {
        ModelAndView result;
        pembasService.checkMoment(pembas.getMoment(),binding);

        if (binding.hasErrors())
            result = this.createEditModelAndView(pembas);
        else
            try {
                this.pembasService.save(pembas);
                result = new ModelAndView("redirect:list.do");
            } catch (final Throwable oops) {
                result = this.createEditModelAndView(pembas, "pembas.commit.error");
            }
        return result;
    }
    // Add to newspaper ------------------------------------------------------

    @RequestMapping(value = "/asociateNewsPaper", method = RequestMethod.GET)
    public ModelAndView asociateNewsPaper(@RequestParam int pembasId) {
        ModelAndView result;
        Pembas pembas;

        pembas = pembasService.findOne(pembasId);
        Assert.isTrue(pembas.getFinalMode(),"must be on final mode");
        Assert.isNull(pembas.getNewsPaper(),"Assigned to newspaper");
        result = new ModelAndView("pembas/asociateNewsPaper");
        result.addObject("pembas",pembas);
        result.addObject("newsPapers", newsPaperService.findPublishedNewsPaper());

        return result;
    }

    @RequestMapping(value = "/asociateNewsPaper", method = RequestMethod.POST,params = "save")
    public ModelAndView asociateNewsPaper(@Valid Pembas pembas, BindingResult binding) {
        ModelAndView result;

        pembasService.save(pembas);
        result = new ModelAndView("redirect:list.do");

        return result;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ModelAndView delete(@RequestParam int pembasId){
        ModelAndView result;
        Pembas pembas= pembasService.findOne(pembasId);


            pembasService.delete(pembas);
            result = new ModelAndView("redirect:list.do");


        return result;
    }

    // Display -------------------------------------------------------------------------

    @RequestMapping(value = "/display", method = RequestMethod.GET)
    public ModelAndView display(@RequestParam final int pembasId) {
        ModelAndView result;
        Pembas pembas;

        SimpleDateFormat formatterEs;
        SimpleDateFormat formatterEn;
        String momentEs;
        String momentEn;

        formatterEs = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        momentEs = formatterEs.format(new Date());
        formatterEn = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        momentEn = formatterEn.format(new Date());

        Administrator administrator = administratorService.findByPrincipal();


        pembas = this.pembasService.findOne(pembasId);
        Assert.notNull(pembas);



        result = new ModelAndView("pembas/display");
        result.addObject("pembas", pembas);
        result.addObject("administrator", administrator);
        result.addObject("cancelUri","welcome/index.do");
        result.addObject("momentEs", momentEs);
        result.addObject("momentEn", momentEn);

        return result;
    }



    // Ancillary methods ------------------------------------------------------

    protected ModelAndView createEditModelAndView(final Pembas pembas) {
        ModelAndView result;

        result = this.createEditModelAndView(pembas, null);
        return result;
    }

    protected ModelAndView createEditModelAndView(final Pembas pembas, final String messageCode) {
        ModelAndView result;
        Administrator administrator = administratorService.findByPrincipal();

        Collection<Pembas> pembass = administrator.getPembass();

        result = new ModelAndView("pembas/edit");
        result.addObject("pembas", pembas);
        result.addObject("pembass", pembass);
        result.addObject("actionUri","pembas/administrator/edit.do");
        result.addObject("message", messageCode);
        result.addObject("cancelUri","pembas/administrator/list.do");

        return result;
    }



}
