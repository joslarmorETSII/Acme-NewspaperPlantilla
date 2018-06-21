package services;

import controllers.AbstractController;
import domain.*;
import forms.SubscribeForm;
import forms.SubscribeVolumeForm;
import forms.UserForm;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.AttributeAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import repositories.CustomerRepository;
import security.LoginService;
import security.UserAccount;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

@Service
@Transactional
public class CustomerService {

    // Customer repository -----------------------------------------------------

    @Autowired
    private CustomerRepository customerRepository;

    // Supporting services ----------------------------------------------------

    @Autowired
    private UserAccountService userAccountService;


    @Autowired
    private ActorService actorService;



    // Constructors -----------------------------------------------------------

    public CustomerService() {
        super();
    }

    // Simple CRUD methods ----------------------------------------------------

    public Customer create() {
        Customer result;

        result = new Customer();

        result.setSubscriptionsToNewspapers(new ArrayList<SubscribeNewsPaper>());
        result.setUserAccount(userAccountService.create("CUSTOMER"));
        result.setFolders(new ArrayList<Folder>());
        result.setSubscriptionsToVolumes(new ArrayList<SubscribeVolume>());

        return result;
    }

    public Customer findOne(final int customerId) {

        Customer result;
        result = this.customerRepository.findOne(customerId);
        return result;
    }

    public Collection<Customer> findAll() {

        Collection<Customer> result;
        result = this.customerRepository.findAll();
        return result;
    }

    public Customer save(Customer customer) {

        Assert.notNull(customer);
        Customer result;
        Collection<Folder> folders;


        if (customer.getId() == 0) {
            result = this.customerRepository.save(customer);
            folders = actorService.generateFolders(result);
            customer.setFolders(folders);
            result = this.customerRepository.save(result);

        } else
            result = this.customerRepository.save(customer);

        return result;
    }

    // Other business methods

    public Customer findByPrincipal() {

        Customer result;
        final UserAccount customerAccount = LoginService.getPrincipal();
        //Assert.notNull(customerAccount);
        result = this.findByCustomerAccountId(customerAccount.getId());
        //Assert.notNull(result);
        return result;
    }

    public Customer findByCustomerAccountId(final int customerAccountId) {

        Customer result;
        result = this.customerRepository.findByCustomerAccountId(customerAccountId);
        return result;
    }


    public CreditCard reconstructSubscribe(SubscribeForm subscribeForm, final BindingResult binding) {
        CreditCard creditCard = new CreditCard();

        creditCard.setBrand(subscribeForm.getBrand());
        creditCard.setCvv(subscribeForm.getCvv());
        creditCard.setExpirationMonth(subscribeForm.getExpirationMonth());
        creditCard.setExpirationYear(subscribeForm.getExpirationYear());
        creditCard.setHolder(subscribeForm.getHolder());
        creditCard.setNumber(subscribeForm.getNumber());

        checkMonth(subscribeForm.getExpirationMonth(),subscribeForm.getExpirationYear(),binding);

        return creditCard;
    }

    public Customer reconstruct(UserForm userForm, final BindingResult binding) {

        Customer result;

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

        if (!result && password.length()>=4 && passwordRepeat.length()>=4) {
            codigos = new String[1];
            codigos[0] = "customer.password.mismatch";
            error = new FieldError("userForm", "repeatPassword", password, false, codigos, null, "password mismatch");
            binding.addError(error);
        }

        return result;
    }

    public void flush() {
        customerRepository.flush();
    }

    public CreditCard reconstructSubscribeVolume(SubscribeVolumeForm subscribeVolumeForm, BindingResult binding) {
        CreditCard creditCard = new CreditCard();

        creditCard.setBrand(subscribeVolumeForm.getBrand());
        creditCard.setCvv(subscribeVolumeForm.getCvv());
        creditCard.setExpirationMonth(subscribeVolumeForm.getExpirationMonth());
        creditCard.setExpirationYear(subscribeVolumeForm.getExpirationYear());
        creditCard.setHolder(subscribeVolumeForm.getHolder());
        creditCard.setNumber(subscribeVolumeForm.getNumber());

        checkMonth(subscribeVolumeForm.getExpirationMonth(),subscribeVolumeForm.getExpirationYear(),binding);
        return creditCard;
    }

    public  Collection<NewsPaper> newsPapersSubscribed(int custometId){
        return customerRepository.newsPapersSubscribed(custometId);
    }
    private boolean checkMonth(Integer month, Integer year, BindingResult binding) {
        FieldError error;
        String[] codigos;
        boolean result;
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        Integer actualMonth = c.get(Calendar.MONTH)+1;
        Integer actualYear = c.get(Calendar.YEAR);

        if (month!=null && year!=null)
            result = actualYear.equals(year) && month<actualMonth;
        else
            result = false;
        if (result) {
            codigos = new String[1];
            codigos[0] = "creditCard.month.invalid";
            error = new FieldError("registerAdvertisementForm", "expirationMonth", month, false, codigos, null, "should not be in the past");
            binding.addError(error);
        }
        return result;
    }
}
