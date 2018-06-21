
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FolderRepository;
import domain.Actor;
import domain.Folder;
import domain.Message;

@Service
@Transactional
public class FolderService {

    @Autowired
    private FolderRepository	folderRepository;

    @Autowired
    private ActorService		actorService;


    public FolderService() {
        super();
    }

    // CRUD methods --------------------------------------------------------------------------------
    public Folder create(Actor a) {
        Assert.notNull(a);

        Folder result;

        result = new Folder();
        result.setMessages(new ArrayList<Message>());
        result.setActor(a);
        result.setSystem(false);

        return result;
    }

    public Folder createInFolder(Actor a) {
        Assert.notNull(a);
        Folder result;

        result = this.create(a);
        result.setName("inbox");
        result.setSystem(true);

        return result;
    }

    public Folder createOutFolder(Actor a) {
        Assert.notNull(a);
        Folder result;

        result = this.create(a);
        result.setName("outbox");
        result.setSystem(true);

        return result;
    }

    public Folder createTrashFolder(Actor a) {
        Assert.notNull(a);
        Folder result;

        result = this.create(a);
        result.setName("trashbox");
        result.setSystem(true);

        return result;
    }

    public Folder createSpamFolder(Actor a) {
        Assert.notNull(a);
        Folder result;

        result = this.create(a);
        result.setName("spambox");
        result.setSystem(true);

        return result;
    }

    public Folder createNotificationFolder(Actor a) {
        Assert.notNull(a);
        Folder result;

        result = this.create(a);
        result.setName("notificationbox");
        result.setSystem(true);

        return result;
    }

    public Folder findOne(int folderId) {
        return this.folderRepository.findOne(folderId);
    }

    public Collection<Folder> findAll() {
        return this.folderRepository.findAll();
    }

    public Folder save(Folder folder) {
        Assert.notNull(folder);
        Assert.isTrue(folder.getActor().getId() == this.actorService.findByPrincipal().getId());
        Folder res;
        res = this.folderRepository.save(folder);
        return res;
    }

    public Folder saveCreate(Folder folder) {
        System.out.println(folder.getId());
        Assert.notNull(folder);
        Folder res;
        res = this.folderRepository.save(folder);
        return res;
    }

    public void delete(Folder folder) {
        Assert.notNull(folder);
        Assert.isTrue(this.folderRepository.exists(folder.getId()));
        Assert.isTrue(!this.folderRepository.findOne(folder.getId()).getSystem());

        this.folderRepository.delete(folder);

        Assert.isTrue(!this.folderRepository.exists(folder.getId()));
    }

    // Other business methods --------------------------------------------------------------------------------

    public Folder findActorAndFolder(int idActor, String nameFolder) {
        return folderRepository.findActorAndFolder(idActor, nameFolder);
    }


    public void flush() {
        folderRepository.flush();
    }
}
