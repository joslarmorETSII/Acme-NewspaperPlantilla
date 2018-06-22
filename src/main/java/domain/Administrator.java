package domain;


import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Entity
@Access(AccessType.PROPERTY)
public class Administrator extends Actor {

    // Constructors

    public Administrator() {
        super();
    }
    // Relationships ----------------------------------------------------------
    private Collection<Note> notes;

    @NotNull
    @Valid
    @OneToMany(mappedBy = "administrator")
    public Collection<Note> getNotes() {
        return notes;
    }

    public void setNotes(Collection<Note> notes) {
        this.notes = notes;
    }
}
