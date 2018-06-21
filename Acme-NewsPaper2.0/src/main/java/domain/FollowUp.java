package domain;


import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.persistence.ElementCollection;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Date;

@Entity
@Access(AccessType.PROPERTY)
public class FollowUp extends DomainEntity {

    // Constructors -----------------------------------------------------------

    public FollowUp() {
        super();
    }

    // Attributes -------------------------------------------------------------

    private String title;
    private Date moment;
    private String summary;
    private String text;

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

    @Column(length = 1024)
    @NotBlank
    @SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    // Relationships -----------------------------------------------------------------------
    private Article article;
    private Collection<Picture> pictures;

    @Valid
    @NotNull
    @OneToMany(mappedBy = "followUp", cascade = CascadeType.ALL)
    public Collection<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(Collection<Picture> pictures) {
        this.pictures = pictures;
    }

    @Valid
    @ManyToOne(optional = true)
    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
}
