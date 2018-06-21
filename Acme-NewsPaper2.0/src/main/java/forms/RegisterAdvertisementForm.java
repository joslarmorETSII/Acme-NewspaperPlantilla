package forms;

import domain.NewsPaper;
import org.hibernate.validator.constraints.*;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Collection;

public class RegisterAdvertisementForm {

    public RegisterAdvertisementForm() {
        super();
    }

    private String title;
    private String banner;
    private String targetPage;
    private NewsPaper newsPaper;
    private Integer newsPaperId;
    private String	    holder;
    private String	    brand;
    private String	    number;
    private Integer		expirationMonth;
    private Integer		expirationYear;
    private Integer		cvv;

    @NotNull
    public Integer getNewsPaperId() {
        return newsPaperId;
    }

    public void setNewsPaperId(Integer newsPaperId) {
        this.newsPaperId = newsPaperId;
    }

    public NewsPaper getNewsPaper() {
        return newsPaper;
    }

    public void setNewsPapers(NewsPaper newsPapers) {
        this.newsPaper = newsPapers;
    }

    @NotBlank
    @SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @URL
    @NotBlank
    @SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    @URL
    @NotBlank
    @SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
    public String getTargetPage() {
        return targetPage;
    }

    public void setTargetPage(String targetPage) {
        this.targetPage = targetPage;
    }

    @NotBlank
    @SafeHtml(whitelistType = WhiteListType.NONE)
    public String getHolder() {
        return holder;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    @NotBlank
    @SafeHtml(whitelistType = WhiteListType.NONE)
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @NotBlank
    @CreditCardNumber
    @Pattern(regexp = "^\\d+$")
    @SafeHtml(whitelistType = WhiteListType.NONE)
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Range(min = 1, max = 12)
    @NotNull
    public Integer getExpirationMonth() {
        return expirationMonth;
    }

    public void setExpirationMonth(Integer expirationMonth) {
        this.expirationMonth = expirationMonth;
    }

    @Min(2018)
    @NotNull
    public Integer getExpirationYear() {
        return expirationYear;
    }

    public void setExpirationYear(Integer expirationYear) {
        this.expirationYear = expirationYear;
    }

    @NotNull
    @Range(min = 100, max = 999)
    public Integer getCvv() {
        return cvv;
    }

    public void setCvv(Integer cvv) {
        this.cvv = cvv;
    }


}
