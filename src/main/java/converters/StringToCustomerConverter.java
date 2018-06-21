package converters;

import domain.Customer;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import repositories.CustomerRepository;

public class StringToCustomerConverter implements Converter<String, Customer>{

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer convert(final String text) {
        Customer result;
        int id;

        try {
            if (StringUtils.isEmpty(text))
                result = null;
            else {
                id = Integer.valueOf(text);
                result = customerRepository.findOne(id);
            }
        } catch (final Throwable oops) {
            throw new IllegalArgumentException(oops);
        }
        return result;
    }
}
