package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Entity
@Access(AccessType.PROPERTY)
public class Agent extends Actor{

    // Constructors ----------------------------------------------------------------------

    public Agent() {
        super();
    }

    // Attributes ------------------------------------------------------------------------



    // Relationships ----------------------------------------------------------------------

    private Collection<Advertisement> advertisements;

    @Valid
    @NotNull
    @OneToMany(mappedBy = "agent")
    public Collection<Advertisement> getAdvertisements() {
        return advertisements;
    }

    public void setAdvertisements(Collection<Advertisement> advertisements) {
        this.advertisements = advertisements;
    }
}
