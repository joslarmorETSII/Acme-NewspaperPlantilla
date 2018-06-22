package controllers.Administrator;

import controllers.AbstractController;

import domain.Administrator;
import domain.Note;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.*;


import javax.validation.Valid;
import java.util.Collection;

@Controller
@RequestMapping("/note/administrator")
public class NoteAdministratorController extends AbstractController {

    // Services --------------------------------------------

    @Autowired
    private NoteService noteService;

    @Autowired
    private NewsPaperService newsPaperService;


    @Autowired
    private AdministratorService administratorService;

    // Constructor --------------------------------------------

    public NoteAdministratorController() {
        super();
    }

    // Creation ------------------------------------------------------

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create() {
        ModelAndView result;
        Note note;

        note = noteService.create();
        result = createEditModelAndView(note);

        return result;
    }

    // Listing -------------------------------------------------------

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list() {
        ModelAndView result;
        Administrator administrator;
        Collection<Note> notes;

        administrator = administratorService.findByPrincipal();
        notes = administrator.getNotes();
        result = new ModelAndView("note/list");
        result.addObject("notes", notes);
        result.addObject("requestURI","note/administrator/list.do");

        return result;

    }

    //  Edition ----------------------------------------------------------------

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView save(@RequestParam int noteId) {
        ModelAndView result;
        Note note;

        note = noteService.findOneToEdit(noteId);
        result = new ModelAndView("note/edit");
        result.addObject("note",note);
        result.addObject("newsPapers", newsPaperService.findPublishedNewsPaper());

        return result;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
    public ModelAndView save(@Valid Note note, BindingResult binding) {
        ModelAndView result;
        if (binding.hasErrors())
            result = this.createEditModelAndView(note);
        else
            try {
                noteService.save(note);
                result = new ModelAndView("redirect:list.do");
            } catch (final Throwable oops) {
                result = this.createEditModelAndView(note, "article.commit.error");
            }
        return result;
    }

    // Add to newspaper ------------------------------------------------------

    @RequestMapping(value = "/addToNewsPaper", method = RequestMethod.GET)
    public ModelAndView addToNewsPaper(@RequestParam int noteId) {
        ModelAndView result;
        Note note;

        note = noteService.findOne(noteId);
        Assert.isTrue(note.getFinalMode(),"must be on final mode");
        Assert.isNull(note.getNewsPaper(),"Assigned to newspaper");
        result = new ModelAndView("note/addToNewsPaper");
        result.addObject("note",note);
        result.addObject("newsPapers", newsPaperService.findPublishedNewsPaper());

        return result;
    }

    @RequestMapping(value = "/addToNewsPaper", method = RequestMethod.POST,params = "save")
    public ModelAndView addToNewsPaper(@Valid Note note, BindingResult binding) {
        ModelAndView result;

        noteService.save(note);
        result = new ModelAndView("redirect:list.do");

        return result;
    }

    // delete
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ModelAndView delete(@RequestParam int noteId) {
        ModelAndView result;
        Note note;

        note = noteService.findOne(noteId);
        noteService.delete(note);
        result = new ModelAndView("redirect:list.do");

        return result;
    }
    // Ancillary methods ------------------------------------------------------

    protected ModelAndView createEditModelAndView(Note note) {
        ModelAndView result;

        result = this.createEditModelAndView(note, null);
        return result;
    }

    protected ModelAndView createEditModelAndView(Note note, String messageCode) {
        ModelAndView result;

        result = new ModelAndView("note/edit");
        result.addObject("note", note);
        result.addObject("newsPapers", newsPaperService.findPublishedNewsPaper());
        result.addObject("message", messageCode);

        return result;
    }
}
