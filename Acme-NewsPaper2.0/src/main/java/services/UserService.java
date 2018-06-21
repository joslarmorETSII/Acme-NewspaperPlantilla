package services;


import domain.*;
import forms.UserForm;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import repositories.UserRepository;
import security.LoginService;
import security.UserAccount;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;

@Service
@Transactional
public class UserService {

    // Managed repository -----------------------------------------------------

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ActorService actorService;


    // Supporting services ----------------------------------------------------

    @Autowired
    private UserAccountService userAccountService;


    // Constructors -----------------------------------------------------------

    public UserService() { super(); }

    // Simple CRUD methods ----------------------------------------------------

    public User create() {
        User result;

        result = new User();
        result.setChirps( new ArrayList<Chirp>());
        result.setFollowers(new ArrayList<User>());
        result.setFollowings(new ArrayList<User>());
        result.setNewsPapers(new ArrayList<NewsPaper>());
        result.setUserAccount(this.userAccountService.create("USER"));
        result.setVolumes(new ArrayList<Volume>());
        result.setFolders(new ArrayList<Folder>());

        return result;
    }

    public User findOne(final int userId) {

        User result;
        result = this.userRepository.findOne(userId);
        return result;
    }

    public Collection<User> findAll() {

        Collection<User> result;
        result = this.userRepository.findAll();
        return result;
    }

    public User save(User user) {
        Assert.notNull(user);
        User result;
        Collection<Folder> folders;

        if(user.getId()==0){
            result = userRepository.save(user);
            folders = actorService.generateFolders(result);
            result.setFolders(folders);
            result = userRepository.save(result);
        }else {
            result = this.userRepository.save(user);
        }
        return result;
    }

    // Other business methods -----------------------------------------------------------------

    public User findByPrincipal() {

        User result;
        final UserAccount userAccount = LoginService.getPrincipal();
        result = this.findByUserAccountId(userAccount.getId());
        return result;
    }


    public User findByUserAccountId(int userAccountId) {

        User result;
        result = this.userRepository.findByUserAccountId(userAccountId);
        return result;
    }

    public User reconstruct(UserForm userForm, final BindingResult binding) {

        User result;

        result = this.create();
        result.getUserAccount().setUsername(userForm.getUsername());
        result.setName(userForm.getName());
        result.setSurname(userForm.getSurname());
        result.setPhone(userForm.getPhone());
        result.setEmail(userForm.getEmail());
        result.setPostalAddresses(userForm.getPostalAddresses());
        result.getUserAccount().setPassword(new Md5PasswordEncoder().encodePassword(userForm.getPassword(), null));

        this.comprobarContrasena(userForm.getPassword(), userForm.getRepeatPassword(), binding);

        return result;
    }

    private boolean comprobarContrasena(final String password, final String passwordRepeat, final BindingResult binding) {
        FieldError error;
        String[] codigos;
        boolean result;

        if (StringUtils.isNotBlank(password) && StringUtils.isNotBlank(passwordRepeat))
            result = password.equals(passwordRepeat);
        else
            result = false;

        if (!result && password.length()>=5 && passwordRepeat.length()>=5) {
            codigos = new String[1];
            codigos[0] = "user.password.mismatch";
            error = new FieldError("userForm", "repeatPassword", password, false, codigos, null, "password mismatch");
            binding.addError(error);
        }

        return result;
    }


    public Collection<User> follow(int userId){
        Assert.notNull(userId);
        User principal;
        User userToFollow;

        principal = findByPrincipal();
        userToFollow = userRepository.findOne(userId);
        Assert.isTrue(!principal.getFollowings().contains(userToFollow));

        principal.getFollowings().add(userToFollow);

        Assert.isTrue(principal.getId() != userId);

        save(principal);
        save(userToFollow);

        return principal.getFollowings();
    }

    public Collection<User> unfollow(int userId){
        Assert.notNull(userId);
        User principal;
        User userToUnFollow;

        principal = findByPrincipal();
        userToUnFollow = userRepository.findOne(userId);
        Assert.isTrue(principal.getFollowings().contains(userToUnFollow));
        principal.getFollowings().remove(userToUnFollow);
        userToUnFollow.getFollowers().remove(principal);

        Assert.isTrue(principal.getId() != userId);

        save(principal);
        save(userToUnFollow);

        return principal.getFollowings();
    }


    public void flush() {
        userRepository.flush();
    }
}