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
    private Collection<Tromem> tromems;

    @NotNull
    @Valid
    @OneToMany(mappedBy = "administrator")
    public Collection<Tromem> getTromems() {
        return tromems;
    }

    public void setTromems(Collection<Tromem> tromems) {
        this.tromems = tromems;
    }
}
