
package domain;

import java.util.Collection;

import javax.persistence.*;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
        @Index(columnList = "name")
})
public class Folder extends DomainEntity {

    // Attributes
    // ====================================================================================

    private String	name;
    private boolean	system;


    // Constructors
    // ====================================================================================

    public Folder() {
        super();
    }

    // Getters & setters
    // ====================================================================================

    @SafeHtml(whitelistType = WhiteListType.NONE)
    @NotBlank
    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public boolean getSystem() {
        return this.system;
    }

    public void setSystem(final boolean system) {
        this.system = system;
    }


    // Relationships -------------------------------------------------------------------


    private Actor				actor;
    private Collection<Message>	messages;


    @ManyToOne(optional = false)
    public Actor getActor() {
        return this.actor;
    }

    public void setActor(final Actor actor) {
        this.actor = actor;
    }

    @Valid
    @OneToMany(mappedBy = "folder")
    public Collection<Message> getMessages() {
        return this.messages;
    }

    public void setMessages(final Collection<Message> messages) {
        this.messages = messages;
    }
}
