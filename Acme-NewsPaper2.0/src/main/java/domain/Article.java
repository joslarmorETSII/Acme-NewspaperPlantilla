package domain;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Date;


@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
        @Index(columnList = "newsPaper_id")
})
public class Article extends DomainEntity {
    // Constructors -----------------------------------------------------------

    public Article() {
        super();
    }

    // Attributes -------------------------------------------------------------

    private String title;
    private Date moment;
    private String summary;
    private String body;
    private boolean finalMode;
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
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    public Date getMoment() {
        return moment;
    }

    public void setMoment(Date moment) {
        this.moment = moment;
    }

    @NotBlank
    @SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @NotBlank
    @SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
    @Column(length = 1024)
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public boolean getFinalMode() {
        return finalMode;
    }

    public void setFinalMode(boolean finalMode) {
        this.finalMode = finalMode;
    }

    public boolean getTaboo() {
        return taboo;
    }

    public void setTaboo(boolean taboo) {
        this.taboo = taboo;
    }

    // Relationships -----------------------------------------------------------------------

    private NewsPaper newsPaper;
    private Collection<FollowUp> followUps;
    private Collection<Picture> pictures;

    @Valid
    @NotNull
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    public Collection<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(Collection<Picture> pictures) {
        this.pictures = pictures;
    }

    @Valid
    @NotNull
    @OneToMany(mappedBy = "article")
    public Collection<FollowUp> getFollowUps() {
        return followUps;
    }

    public void setFollowUps(Collection<FollowUp> followUps) {
        this.followUps = followUps;
    }

    @Valid
    @NotNull
    @ManyToOne(optional = false)
    public NewsPaper getNewsPaper() {
        return newsPaper;
    }

    public void setNewsPaper(NewsPaper newsPaper) {
        this.newsPaper = newsPaper;
    }
}
