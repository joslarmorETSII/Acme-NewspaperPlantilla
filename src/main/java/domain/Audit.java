

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
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Audit extends DomainEntity {

    // Constructor -------------------------------------------------------------------

    // Attributes --------------------------------------------------------------------

    private String	code;
    private Integer	gauge;
    private String	title;
    private String	description;
    private Date	moment;
    private Boolean finalMode;


    @Column(unique = true)
    @NotBlank
    @Pattern(regexp = "^\\d{2}:\\w{2}:\\d{2}:\\d{5}:\\d{2}$")
    public String getCode() {
        return this.code;
    }

    public void setCode(final String code) {
        this.code = code;
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
    public String getTitle() {
        return this.title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }


    @Length(max = 250)
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

    public Boolean getFinalMode() {
        return finalMode;
    }

    public void setFinalMode(Boolean finalMode) {
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
