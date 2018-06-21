package domain;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.Date;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
        @Index(columnList = "customer_id"),@Index(columnList = "newspaper_id")
})
public class SubscribeNewsPaper extends DomainEntity {

    // Constructors -----------------------------------------------------------

    public SubscribeNewsPaper() {
        super();
    }

    // Attributes -------------------------------------------------------------

    private CreditCard	creditCard;
    private Date moment;

    @Valid
    @NotNull
    public CreditCard getCreditCard() {
        return this.creditCard;
    }

    public void setCreditCard(final CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    public Date getMoment() {
        return moment;
    }

    public void setMoment(Date moment) {
        this.moment = moment;
    }

    // RelationShips -------------------------------------------------------------

    private Customer customer;
    private NewsPaper newsPaper;

    @Valid
    @ManyToOne(optional = false)
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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
