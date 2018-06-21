
package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/termAndCondition")
public class TermAndConditionController extends AbstractController {

	@RequestMapping("/termAndCondition")
	public ModelAndView lopd() {
		ModelAndView result;

		result = new ModelAndView("termAndCondition/termAndCondition");

		return result;
	}

}
