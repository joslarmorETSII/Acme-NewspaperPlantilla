package domain;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Entity
@Access(AccessType.PROPERTY)
public class Customer extends Actor {


    // Constructors ----------------------------------------------------------

    public Customer() {
        super();
    }

    // Relationships ----------------------------------------------------------

    private Collection<SubscribeNewsPaper> subscriptionsToNewspapers;
    private Collection<SubscribeVolume> subscriptionsToVolumes;

    @NotNull
    @Valid
    @OneToMany(mappedBy = "customer")
    public Collection<SubscribeNewsPaper> getSubscriptionsToNewspapers() {
        return subscriptionsToNewspapers;
    }

    @Valid
    @NotNull
    @OneToMany(mappedBy = "customer")
    public Collection<SubscribeVolume> getSubscriptionsToVolumes() {
        return subscriptionsToVolumes;
    }

    public void setSubscriptionsToNewspapers(Collection<SubscribeNewsPaper> subscriptionsToNewspaper) {
        this.subscriptionsToNewspapers = subscriptionsToNewspaper;
    }

    public void setSubscriptionsToVolumes(Collection<SubscribeVolume> subscriptionsToVolumes) {
        this.subscriptionsToVolumes = subscriptionsToVolumes;
    }
}
