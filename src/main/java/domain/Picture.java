package domain;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.Valid;

@Entity
@Access(AccessType.PROPERTY)
public class Picture extends DomainEntity{

    // Constructors -----------------------------------------------------------

    public Picture() {
        super();
    }

    // Attributes -------------------------------------------------------------

    private String url;

    @URL
    @SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
    @NotBlank
    public String getUrl() {
        return url;
    }

    public void setUrl(String picture) {
        this.url = picture;
    }

    // Relationships -----------------------------------------------------------------------

    private Article article;
    private FollowUp followUp;

    @Valid
    @ManyToOne(optional = true)
    public FollowUp getFollowUp() {
        return followUp;
    }

    public void setFollowUp(FollowUp followUp) {
        this.followUp = followUp;
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
