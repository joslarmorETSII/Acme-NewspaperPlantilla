/*
 * CustomerController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */


package controllers;

import domain.Customer;
import forms.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.CustomerService;

import javax.validation.Valid;

@Controller
@RequestMapping("/customer")
public class CustomerController extends AbstractController {

	//Services -------------------------------------------------------

	@Autowired
	private CustomerService customerService;


	// edition ---------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int customerId){
		ModelAndView result;
		Customer customer;

		Assert.notNull(customerId);
		customer = customerService.findOne(customerId);
		result = createEditModelAndView(customer);

		return result;
	}


	@RequestMapping(value = "/edit", method = RequestMethod.POST,params = "save")
	public ModelAndView edit(@Valid Customer customer, BindingResult bindingResult){
		ModelAndView result;
		if(bindingResult.hasErrors())
			result = createEditModelAndView(customer);
		else{
			try{
				customerService.save(customer);
				result = new ModelAndView("redirect: list.do");

			}catch (Throwable oops){
				result = createEditModelAndView(customerService.create(),"customer.commit.error");
			}
		}
		return result;
	}

	//Edition --------------------------------------------------------------------

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView edit() {

		ModelAndView result;
		result = new ModelAndView("customer/editForm");

		result.addObject("userForm", new UserForm());

		return result;
	}

	// Save ------------------------------------------------------------------------

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final UserForm userForm, final BindingResult binding) {
		ModelAndView result;
		Customer customer;

		try {
			customer = this.customerService.reconstruct(userForm, binding);

			if (binding.hasErrors())
				result = this.createEditModelAndView2(userForm);
			else {
				result = new ModelAndView("redirect:/welcome/index.do");
				this.customerService.save(customer);

			}
		} catch (final Throwable oops) {
			if(oops.getCause().getCause().getMessage().contains("Duplicate entry"))
				result = createEditModelAndView2(userForm,"user.duplicated.username");
			else
				result = this.createEditModelAndView2(userForm, "customer.commit.error");
			}

		return result;
	}

	// Ancillary methods


	private ModelAndView createEditModelAndView(final Customer customer) {

		return this.createEditModelAndView(customer, null);
	}

	private ModelAndView createEditModelAndView(final Customer customer, final String message) {

		ModelAndView result = new ModelAndView("customer/edit");

		result.addObject("customer", customer);
		result.addObject("message", message);

		return result;
	}

	private ModelAndView createEditModelAndView2( UserForm userForm) {
		return createEditModelAndView2(userForm,null);
	}

	private ModelAndView createEditModelAndView2(UserForm userForm, String messageCode) {

		ModelAndView res;

		res = new ModelAndView("customer/editForm");
		res.addObject("userForm", userForm);
		res.addObject("message", messageCode);

		return res;
	}
}
