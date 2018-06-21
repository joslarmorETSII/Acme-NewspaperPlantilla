package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

@Embeddable
@Access(AccessType.PROPERTY)
public class CreditCard{

    private String	    holder;
    private String	    brand;
    private String	    number;
    private Integer		expirationMonth;
    private Integer		expirationYear;
    private Integer		cvv;


    public CreditCard() {
        super();
    }

    @NotBlank
    @SafeHtml(whitelistType = WhiteListType.NONE)
    public String getHolder() {
        return this.holder;
    }

    public void setHolder(final String holder) {
        this.holder = holder;
    }

    @NotBlank
    @SafeHtml(whitelistType = WhiteListType.NONE)
    public String getBrand() {
        return this.brand;
    }

    public void setBrand(final String brand) {
        this.brand = brand;
    }

    @Pattern(regexp = "^\\d+$")
    @CreditCardNumber
    public String getNumber() {
        return this.number;
    }

    public void setNumber(final String number) {
        this.number = number;
    }

    @Range(min = 1, max = 12)
    @NotNull
    public Integer getExpirationMonth() {
        return this.expirationMonth;
    }

    public void setExpirationMonth(final Integer expirationMonth) {
        this.expirationMonth = expirationMonth;
    }

    @Min(2018)
    @NotNull
    public Integer getExpirationYear() {
        return this.expirationYear;
    }

    public void setExpirationYear(final Integer expirationYear) {
        this.expirationYear = expirationYear;
    }

    @NotNull
    @Range(min = 100, max = 999)
    public Integer getCvv() {
        return this.cvv;
    }

    public void setCvv(final Integer cvv) {
        this.cvv = cvv;
    }



}