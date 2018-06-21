package domain;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Entity
@Access(AccessType.PROPERTY)
public class Note extends DomainEntity{

    // Constructors -----------------------------------------------------------

    public Note() {
        super();
    }

    // Attributes -------------------------------------------------------------

    private String ticker;
    private String title;
    private String description;
    private Integer gauge;
    private Date displayMoment;

    @Column(unique = true)
    @NotBlank
    @Pattern(regexp = "\\d{2}-\\d{2}-\\d{2}")
    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    @NotBlank
    @SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotNull
    @Range(min = 1,max = 3)
    public Integer getGauge() {
        return gauge;
    }

    public void setGauge(Integer gauge) {
        this.gauge = gauge;
    }

    @Future
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    public Date getDisplayMoment() {
        return displayMoment;
    }

    public void setDisplayMoment(Date displayMoment) {
        this.displayMoment = displayMoment;
    }

    // Relationships ----------------------------------------------------------
    private Administrator administrator;
    private NewsPaper newsPaper;

    @Valid
    @ManyToOne(optional = false)
    public Administrator getAdministrator() {
        return administrator;
    }

    public void setAdministrator(Administrator administrator) {
        this.administrator = administrator;
    }

    @Valid
    @ManyToOne(optional = false)
    public NewsPaper getNewsPaper() {
        return newsPaper;
    }

    public void setNewsPaper(NewsPaper newsPaper) {
        this.newsPaper = newsPaper;
    }
}
