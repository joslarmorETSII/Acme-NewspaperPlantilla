
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Message extends DomainEntity {

    // Attributes
    // ====================================================================================

    private Date	moment;
    private String	subject;
    private String	body;
    private String	priority;


    // Constructors
    // ====================================================================================

    public Message() {
        super();
    }

    @NotNull
    @Past
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    public Date getMoment() {
        return this.moment;
    }

    public void setMoment(final Date moment) {
        this.moment = moment;
    }

    @SafeHtml(whitelistType = WhiteListType.NONE)
    @NotBlank
    public String getSubject() {
        return this.subject;
    }

    public void setSubject(final String subject) {
        this.subject = subject;
    }

    @SafeHtml(whitelistType = WhiteListType.NONE)
    @NotBlank
    public String getBody() {
        return this.body;
    }

    public void setBody(final String body) {
        this.body = body;
    }

    @SafeHtml(whitelistType = WhiteListType.NONE)
    @NotBlank
    public String getPriority() {
        return this.priority;
    }

    public void setPriority(final String priority) {
        this.priority = priority;
    }


    // Relationships -------------------------------------------------------------------


    private Folder				folder;
    private Actor				actorSender;
    private Collection<Actor>	actorReceivers;


    @Valid
    @ManyToOne(optional = false)
    public Folder getFolder() {
        return this.folder;
    }

    public void setFolder(final Folder folder) {
        this.folder = folder;
    }

    @Valid
    @ManyToOne(optional = false)
    public Actor getActorSender() {
        return this.actorSender;
    }

    public void setActorSender(final Actor actorSender) {
        this.actorSender = actorSender;
    }

    @Valid
    @ManyToMany()
    public Collection<Actor> getActorReceivers() {
        return this.actorReceivers;
    }

    public void setActorReceivers(final Collection<Actor> actorReceivers) {
        this.actorReceivers = actorReceivers;
    }

    @Override
    public Message clone() {

        final Message msg = new Message();
        msg.setBody(this.body);
        msg.setMoment(this.moment);
        msg.setPriority(this.priority);
        msg.setActorReceivers(this.actorReceivers);
        msg.setSubject(this.subject);
        msg.setActorSender(this.actorSender);

        return msg;
    }

}
