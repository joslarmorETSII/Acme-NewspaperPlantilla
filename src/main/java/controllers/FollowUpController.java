package controllers;

import domain.Article;
import domain.FollowUp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.ArticleService;
import services.FollowUpService;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

@Controller
@RequestMapping("/followUp")
public class FollowUpController extends AbstractController {

    // Services --------------------------------------------
    @Autowired
    private FollowUpService followUpService;

    @Autowired
    private ArticleService articleService;
    // Constructor -----------------------------------------
    public FollowUpController() {
        super();
    }

    // Display ----------------------------------------------------------------

    @RequestMapping(value = "/display", method = RequestMethod.GET)
    public ModelAndView display(@RequestParam int followUpId, HttpServletRequest request) {
        ModelAndView result;
        FollowUp followUp;
        SimpleDateFormat formatterEs;
        SimpleDateFormat formatterEn;
        String momentEs;
        String momentEn;

        followUp = followUpService.findOne(followUpId);
        formatterEs = new SimpleDateFormat("dd/MM/yyyy");
        momentEs = formatterEs.format(followUp.getMoment());
        formatterEn = new SimpleDateFormat("yyyy/MM/dd");
        momentEn = formatterEn.format(followUp.getMoment());

        result = new ModelAndView("followUp/display");
        result.addObject("followUp", followUp);
        result.addObject("cancelUriSession", request.getSession().getAttribute("cancelUriSession"));

        result.addObject("momentEs", momentEs);
        result.addObject("momentEn", momentEn);


        return result;
    }

    // Listing -------------------------------------------------------

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(@RequestParam int articleId) {
        ModelAndView result;
        Article article;
        Collection<FollowUp> followUps;

        SimpleDateFormat formatterEs;
        SimpleDateFormat formatterEn;
        String momentEs;
        String momentEn;

        formatterEs = new SimpleDateFormat("dd/MM/yyyy");
        momentEs = formatterEs.format(new Date());
        formatterEn = new SimpleDateFormat("yyyy/MM/dd");
        momentEn = formatterEn.format(new Date());

        article = articleService.findOne(articleId);
        followUps = article.getFollowUps();

        result = new ModelAndView("followUp/list");
        result.addObject("followUps", followUps);
        result.addObject("requestUri","followUp/list.do");

        result.addObject("momentEs", momentEs);
        result.addObject("momentEn", momentEn);

        return result;

    }
}
