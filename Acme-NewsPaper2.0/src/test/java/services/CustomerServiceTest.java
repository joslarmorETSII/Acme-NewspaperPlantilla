package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import services.CustomerService;
import utilities.AbstractTest;
import domain.Customer;

@Transactional
@ContextConfiguration(locations = {
        "classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class CustomerServiceTest extends AbstractTest {

    // The SUT
    // ====================================================

    @Autowired
    private CustomerService customerService;


    // Tests
    // ====================================================

    /*  FUNCTIONAL REQUIREMENT:
     *  An actor who is not authenticated must be able to:
          -Register to the system as a customer.
*/

    public void customerRegisterTest(final String username, final String password, final String passwordRepeat, final String name, final String surname, final String phone, final String email, final String postalAddress, final Class<?> expected) {
        Class<?> caught = null;
        startTransaction();

        try {

            final Customer result = this.customerService.create();


            result.getUserAccount().setUsername(username);
            result.setName(name);
            result.setSurname(surname);
            result.setPhone(phone);
            result.setEmail(email);
            result.setPostalAddresses(postalAddress);
            result.getUserAccount().setPassword(new Md5PasswordEncoder().encodePassword(password, null));


            this.customerService.save(result);
            customerService.flush();
            this.unauthenticate();

        } catch (final Throwable oops) {

            caught = oops.getClass();

        }

        this.checkExceptions(expected, caught);
        rollbackTransaction();
    }

    //Drivers
    // ===================================================

    @Test
    public void driverCustomerRegisterTest() {

        final Object testingData[][] = {
                // Alguien sin registrar/logueado -> true
                {
                        "cutomer33", "cutomer33", "cutomer33", "customerTestName", "customerTestName", "+34123456789", "customerTest@customerTest.com", "addressTest", null
                },
                // todos los valores estan a null -> false
                {
                        null, null, null, null, null,  null, null,null, ConstraintViolationException.class
                },
                // El patron de telefono incorrecto -> false
                {
                        "cutomer33", "cutomer33", "customer33", "customerTestName", "customerTestName", "+3 412 3456789", "customerTest@customerTest.com", "addressTest", ConstraintViolationException.class
                },

        };
        for (int i = 0; i < testingData.length; i++)
            this.customerRegisterTest((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
                    (String) testingData[i][7], (Class<?>) testingData[i][8]);
    }
}