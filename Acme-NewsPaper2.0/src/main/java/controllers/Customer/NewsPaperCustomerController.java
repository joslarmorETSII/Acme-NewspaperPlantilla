
package controllers.Customer;

import controllers.AbstractController;
import domain.*;
import forms.SubscribeForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;


@Controller
@RequestMapping("/newsPaper/customer")
public class NewsPaperCustomerController extends AbstractController{

    // Services ------------------------------------------------------------

    @Autowired
    private CustomerService customerService;

    @Autowired
    private NewsPaperService newsPaperService;

    @Autowired
    private VolumeService   volumeService;

    @Autowired
    private ActorService actorService;

    @Autowired
    private SubscribeVolumeService subscribeVolumeService;

    @Autowired
    private SubscribeNewsPaperService subscribeNewsPaperService;

    // Listing  --------------------------------------------------------------
    //Listado de newspaper donde estoy suscrito
    @RequestMapping(value = "/listNewsPaperCustomer", method = RequestMethod.GET)
    public ModelAndView list() {
        ModelAndView result;
        Collection<NewsPaper> newsPapers;

        Customer customer = customerService.findByPrincipal();
        newsPapers= customerService.newsPapersSubscribed(customer.getId());

        result = new ModelAndView("newsPaper/listNewsPaperCustomer");
        result.addObject("newsPapers", newsPapers);
        result.addObject("customer", customer);
        result.addObject("requestURI", "newsPaper/customer/listNewsPaperCustomer.do");
        return result;
    }
    //Listado de newspaper donte me puedo suscritbir
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView listNotSubscribed() {

        ModelAndView result;
        Customer customer = customerService.findByPrincipal();

        Collection<NewsPaper> newsPapersToSubscribe = newsPaperService.findPublishedAndPrivateNewsPaper();
        newsPapersToSubscribe.removeAll(customerService.newsPapersSubscribed(customer.getId()));

        result = new ModelAndView("newsPaper/list");
        result.addObject("newsPapers", newsPapersToSubscribe);
        result.addObject("customer", customer);
        result.addObject("requestUri", "newsPaper/customer/list.do");
        return result;
    }
    //Listado de newspaper por volume que son privadas y no privadas
    @RequestMapping(value = "/listNewsPapersPerVolume", method = RequestMethod.GET)
    public ModelAndView listNewsPapersVNP(@RequestParam int volumeId, HttpServletRequest request) {
        ModelAndView result;
        boolean customerIsSuscribed = false;
        Collection<NewsPaper> newsPapers;
        Customer customer = this.customerService.findByPrincipal();
        SimpleDateFormat formatterEs;
        SimpleDateFormat formatterEn;
        String momentEs;
        String momentEn;

        formatterEs = new SimpleDateFormat("dd/MM/yyyy");
        momentEs = formatterEs.format(new Date());
        formatterEn = new SimpleDateFormat("yyyy/MM/dd");
        momentEn = formatterEn.format(new Date());

        newsPapers = this.volumeService.findPublishedNewsPaperPerVolume(volumeId);
        // El customer esta suscrito al volume
        if(subscribeVolumeService.findSubscriptionToAVolume(volumeId,customer.getId())!=null){
            customerIsSuscribed = true;
        }

        HttpSession session = request.getSession();

        result = new ModelAndView("newsPaper/listNewsPaperPerVolume");
        result.addObject("newsPapers", newsPapers);
        result.addObject("requestUri","newsPaper/customer/listNewsPapersPerVolume.do");
        result.addObject("customerIsSuscribed", customerIsSuscribed);
        result.addObject("customer",customerService.findByPrincipal());
        result.addObject("momentEs", momentEs);
        result.addObject("momentEn", momentEn);
        result.addObject("cancelUriSession",session.getAttribute("cancelUriSession"));

        session.setAttribute("cancelUriSession", request.getRequestURI()+ "?volumeId=" + volumeId);

        return result;

    }


    //Subscribirse

    @RequestMapping(value = "/subscribe", method = RequestMethod.GET)
    public ModelAndView subscribe(@RequestParam  int newsPaperId) {

        final ModelAndView result;
        NewsPaper newsPaper;

        newsPaper = this.newsPaperService.findOne(newsPaperId);
        Assert.notNull(newsPaper);

        SubscribeForm subscribeForm = new SubscribeForm();
        subscribeForm.setNewsPaper(newsPaper);

        result = new ModelAndView("customer/subscribeForm");
        result.addObject("subscribeForm", subscribeForm);

        return result;
    }

    // Save ------------------------------------------------------------------------

    @RequestMapping(value = "/subscribe", method = RequestMethod.POST, params = "save")
    public ModelAndView save(@Valid SubscribeForm subscribeForm, BindingResult binding) {
        ModelAndView result;
        NewsPaper newsPaper;
        SubscribeNewsPaper subscribeNewsPaper;

        try {
            CreditCard creditCard = customerService.reconstructSubscribe(subscribeForm, binding);

            if (binding.hasErrors())
                result = this.createEditModelAndView2(subscribeForm, null);
            else {
                result = new ModelAndView("redirect:listNewsPaperCustomer.do");
                newsPaper = subscribeForm.getNewsPaper();
                subscribeNewsPaper = subscribeNewsPaperService.create();
                subscribeNewsPaper.setCreditCard(creditCard);
                subscribeNewsPaper.setNewsPaper(newsPaper);
                subscribeNewsPaperService.save(subscribeNewsPaper);
            }
        } catch (final Throwable oops) {
            result = this.createEditModelAndView2(subscribeForm, "general.commit.error");
        }

        return result;
    }

    //Unsuscribe
    @RequestMapping(value = "/unsubscribe", method = RequestMethod.GET)
    public ModelAndView unsuscribe(@RequestParam int newsPaperId){
        ModelAndView result;
        NewsPaper newsPaper= newsPaperService.findOne(newsPaperId);
        try{

            newsPaperService.unsuscribe(newsPaper);
            result = new ModelAndView("redirect:list.do");
        }catch (Throwable oops){
            result = createEditModelAndView(newsPaper,"general.commit.error");
        }
        return result;
    }

    // Ancillary methods

    protected ModelAndView createEditModelAndView(final NewsPaper newsPaper) {
        ModelAndView result;

        result = this.createEditModelAndView(newsPaper, null);
        return result;
    }

    protected ModelAndView createEditModelAndView(final NewsPaper newsPaper, final String messageCode) {
        ModelAndView result;

        result = new ModelAndView("newsPaper/edit");
        result.addObject("newsPaper", newsPaper);
        result.addObject("message", messageCode);

        return result;
    }

    protected ModelAndView createEditModelAndView2(SubscribeForm subscribeForm, final String messageCode) {
        ModelAndView result;

        result = new ModelAndView("customer/subscribeForm");
        result.addObject("subscribeForm", subscribeForm);
        result.addObject("message", messageCode);

        return result;
    }

    // Display ----------------------------------------------------------------

    @RequestMapping(value = "/display", method = RequestMethod.GET)
    public ModelAndView display(@RequestParam int newsPaperId, HttpServletRequest request) {
        ModelAndView result;
        NewsPaper newsPaper;
        Customer customer;

        HttpSession session = request.getSession();


        newsPaper = this.newsPaperService.findOne(newsPaperId);
        Actor actor=actorService.findByPrincipal();
        // si el customer esta suscrito a la newspaper
        SubscribeNewsPaper subscribeNewsPaper = newsPaperService.findSubscriptionNewsPaperByCustomer(actor.getId(),newsPaperId);
        //
        customer = newsPaperService.isCustomerSubscribedToNewspaperViaVolume(actor.getId(),newsPaperId);
        Assert.isTrue(subscribeNewsPaper!=null || !newsPaper.isModePrivate()|| customer!=null);

        result = new ModelAndView("newsPaper/display");
        result.addObject("newsPaper", newsPaper);
        result.addObject("cancelURI", "newsPaper/listAll.do");
        result.addObject("cancelUriSession", request.getSession().getAttribute("cancelUriSession"));

        session.setAttribute("cancelUriSession", request.getRequestURI()+"?newsPaperId="+newsPaperId);

        return result;
    }
}
