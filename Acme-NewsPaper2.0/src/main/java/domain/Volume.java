package domain;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;


@Entity
@Access(AccessType.PROPERTY)
public class Volume extends DomainEntity {
    // Constructors -----------------------------------------------------------

    public Volume() {
        super();
    }

    // Attributes -------------------------------------------------------------

    private String title;
    private String description;
    private Integer anyo;

    @NotBlank
    @SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @NotBlank
    @SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotNull
    @Range(min = 2018,max = 3000)
    public Integer getAnyo() {
        return anyo;
    }

    public void setAnyo(Integer anyo) {
        this.anyo = anyo;
    }

    // Relationships -----------------------------------------------------------------------

    private Collection<SubscribeVolume> subscriptionVolumes;
    private Collection<NewsPaper> newsPapersVolume;
    private User user;

    @Valid
    @NotNull
    @ManyToOne(optional = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Valid
    @NotNull
    @OneToMany
    public Collection<SubscribeVolume> getSubscriptionVolumes() {
        return subscriptionVolumes;
    }

    public void setSubscriptionVolumes(Collection<SubscribeVolume> subscriptionVolumes) {
        this.subscriptionVolumes = subscriptionVolumes;
    }

    @Valid
    @NotNull
    @ManyToMany
    public Collection<NewsPaper> getNewsPapersVolume() {
        return newsPapersVolume;
    }

    public void setNewsPapersVolume(Collection<NewsPaper> newsPapersVolume) {
        this.newsPapersVolume = newsPapersVolume;
    }
}
