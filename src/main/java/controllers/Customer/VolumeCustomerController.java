
package controllers.Customer;

import controllers.AbstractController;
import domain.*;
import forms.SubscribeVolumeForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.CustomerService;
import services.SubscribeVolumeService;
import services.VolumeService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Collection;

@Controller
@RequestMapping("/volume/customer")
public class VolumeCustomerController extends AbstractController{

    //Services --------------------------------------------------------

    @Autowired
    private CustomerService customerService;

    @Autowired
    private VolumeService volumeService;

    @Autowired
    private SubscribeVolumeService subscribeVolumeService;

    // Listing  --------------------------------------------------------------

    @RequestMapping(value = "/listVolumeCustomer", method = RequestMethod.GET)
    public ModelAndView list(HttpServletRequest request) {

        ModelAndView result;
        Customer customer;
        Collection<Volume> volumes;

        customer= customerService.findByPrincipal();
        volumes = volumeService.findVolumeByCustomer(customer.getId());

        HttpSession session = request.getSession();
        session.setAttribute("cancelUriSession",request.getRequestURI());

        result = new ModelAndView("volume/listSubscribedVolumes");
        result.addObject("volumes", volumes);
        result.addObject("customer",customerService.findByPrincipal());
        result.addObject("requestURI", "volume/customer/listVolumeCustomer.do");
        result.addObject("cancelUri", "welcome/index.do");

        return result;
    }

    @RequestMapping(value = "/listAllVolumes", method = RequestMethod.GET)
    public ModelAndView listAllVolumes(HttpServletRequest request) {

        ModelAndView result;
        Customer customer = customerService.findByPrincipal();

        Collection<Volume> volumes = volumeService.findAll();
        volumes.removeAll(volumeService.findVolumeByCustomer(customer.getId()));

        HttpSession session = request.getSession();
        session.setAttribute("cancelUriSession",request.getRequestURI());

        result = new ModelAndView("volume/listVolumesToSubscribe");
        result.addObject("volumes", volumes);
        result.addObject("customer",customer);
        result.addObject("requestURI", "volume/customer/listAllVolumes.do");
        result.addObject("cancelUri", "welcome/index.do");
        return result;
    }


    // Subscribe --------------------------------------------------------------

    @RequestMapping(value = "/subscribe", method = RequestMethod.GET)
    public ModelAndView subscribe(@RequestParam int volumeId) {

        ModelAndView result;
        Volume volume;

        volume = volumeService.findOne(volumeId);
        Assert.notNull(volume);
        SubscribeVolume subscribeVolume = subscribeVolumeService.findSubscriptionToAVolume(volumeId,customerService.findByPrincipal().getId());
        Assert.isTrue(subscribeVolume==null,"Already subscribed to this volume");

        SubscribeVolumeForm subscribeVolumeForm = new SubscribeVolumeForm();
        subscribeVolumeForm.setVolume(volume);

        result = new ModelAndView("customer/subscribeVolumeForm");
        result.addObject("subscribeVolumeForm", subscribeVolumeForm);

        return result;
    }

    @RequestMapping(value = "/unsubscribe", method = RequestMethod.GET)
    public ModelAndView unsubscribe(@RequestParam int volumeId) {
        ModelAndView result;
        Volume volume;
        Customer principal;

        volume = volumeService.findOne(volumeId);
        Assert.notNull(volume);
        principal = customerService.findByPrincipal();
        SubscribeVolume subscribeVolume = subscribeVolumeService.findSubscriptionToAVolume(volumeId,principal.getId());
        subscribeVolumeService.delete(subscribeVolume);

        result = new ModelAndView("redirect: listAllVolumes.do");

        return result;
    }

    @RequestMapping(value = "/subscribe", method = RequestMethod.POST, params = "save")
    public ModelAndView save(@Valid SubscribeVolumeForm subscribeVolumeForm, BindingResult binding) {
        ModelAndView result;
        Customer customer;
        Volume volume;
        SubscribeVolume subscribeVolume;

        try {
            CreditCard creditCard = customerService.reconstructSubscribeVolume(subscribeVolumeForm, binding);

            if (binding.hasErrors())
                result = createEditModelAndView(subscribeVolumeForm);
            else {
                result = new ModelAndView("redirect: listVolumeCustomer.do");

                subscribeVolume = subscribeVolumeService.create();
                customer = customerService.findByPrincipal();
                volume = subscribeVolumeForm.getVolume();
                subscribeVolume.setCustomer(customer);
                subscribeVolume.setVolume(volume);
                subscribeVolume.setCreditCard(creditCard);
                subscribeVolumeService.save(subscribeVolume);
            }
        } catch (final Throwable oops) {
            result = this.createEditModelAndView(subscribeVolumeForm, "general.commit.error");
        }

        return result;
    }

    protected ModelAndView createEditModelAndView(SubscribeVolumeForm subscribeVolumeForm) {
        return createEditModelAndView(subscribeVolumeForm,null);
    }

        protected ModelAndView createEditModelAndView(SubscribeVolumeForm subscribeVolumeForm, final String messageCode) {
        ModelAndView result;

        result = new ModelAndView("customer/subscribeVolumeForm");
        result.addObject("subscribeVolumeForm", subscribeVolumeForm);
        result.addObject("message", messageCode);

        return result;
    }



}

