package controllers.Administrator;

import controllers.AbstractController;
import domain.Actor;
import domain.Message;
import domain.Priority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import services.ActorService;
import services.AdministratorService;
import services.FolderService;
import services.MessageService;

import javax.validation.Valid;
import java.util.Collection;

@Controller
@RequestMapping("/message/admin")
public class MessageAdminController extends AbstractController{

    // Services ---------------------------------------------------------------

    @Autowired
    private MessageService messageService;

    @Autowired
    private FolderService folderService;

    @Autowired
    private ActorService actorService;

    @Autowired
    private AdministratorService administratorService;


    // Constructors -----------------------------------------------------------

    public MessageAdminController() { super(); }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create() {
        ModelAndView result;
        Message message;
        Actor principal;

        principal = actorService.findByPrincipal();
        message = messageService.create();
        message.setFolder(folderService.findActorAndFolder(principal.getId(), "inbox"));
        result = this.createEditModelAndView(message);

        return result;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, params = "send")
    public ModelAndView save(@ModelAttribute(value = "sendMessage") @Valid Message message, BindingResult binding) {
        ModelAndView result;
        Actor principal;
        Collection<Actor> actors;


        principal = actorService.findByPrincipal();
        actors = actorService.findAll();
        actors.remove(principal);
        message.setActorReceivers(actors);
        messageService.reconstruct(message, binding);

        if (binding.hasErrors())
            result = this.createEditModelAndView(message);
        else
            try {
                try {
                    Assert.isTrue(message.getActorSender().getId() == actorService.findByPrincipal().getId());
                } catch (Exception e) {
                    result = new ModelAndView("redirect:/folder/actor/list.do");
                    result.addObject("message", "folder.commit.error");
                }
                messageService.sendMessageNotification(message);
                result = new ModelAndView("redirect:/folder/actor/list.do");
            } catch (Throwable oops) {
                result = this.createEditModelAndView(message, "message.commit.error");
            }

        return result;
    }



    protected ModelAndView createEditModelAndView(Message message) {
        ModelAndView result;

        result = this.createEditModelAndView(message, null);

        return result;
    }

    protected ModelAndView createEditModelAndView( Message msg, String text) {
        ModelAndView result;
        Collection<Actor> recipients;

        recipients = this.actorService.findAll();
        recipients.remove(this.actorService.findByPrincipal());

        result = new ModelAndView("message/create");
        result.addObject("formAction", "message/admin/create.do");
        result.addObject("sendMessage", msg);
        result.addObject("listPriority", Priority.values());
        result.addObject("message", text);
        result.addObject("isBroadcast",true);

        return result;
    }
}
