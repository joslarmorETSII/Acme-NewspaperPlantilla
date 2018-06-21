package domain;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Date;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
        @Index(columnList = "publisher_id")
})
public class
NewsPaper extends DomainEntity {

    // Constructors -----------------------------------------------------------

    public NewsPaper() {
        super();
    }

    // Attributes -------------------------------------------------------------

    private String title;
    private Date publicationDate;
    private String description;
    private String picture;
    private boolean published;
    private boolean modePrivate;
    private boolean taboo;


    @NotBlank
    @SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    @NotBlank
    @SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @URL
    @SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public boolean getPublished() {
        return published;
    }


    public void setPublished(boolean published) {
        this.published = published;
    }

    public boolean getTaboo() {
        return taboo;
    }

    public void setTaboo(boolean taboo) {
        this.taboo = taboo;
    }

    public boolean isModePrivate() {
        return modePrivate;
    }

    public void setModePrivate(boolean modePrivate) {
        this.modePrivate = modePrivate;
    }

    // Relationships -----------------------------------------------------------------------

    private Collection<Article> articles;
    private User publisher;
    private Collection<SubscribeNewsPaper> subscriptions;
    private Collection<Advertisement> advertisements;
    private Collection<Volume> volumes;

    @Valid
    @NotNull
    @ManyToOne(optional = false)
    public User getPublisher() {
        return publisher;
    }

    public void setPublisher(User publisher) {
        this.publisher = publisher;
    }

    @Valid
    @NotNull
    @OneToMany(mappedBy = "newsPaper")
    public Collection<SubscribeNewsPaper> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(Collection<SubscribeNewsPaper> subscriptions) {
        this.subscriptions = subscriptions;
    }

    @Valid
    @NotNull
    @OneToMany(mappedBy = "newsPaper")
    public Collection<Article> getArticles() {
        return articles;
    }

    public void setArticles(Collection<Article> articles) {
        this.articles = articles;
    }

    @Valid
    @NotNull
    @ManyToMany
    public Collection<Advertisement> getAdvertisements() {
        return advertisements;
    }

    public void setAdvertisements(Collection<Advertisement> advertisements) {
        this.advertisements = advertisements;
    }

    @Valid
    @NotNull
    @ManyToMany(mappedBy = "newsPapersVolume")
    public Collection<Volume> getVolumes() { return volumes; }

    public void setVolumes(Collection<Volume> volumes) {
        this.volumes = volumes;
    }
}
