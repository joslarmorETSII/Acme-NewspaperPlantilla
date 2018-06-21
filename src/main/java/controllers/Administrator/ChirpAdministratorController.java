
package controllers.Administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ChirpService;
import controllers.AbstractController;
import domain.Chirp;

@Controller
@RequestMapping("/chirp/administrator")
public class ChirpAdministratorController extends AbstractController {

	// Services --------------------------------------------

	@Autowired
	private ChirpService	chirpService;


	// Constructor --------------------------------------------

	public ChirpAdministratorController() {
		super();
	}

	// Listing -------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Chirp> tabooChirps;
		Collection<Chirp> allChirps;

		result = new ModelAndView("chirp/list");
		tabooChirps = this.chirpService.findTabooChirps();
		allChirps = this.chirpService.findPublishedChirps();

		result.addObject("allChirps", allChirps);
		result.addObject("chirps", tabooChirps);
		result.addObject("requestURI", "chirp/administrator/list.do");

		return result;
	}

	// Delete --------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int chirpId) {
		ModelAndView result;
		Chirp chirp;

		try {
			chirp = this.chirpService.findOne(chirpId);
			this.chirpService.delete(chirp);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("misc/panic");
		}

		return result;
	}

}
