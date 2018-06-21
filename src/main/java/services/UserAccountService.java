package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import security.Authority;
import security.UserAccount;
import security.UserAccountRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;

@Service
@Transactional
public class UserAccountService {

    // Managed repository -----------------------------------------------------

    @Autowired
    private UserAccountRepository userAccountRepository;


    // Supporting services ----------------------------------------------------

    // Constructors -----------------------------------------------------------

    public UserAccountService() {
        super();
    }

    // Simple CRUD methods ----------------------------------------------------

    public UserAccount create(final String authorityName) {

        Assert.notNull(authorityName);

        UserAccount result;
        result = new UserAccount();
        result.setAuthorities(new ArrayList<Authority>());
        final Authority authority = new Authority();
        authority.setAuthority(authorityName);
        result.getAuthorities().add(authority);
        return result;
    }

    public UserAccount findOne(final int userAccountId) {

        UserAccount result;
        result = this.userAccountRepository.findOne(userAccountId);
        return result;
    }

    public Collection<UserAccount> findAll() {

        Collection<UserAccount> result;
        result = this.userAccountRepository.findAll();
        return result;
    }

    public UserAccount save(final UserAccount userAccount) {
        return this.userAccountRepository.save(userAccount);
    }

    // Other business methods -------------------------------------------------

}