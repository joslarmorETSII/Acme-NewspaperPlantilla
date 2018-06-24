

package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Audit extends DomainEntity {

    // Constructor -------------------------------------------------------------------

    // Attributes --------------------------------------------------------------------

    private String	ticker;
    private Integer	gauge;
    private String	title;
    private String	description;
    private Date	moment;
    private boolean finalMode;


    @Column(unique = true)
    @NotBlank
    @Pattern(regexp = "^\\d{2}:\\w{2}:\\d{2}:\\d{5}:\\d{2}$")
    public String getTicker() {
        return this.ticker;
    }

    public void setTicker(final String ticker) {
        this.ticker = ticker;
    }
    @NotNull
    @Range(min = 1, max = 3)
    public Integer getGauge() {
        return this.gauge;
    }

    public void setGauge(final Integer gauge) {
        this.gauge = gauge;
    }

    @NotBlank
    @Length(max = 100)
    @SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
    public String getTitle() {
        return this.title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    @NotBlank
    @Length(max = 250)
    @SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }


    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    public Date getMoment() {
        return this.moment;
    }

    public void setMoment(final Date moment) {
        this.moment = moment;
    }

    public boolean getFinalMode() {
        return finalMode;
    }

    public void setFinalMode(boolean finalMode) {
        this.finalMode = finalMode;
    }

    // Relationships ----------------------------------------------------------

    private Administrator administrator;
    private NewsPaper	newsPaper;


    @Valid
    @NotNull
    @ManyToOne(optional = false)
    public Administrator getAdministrator() {
        return this.administrator;
    }

    public void setAdministrator(final Administrator administrator) {
        this.administrator = administrator;
    }

    @Valid
    @ManyToOne(optional = true)
    public NewsPaper getNewsPaper() {
        return this.newsPaper;
    }

    public void setNewsPaper(final NewsPaper newsPaper) {
        this.newsPaper = newsPaper;
    }

}
