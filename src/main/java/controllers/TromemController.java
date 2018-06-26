package controllers;

import domain.Tromem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import services.TromemService;

import java.util.Collection;

@Controller
@RequestMapping("/tromem")
public class TromemController extends AbstractController{

    @Autowired
    private TromemService tromemService;

    // Listing -------------------------------------------------------

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list() {
        ModelAndView result;
        Collection<Tromem> tromems;

        result = new ModelAndView("tromem/list");
        result.addObject("requestURI","tromem/administrator/list.do");

        return result;

    }
}
