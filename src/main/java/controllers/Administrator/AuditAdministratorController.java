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
import services.AuditService;
import services.NewsPaperService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;


@Controller
@RequestMapping("/audit/administrator")
public class AuditAdministratorController extends AbstractController {

    // Services --------------------------------------------

    @Autowired
    private AdministratorService administratorService;

    @Autowired
    private AuditService auditService;

    @Autowired
    private NewsPaperService newsPaperService;

    // Constructor --------------------------------------------

    public AuditAdministratorController() { super();}

    // Creation ------------------------------------------------------

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create() {
        ModelAndView result;
        Audit audit= null ;
        audit = this.auditService.create();
        result = this.createEditModelAndView(audit);

        return result;
    }

    // Listing -------------------------------------------------------

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list() {
        ModelAndView result;
        Collection<Audit> audits;


        result = new ModelAndView("audit/list");

        audits = auditService.findAll();

        result.addObject("audits", audits);
        result.addObject("requestURI","audit/administrator/list.do");


        return result;
    }

    //  Edition ----------------------------------------------------------------

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(@RequestParam int auditId) {
        final ModelAndView result;
        Audit audit;
        audit = this.auditService.findOneToEdit(auditId);
        Assert.isTrue(!audit.getFinalMode());
        Assert.notNull(audit);

        result = this.createEditModelAndView(audit);
        return result;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
    public ModelAndView save(@Valid final  Audit audit, final BindingResult binding) {
        ModelAndView result;
        if (binding.hasErrors())
            result = this.createEditModelAndView(audit);
        else
            try {
                this.auditService.save(audit);
                result = new ModelAndView("redirect:list.do");
            } catch (final Throwable oops) {
                result = this.createEditModelAndView(audit, "audit.commit.error");
            }
        return result;
    }
    // Add to newspaper ------------------------------------------------------

    @RequestMapping(value = "/asociateNewsPaper", method = RequestMethod.GET)
    public ModelAndView asociateNewsPaper(@RequestParam int auditId) {
        ModelAndView result;
        Audit audit;

        audit = auditService.findOne(auditId);
        Assert.isTrue(audit.getFinalMode(),"must be on final mode");
        Assert.isNull(audit.getNewsPaper(),"Assigned to newspaper");
        result = new ModelAndView("audit/asociateNewsPaper");
        result.addObject("audit",audit);
        result.addObject("newsPapers", newsPaperService.findPublishedNewsPaper());

        return result;
    }

    @RequestMapping(value = "/asociateNewsPaper", method = RequestMethod.POST,params = "save")
    public ModelAndView asociateNewsPaper(@Valid Audit audit, BindingResult binding) {
        ModelAndView result;

        auditService.save(audit);
        result = new ModelAndView("redirect:list.do");

        return result;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ModelAndView delete(@RequestParam int auditId){
        ModelAndView result;
        Audit audit= auditService.findOne(auditId);
        try{

            auditService.delete(audit);
            result = new ModelAndView("redirect:list.do");
        }catch (Throwable oops){
            result = createEditModelAndView(audit,"audit.commit.error");
        }

        return result;
    }

    // Display -------------------------------------------------------------------------

    @RequestMapping(value = "/display", method = RequestMethod.GET)
    public ModelAndView display(@RequestParam final int auditId) {
        ModelAndView result;
        Audit audit;

        SimpleDateFormat formatterEs;
        SimpleDateFormat formatterEn;
        String momentEs;
        String momentEn;

        formatterEs = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        momentEs = formatterEs.format(new Date());
        formatterEn = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        momentEn = formatterEn.format(new Date());

        Administrator administrator = administratorService.findByPrincipal();


        audit = this.auditService.findOne(auditId);
        Assert.notNull(audit);



        result = new ModelAndView("audit/display");
        result.addObject("audit", audit);
        result.addObject("administrator", administrator);
        result.addObject("cancelUri","audit/administrator/list.do");
        result.addObject("momentEs", momentEs);
        result.addObject("momentEn", momentEn);

        return result;
    }

    // Asociate a newsPaper -------------------------------------------------------------------------

    @RequestMapping(value = "/asociateNewspaper", method = RequestMethod.POST)
    public ModelAndView asociateNewspaper(@Valid final  int auditId, final BindingResult binding) {
        ModelAndView result;
        Audit audit= auditService.findOne(auditId);
        Collection<NewsPaper> newsPapers=newsPaperService.findAll();
        newsPapers.remove(audit.getNewsPaper());


        if (binding.hasErrors())
            result = this.createEditModelAndView(audit);
        else
            try {
                this.auditService.save(audit);
                result = new ModelAndView("redirect:list.do");
            } catch (final Throwable oops) {
                result = this.createEditModelAndView(audit, "audit.commit.error");
            }
        return result;
    }


    // Ancillary methods ------------------------------------------------------

    protected ModelAndView createEditModelAndView(final Audit audit) {
        ModelAndView result;

        result = this.createEditModelAndView(audit, null);
        return result;
    }

    protected ModelAndView createEditModelAndView(final Audit audit, final String messageCode) {
        ModelAndView result;
        Administrator administrator = administratorService.findByPrincipal();

        Collection<Audit> audits = administrator.getAudits();

        result = new ModelAndView("audit/edit");
        result.addObject("audit", audit);
        result.addObject("audits", audits);
        result.addObject("actionUri","audit/administrator/edit.do");
        result.addObject("message", messageCode);
        result.addObject("cancelUri","audit/administrator/list.do");

        return result;
    }



}
