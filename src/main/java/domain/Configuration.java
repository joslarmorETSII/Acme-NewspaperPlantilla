package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.*;

@Entity
@Access(AccessType.PROPERTY)
public class Configuration extends DomainEntity {

    // Constructors ---------------------------------------------

    public Configuration() {
        super();
    }


    // Attributes -----------------------------------------------

    private String              name;
    private String				banner;
    private String				englishWelcome;
    private String				spanishWelcome;
    private Collection<String>	tabooWords;

    @NotBlank
    @SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotBlank
    @URL
    @SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
    public String getBanner() {
        return this.banner;
    }

    public void setBanner(final String banner) {
        this.banner = banner;
    }

    @NotBlank
    @SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
    public String getEnglishWelcome() {
        return this.englishWelcome;
    }

    public void setEnglishWelcome(final String englishWelcome) {
        this.englishWelcome = englishWelcome;
    }

    @NotBlank
    @SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
    public String getSpanishWelcome() {
        return this.spanishWelcome;
    }

    public void setSpanishWelcome(final String spanishWelcome) {
        this.spanishWelcome = spanishWelcome;
    }

    @NotEmpty
    @ElementCollection
    public Collection<String> getTabooWords() {
        return this.tabooWords;
    }

    public void setTabooWords(final Collection<String> tabooWords) {
        this.tabooWords = tabooWords;
    }


    // Relationships ----------------------------------------------------------

}

