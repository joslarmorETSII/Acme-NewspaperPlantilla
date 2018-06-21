package controllers;

import domain.Actor;
import domain.NewsPaper;
import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.ActorService;
import services.NewsPaperService;
import services.VolumeService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

@Controller
@RequestMapping("/newsPaper")
public class NewsPaperController extends AbstractController {

    // Services --------------------------------------------

    @Autowired
    private NewsPaperService newsPaperService;

    @Autowired
    private VolumeService volumeService;

    @Autowired
    private ActorService actorService;

    // Constructor --------------------------------------------

    public NewsPaperController() {
        super();
    }



    // Listing -------------------------------------------------------

    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    public ModelAndView list(HttpServletRequest request) {
        ModelAndView result;

        Collection<NewsPaper> newsPapers=null;

        SimpleDateFormat formatterEs;
        SimpleDateFormat formatterEn;
        String momentEs;
        String momentEn;

        formatterEs = new SimpleDateFormat("dd/MM/yyyy");
        momentEs = formatterEs.format(new Date());
        formatterEn = new SimpleDateFormat("yyyy/MM/dd");
        momentEn = formatterEn.format(new Date());
        newsPapers=newsPaperService.findPublishedAndNotPrivateNewsPaper();

        HttpSession session = request.getSession();
        session.setAttribute("cancelUriSession", request.getRequestURI());

        result = new ModelAndView("newsPaper/list");
        result.addObject("newsPapers", newsPapers);
        result.addObject("requestUri","newsPaper/listAll.do");
        result.addObject("momentEs", momentEs);
        result.addObject("momentEn", momentEn);

        return result;

    }

    @RequestMapping(value = "/listNewsPapersV", method = RequestMethod.GET)
    public ModelAndView listNewsPapersV(@RequestParam int volumeId) {
        ModelAndView result;

        Collection<NewsPaper> newsPapers=null;

        SimpleDateFormat formatterEs;
        SimpleDateFormat formatterEn;
        String momentEs;
        String momentEn;

        formatterEs = new SimpleDateFormat("dd/MM/yyyy");
        momentEs = formatterEs.format(new Date());
        formatterEn = new SimpleDateFormat("yyyy/MM/dd");
        momentEn = formatterEn.format(new Date());

        newsPapers = this.volumeService.findPublishedNewsPaperPerVolume(volumeId);

        result = new ModelAndView("newsPaper/list");
        result.addObject("newsPapers", newsPapers);
        result.addObject("requestURI","newsPaper/listNewsPapersV.do");
        result.addObject("momentEs", momentEs);
        result.addObject("momentEn", momentEn);
        result.addObject("cancelUri", "volume/list.do");

        return result;

    }

    // Display ----------------------------------------------------------------

    @RequestMapping(value = "/display", method = RequestMethod.GET)
    public ModelAndView display(@RequestParam int newsPaperId, HttpServletRequest request) {
        ModelAndView result;
        NewsPaper newsPaper;
        newsPaper = this.newsPaperService.findOne(newsPaperId);

        Actor actor=actorService.findByPrincipal();
        User publisher = newsPaper.getPublisher();

        if(!actor.equals(publisher) ){
            Assert.isTrue(!newsPaper.isModePrivate());
        }

        HttpSession session = request.getSession();

        result = new ModelAndView("newsPaper/display");
        result.addObject("newsPaper", newsPaper);
        result.addObject("cancelUriSession", request.getSession().getAttribute("cancelUriSession"));

        return result;
    }

    @RequestMapping(value = "/displayAnonymous", method = RequestMethod.GET)
    public ModelAndView displayAnonymous(@RequestParam int newsPaperId) {
        ModelAndView result;
        NewsPaper newsPaper;

        newsPaper = this.newsPaperService.findOne(newsPaperId);

        Assert.isTrue(!newsPaper.isModePrivate());
        result = new ModelAndView("newsPaper/display");
        result.addObject("newsPaper", newsPaper);
        result.addObject("cancelURI", "newsPaper/listAll.do");


        return result;
    }
}