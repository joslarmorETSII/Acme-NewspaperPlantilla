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

    // Relationships ----------------------------------------------------------------------

    private Collection<Pembas> pembass;


    @NotNull
    @Valid
    @OneToMany(mappedBy = "administrator")
    public Collection<Pembas> getPembass() {
        return pembass;
    }

    public void setPembass(Collection<Pembas> pembass) {
        this.pembass = pembass;
    }
}
