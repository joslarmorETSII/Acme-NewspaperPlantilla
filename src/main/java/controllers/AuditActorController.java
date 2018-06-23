package controllers;


import domain.Audit;
import domain.NewsPaper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.ActorService;
import services.AdministratorService;
import services.AuditService;
import services.NewsPaperService;

import java.util.Collection;

@Controller
@RequestMapping("/audit/actor")
public class AuditActorController extends AbstractController {

    // Services --------------------------------------------

    @Autowired
    private ActorService actorService;

    @Autowired
    private AuditService auditService;

    @Autowired
    private NewsPaperService newsPaperService;

    // Constructor --------------------------------------------

    public AuditActorController() { super();}




    // Listing -------------------------------------------------------

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(@RequestParam int newsPaperId) {
        ModelAndView result;
        Collection<Audit> audits;
        NewsPaper newsPaper= newsPaperService.findOne(newsPaperId);

        result = new ModelAndView("newsPaper/display");

        audits = auditService.AuditForDisplay(newsPaperId);

        result.addObject("audits", audits);
        result.addObject("requestURI","audit/actor/list.do");
        result.addObject("newsPaper",newsPaper);

        return result;
    }
}
