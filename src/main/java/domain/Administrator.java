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

    private Collection<Audit> audits;


    @NotNull
    @Valid
    @OneToMany(mappedBy = "administrator")
    public Collection<Audit> getAudits() {
        return audits;
    }

    public void setAudits(Collection<Audit> audits) {
        this.audits = audits;
    }
}
