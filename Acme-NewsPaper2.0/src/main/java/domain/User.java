package domain;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Entity
@Access(AccessType.PROPERTY)
public class User extends Actor{

    // Constructors ----------------------------------------------------------------------

    public User() { super();}

    // Attributes ------------------------------------------------------------------------



    // Relationships ----------------------------------------------------------------------

    private Collection<Chirp> chirps;
    private Collection<NewsPaper> newsPapers;
    private Collection<User> followings;
    private Collection<User> followers;
    private Collection<Volume> volumes;

    @Valid
    @NotNull
    @OneToMany(mappedBy = "user")
    public Collection<Chirp> getChirps() {
        return chirps;
    }

    @Valid
    @NotNull
    @OneToMany(mappedBy = "publisher")
    public Collection<NewsPaper> getNewsPapers() {
        return newsPapers;
    }

    @Valid
    @ManyToMany(mappedBy = "followings",fetch= FetchType.EAGER)
    public Collection<User> getFollowers() {
        return this.followers;
    }

    public void setFollowers(Collection<User> followers) {
        this.followers = followers;
    }

    @Valid
    @ManyToMany(cascade = CascadeType.PERSIST, fetch= FetchType.EAGER)
    public Collection<User> getFollowings() {
        return this.followings;
    }

    public void setFollowings(Collection<User> following) {
        this.followings = following;
    }

    public void setChirps(Collection<Chirp> chirps) {
        this.chirps = chirps;
    }

    public void setNewsPapers(Collection<NewsPaper> newsPapers) {
        this.newsPapers = newsPapers;
    }

    @Valid
    @NotNull
    @OneToMany(mappedBy = "user")
    public Collection<Volume> getVolumes() {
        return volumes;
    }

    public void setVolumes(Collection<Volume> volumes) {
        this.volumes = volumes;
    }
}
