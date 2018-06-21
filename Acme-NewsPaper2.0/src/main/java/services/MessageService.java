
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import repositories.MessageRepository;
import domain.Actor;
import domain.Configuration;
import domain.Folder;
import domain.Message;

@Service
@Transactional
public class MessageService {

    public MessageService() {
        super();
    }


    @Autowired
    private MessageRepository			messageRepository;
    @Autowired
    private FolderService				folderService;
    @Autowired
    private ActorService				actorService;
    @Autowired
    private ConfigurationService	configurationSystemService;

    // CRUD methods --------------------------------------------------------------------------------
    public Message create() {
        Message res = new Message();
        res.setPriority("NEUTRAL");
        long milliseconds = System.currentTimeMillis() - 100;
        Date moment = new Date(milliseconds);
        res.setActorSender(this.actorService.findByPrincipal());
        res.setMoment(moment);
        return res;
    }

    public Message findOne(final int messageId) {
        return messageRepository.findOne(messageId);
    }

    public Collection<Message> findAll() {
         return messageRepository.findAll();

    }

    public Message save(final Message message) {
        return this.messageRepository.save(message);
    }

    public void delete(final Message message) {
        //Coprobamos que el mensaje existe
        Assert.isTrue(this.messageRepository.exists(message.getId()));
        Assert.isTrue(message.getFolder().getActor().getId() == this.actorService.findByPrincipal().getId());
        if (message.getFolder().getName().equals("trashbox") && message.getFolder().getSystem())
            this.messageRepository.delete(message);
        else
            this.moveMessage(this.folderService.findActorAndFolder(this.actorService.findByPrincipal().getId(), "trashbox"), message);

    }

    // Other methods ------------------------------------------------------------------

    //método para mover un mensaje de una carpeta a otra
    public void moveMessage(final Folder destinyFolder, final Message msg) {
        // Comprobamos que la carpeta destino pertenece al actor
        final Actor sender = this.actorService.findByPrincipal();
        final Collection<Folder> folders = sender.getFolders();
        Assert.isTrue(folders.contains(destinyFolder));
        msg.setFolder(destinyFolder);
        this.messageRepository.save(msg);
    }

    public void sendMessage(Message message) {
        Assert.isTrue(!message.getActorReceivers().contains(message.getActorSender()));
        Folder folderOutbox;
        Message msg = message.clone();
        folderOutbox = folderService.findActorAndFolder(message.getActorSender().getId(),"outbox");
        msg.setFolder(folderOutbox);
        messageRepository.save(msg);

        Boolean isSpam = this.isSpam(message);
        for (Actor a : message.getActorReceivers()) {
            Message msgRe = message.clone();
            Folder folderInbox;
            for (Folder f : a.getFolders())
                if ((!isSpam && f.getName().equalsIgnoreCase("inbox") && f.getSystem()) || (isSpam && f.getName().equalsIgnoreCase("spambox") && f.getSystem())) {
                    folderInbox = f;
                    msgRe.setFolder(folderInbox);
                    this.messageRepository.save(msgRe);
                    break;
                }
        }
    }

    public void sendMessageNotification(Message message) {
        Folder folderOutbox;
        Message msg = message.clone();

        folderOutbox = folderService.findActorAndFolder(message.getActorSender().getId(),"outbox");
        msg.setFolder(folderOutbox);
        messageRepository.save(msg);
        for (Actor a : message.getActorReceivers()) {
            Message msgRe = message.clone();
            Folder notificationBox;
            notificationBox = folderService.findActorAndFolder(a.getId(),"notificationbox");
            msgRe.setFolder(notificationBox);
            messageRepository.save(msgRe);
        }
    }

    private Boolean isSpam(Message message) {
        Configuration cs = this.configurationSystemService.getCS();
        Collection<String> spams = cs.getTabooWords();
        Boolean isSpam = false;
        String body = message.getBody();
        String subject = message.getSubject();
        body = body.toUpperCase();
        subject = subject.toUpperCase();
        for (final String s : spams)
            if (body.contains(s.toUpperCase()) || subject.contains(s.toUpperCase())) {
                isSpam = true;
                break;
            }

        return isSpam;
    }

    public void reconstruct(Message message, BindingResult binding) {
        FieldError error;
        String[] codigos;

        if (message.getActorReceivers() == null) {
            codigos = new String[1];
            codigos[0] = "message.receives.error";
            error = new FieldError("sendMessage", "actorReceivers", message.getActorReceivers(), false, codigos, null, "");
            binding.addError(error);
        } else {

        }
    }

    public void sendBroadcast(final Message message) {
        Assert.isTrue(actorService.isAdministrator());
        Assert.isTrue(!message.getActorReceivers().contains(message.getActorSender()));
        Folder folderOutbox;
        final Message msg = message.clone();
        for (final Folder f : message.getActorSender().getFolders())
            if (f.getName().equalsIgnoreCase("outbox")) {
                folderOutbox = f;
                msg.setFolder(folderOutbox);
                this.messageRepository.save(msg);
                break;
            }
    }

    public void flush() {
        messageRepository.flush();
    }
}
