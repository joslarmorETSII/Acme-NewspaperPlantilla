package controllers;


import domain.Pembas;
import domain.NewsPaper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.ActorService;
import services.AdministratorService;
import services.PembasService;
import services.NewsPaperService;

import java.util.Collection;

@Controller
@RequestMapping("/pembas")
public class PembasController extends AbstractController {

    // Services --------------------------------------------

    @Autowired
    private ActorService actorService;

    @Autowired
    private PembasService pembasService;

    @Autowired
    private NewsPaperService newsPaperService;

    // Constructor --------------------------------------------

    public PembasController() { super();}




    // Listing -------------------------------------------------------

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(@RequestParam int newsPaperId) {
        ModelAndView result;
        Collection<Pembas> pembass;
        NewsPaper newsPaper= newsPaperService.findOne(newsPaperId);

        result = new ModelAndView("newsPaper/display");

        pembass = pembasService.PembasForDisplay(newsPaperId);

        result.addObject("pembass", pembass);
        result.addObject("requestURI","pembas/list.do");
        result.addObject("newsPaper",newsPaper);

        return result;
    }
}
