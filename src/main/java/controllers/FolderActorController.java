
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.FolderService;
import domain.Actor;
import domain.Folder;

@Controller
@RequestMapping("/folder/actor")
public class FolderActorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private FolderService	folderService;
	@Autowired
	private ActorService	actorService;


	// Constructors -----------------------------------------------------------

	public FolderActorController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(defaultValue = "0", required = false) final String message) {
		ModelAndView result;
		Collection<Folder> folders;

		final Actor actor = this.actorService.findByPrincipal();

		folders = actor.getFolders();

		result = new ModelAndView("folder/list");
		result.addObject("requestURI", "folder/actor/list.do");
		result.addObject("folders", folders);

		if (!message.equals("0"))
			result.addObject("message", message);

		return result;
	}

	@RequestMapping(value = "/listError", method = RequestMethod.GET)
	public ModelAndView listError() {
		ModelAndView result;
		Collection<Folder> folders;

		final Actor actor = this.actorService.findByPrincipal();

		folders = actor.getFolders();

		result = new ModelAndView("folder/list");
		result.addObject("requestURI", "folder/actor/list.do");
		result.addObject("folders", folders);

		result.addObject("message", "folder.not.empty");

		return result;
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Folder folder;
		Actor actor;

		actor = this.actorService.findByPrincipal();
		folder = this.folderService.create(actor);
		folder.setSystem(false);

		result = this.createEditModelAndView(folder);
		result.addObject("folder", folder);

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int folderId) {
		ModelAndView result;
		Folder folder;

		try {
			folder = this.folderService.findOne(folderId);
			Assert.notNull(folder);
			Assert.isTrue(folder.getActor().getId() == this.actorService.findByPrincipal().getId());
			Assert.isTrue(!folder.getSystem());

			result = this.createEditModelAndView(folder);
		} catch (final Exception e) {

			result = this.panic(e);

		}

		return result;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(@Valid final Folder folder, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = this.createEditModelAndView(folder);
			System.out.println(binding);
		} else
			try {
				Assert.isTrue(this.actorService.findByPrincipal().getId() == folder.getActor().getId());
				this.folderService.save(folder);
				result = new ModelAndView("redirect:/folder/actor/list.do");
			} catch (final Throwable oops) {
				System.out.println(oops.toString());
				result = this.createEditModelAndView(folder, "folder.commit.error.2");
			}

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int folderId) {
		ModelAndView result;
		final Folder folder = this.folderService.findOne(folderId);
		Assert.isTrue(folder.getActor().getId() == this.actorService.findByPrincipal().getId());
		try {
			if (!folder.getMessages().isEmpty())
				result = new ModelAndView("redirect:/folder/actor/listError.do");
			else {
				this.folderService.delete(folder);
				result = new ModelAndView("redirect:/folder/actor/list.do");
			}
		} catch (final Throwable e) {
			result = this.createEditModelAndView(folder, "folder.commit.error.2");
		}

		return result;
	}
	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Folder folder) {
		ModelAndView result;

		result = this.createEditModelAndView(folder, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Folder folder, final String text) {
		ModelAndView result;

		result = new ModelAndView("folder/edit");
		result.addObject("folder", folder);
		result.addObject("message", text);
		result.addObject("requestURI", "folder/actor/save.do");

		return result;
	}

}
