
package controllers.Administrator;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationService;
import controllers.AbstractController;
import domain.Configuration;

@Controller
@RequestMapping("/configuration/administrator")
public class ConfigurationAdministratorController extends AbstractController {

    // services -------------------------------------------------
    @Autowired
    private ConfigurationService	configurationService;


    // constructor ----------------------------------------------

    public ConfigurationAdministratorController() {
        super();
    }

    // listing ---------------------------------------------------

    // creating --------------------------------------------------

    // edition --------------------------------------------------

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit() {
        final ModelAndView result;
        Configuration configuration;

        configuration = this.configurationService.findAll().iterator().next();
        Assert.notNull(configuration);
        result = this.createEditModelAndView(configuration);

        return result;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
    public ModelAndView edit(@Valid final Configuration configuration, final BindingResult bindingResult) {
        ModelAndView result;

        if (bindingResult.hasErrors())
            result = this.createEditModelAndView(configuration);
        else
            try {
                this.configurationService.save(configuration);
                result = new ModelAndView("redirect:/welcome/index.do");
            } catch (final Throwable oops) {
                result = this.createEditModelAndView(configuration, "general.commit.error");
            }
        return result;

    }
    // Ancillary Methods ------------------------------------------------------------------------
    protected ModelAndView createEditModelAndView(final Configuration configuration) {
        ModelAndView result;

        result = this.createEditModelAndView(configuration, null);

        return result;
    }

    protected ModelAndView createEditModelAndView(final Configuration configuration, final String messageCode) {
        ModelAndView result;

        result = new ModelAndView("configuration/edit");
        result.addObject("configuration", configuration);
        result.addObject("message", messageCode);

        return result;
    }

}
