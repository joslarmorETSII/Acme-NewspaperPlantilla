package controllers;

import domain.Agent;

import forms.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.AgentService;


import javax.validation.Valid;

@Controller
@RequestMapping("/agent")
public class AgentController extends AbstractController {

    //Services -------------------------------------------------------

    @Autowired
    private AgentService agentService;

    // edition ---------------------------------------------

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(@RequestParam int agentId){
        ModelAndView result;
        Agent agent;
        Assert.notNull(agentId);
        agent = agentService.findOne(agentId);

        result = createEditModelAndView(agent);

        return result;
    }


    @RequestMapping(value = "/edit", method = RequestMethod.POST,params = "save")
    public ModelAndView edit(@Valid Agent agent, BindingResult bindingResult){
        ModelAndView result;
        if(bindingResult.hasErrors())
            result = createEditModelAndView(agent);
        else{
            try{
                agentService.save(agent);
                result = new ModelAndView("redirect: list.do");

            }catch (Throwable oops){
                result = createEditModelAndView(agentService.create(),"agent.commit.error");
            }
        }
        return result;
    }

    //Edition --------------------------------------------------------------------

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView edit() {

        ModelAndView result;
        result = new ModelAndView("agent/editForm");

        result.addObject("userForm", new UserForm());

        return result;
    }

    // Save ------------------------------------------------------------------------

    @RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
    public ModelAndView save(@Valid final UserForm userForm, final BindingResult binding) {
        ModelAndView result;
        Agent agent;

        try {
            agent = this.agentService.reconstruct(userForm, binding);

            if (binding.hasErrors())
                result = createEditModelAndView2(userForm);
            else {
                result = new ModelAndView("redirect:/welcome/index.do");
                this.agentService.save(agent);

            }
        } catch (final Throwable oops) {
            if(oops.getCause().getCause().getMessage().contains("Duplicate entry"))
                result = createEditModelAndView2(userForm,"user.duplicated.username");
            else
                result = this.createEditModelAndView2(userForm, "agent.commit.error");
        }

        return result;
    }

    // Ancillary methods


    private ModelAndView createEditModelAndView(Agent agent) {

        return this.createEditModelAndView(agent, null);
    }

    private ModelAndView createEditModelAndView(Agent agent, String message) {

        final ModelAndView result = new ModelAndView("agent/edit");

        result.addObject("agent", agent);
        result.addObject("message", message);

        return result;
    }
    private ModelAndView createEditModelAndView2(UserForm userForm) {
        return createEditModelAndView2(userForm,null);
    }

        private ModelAndView createEditModelAndView2(UserForm userForm, String messageCode) {

        ModelAndView res;
        res = new ModelAndView("agent/editForm");

        res.addObject("userForm", userForm);
        res.addObject("message", messageCode);

        return res;
    }
}
