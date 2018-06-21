package forms;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

public class UserForm {

    public UserForm() {
        super();
    }

    private String	username;
    private String	password;
    private String	repeatPassword;
    private String	name;
    private String	surname;
    private String	phone;
    private String	email;
    private String  postalAddresses;
    private boolean	check;

    @Column(unique = true)
    @Size(min = 5, max = 32)
    @SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
    public String getUsername() {
        return this.username;
    }
    public void setUsername(final String username) {
        this.username = username;
    }

    @Size(min = 5, max = 32)
    @SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
    public String getPassword() {
        return this.password;
    }
    public void setPassword(final String password) {
        this.password = password;
    }

    @Size(min = 5, max = 32)
    @SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
    public String getRepeatPassword() {
        return this.repeatPassword;
    }
    public void setRepeatPassword(final String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    @NotBlank
    @SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
    public String getName() {
        return this.name;
    }
    public void setName(final String name) {
        this.name = name;
    }

    @NotBlank
    @SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
    public String getSurname() {
        return this.surname;
    }
    public void setSurname(final String surname) {
        this.surname = surname;
    }

    @Pattern(regexp = "^\\+?([0-9]{0,15}$)")
    @SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
    public String getPhone() {
        return this.phone;
    }
    public void setPhone(final String phone) {
        this.phone = phone;
    }

    @Email
    @NotBlank
    @SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
    public String getEmail() {
        return this.email;
    }
    public void setEmail(final String email) {
        this.email = email;
    }

    @SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
    public String getPostalAddresses() {
        return this.postalAddresses;
    }
    public void setPostalAddresses(final String postalAddresses) {
        this.postalAddresses = postalAddresses;
    }


    public boolean getCheck() {
        return this.check;
    }
    public void setCheck(final boolean check) {
        this.check = check;
    }
}
