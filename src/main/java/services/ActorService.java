package services;

import domain.Actor;
import domain.Folder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import repositories.ActorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;

@Service
@Transactional
public class ActorService {

    // Managed repository -----------------------------------------------------

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private FolderService folderService;

    // Supporting services ----------------------------------------------------


    // Constructors -----------------------------------------------------------

    public ActorService() {
        super();
    }

    // Simple CRUD methods ----------------------------------------------------


    public Actor findOne(final int actorId) {

        Actor result;
        result = this.actorRepository.findOne(actorId);
        return result;
    }

    public Collection<Actor> findAll() {

        Collection<Actor> result;
        result = this.actorRepository.findAll();
        return result;
    }

    public Actor save(final Actor actor) {

        Assert.notNull(actor);
        Assert.notNull(actor.getUserAccount());
        Assert.isTrue(this.findByPrincipal().getId() == actor.getId());

        Actor result;

        result = this.actorRepository.save(actor);

        return result;
    }

    // Other business methods -------------------------------------------------

    public Actor findByPrincipal() {

        Actor result;
        final UserAccount userAccount = LoginService.getPrincipal();
        result = this.findByUserAccountId(userAccount.getId());
        return result;
    }

    public Actor findByUserAccountId(final int userAccountId) {

        Actor result;
        result = this.actorRepository.findByUserAccountId(userAccountId);
        return result;
    }

    public boolean checkRole(final String role) {
        boolean result;
        Collection<Authority> authorities;

        result = false;
        authorities = LoginService.getPrincipal().getAuthorities();
        for (final Authority a : authorities)
            result = result || a.getAuthority().equals(role);

        return result;
    }

    public boolean isAdministrator() {
        boolean result;

        result = this.checkRole(Authority.ADMINISTRATOR);

        return result;
    }

    public boolean isCustomer() {
        boolean result;

        result = this.checkRole(Authority.CUSTOMER);

        return result;
    }

    public boolean isUser() {
        boolean result;

        result = this.checkRole(Authority.USER);

        return result;
    }

    public boolean isAgent() {
        boolean result;

        result = this.checkRole(Authority.AGENT);

        return result;
    }

    public Collection<Folder> generateFolders(Actor actor){
        Collection<Folder> folders = new ArrayList<>();

        Folder inbox = folderService.createInFolder(actor);
        folderService.saveCreate(inbox);
        Folder outbox = folderService.createOutFolder(actor);
        folderService.saveCreate(outbox);
        Folder spambox = folderService.createSpamFolder(actor);
        folderService.saveCreate(spambox);
        Folder trashbox = folderService.createTrashFolder(actor);
        folderService.saveCreate(trashbox);
        Folder notification = folderService.createNotificationFolder(actor);
        folderService.saveCreate(notification);

        folders.add(inbox);
        folders.add(outbox);
        folders.add(spambox);
        folders.add(trashbox);
        folders.add(notification);

        return folders;
    }


}
