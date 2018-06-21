/*
 * WelcomeController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

import domain.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.ConfigurationService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/welcome")
public class WelcomeController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public WelcomeController() {
		super();
	}


	// services -------------------------------------------------
	@Autowired
	private ConfigurationService configurationService;


	// Index ------------------------------------------------------------------		

	@RequestMapping(value = "/index")
	public ModelAndView index(@RequestParam(required = false, defaultValue = "John Doe") final String name, HttpServletRequest request) {
		ModelAndView result;

		SimpleDateFormat formatterEs;
		SimpleDateFormat formatterEn;
		String momentEs;
		String momentEn;

		Configuration configuration= configurationService.findAll().iterator().next();

		formatterEs = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		momentEs = formatterEs.format(new Date());
		formatterEn = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		momentEn = formatterEn.format(new Date());

		HttpSession session = request.getSession();
		session.setAttribute("nameOfbusiness",configuration.getName());

		result = new ModelAndView("welcome/index");
		result.addObject("englishWelcome", configuration.getEnglishWelcome());
		result.addObject("spanishWelcome", configuration.getSpanishWelcome());
		result.addObject("configurationBanner", configuration.getBanner());
		result.addObject("name",configuration.getName());
		result.addObject("momentEs", momentEs);
		result.addObject("momentEn", momentEn);

		return result;
	}

	@RequestMapping(value = "/cookies")
	public ModelAndView cookies() {

		ModelAndView result;

		result = new ModelAndView("welcome/cookies");
		result.addObject("backURI", "/welcome/index.do");

		return result;
	}

}
