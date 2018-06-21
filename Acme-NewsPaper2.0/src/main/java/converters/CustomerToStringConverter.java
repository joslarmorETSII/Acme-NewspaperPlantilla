package converters;

import domain.Customer;
import domain.Customer;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
public class CustomerToStringConverter implements Converter<Customer,String> {

    @Override
    public String convert(final Customer customer){
        String result;

        if (customer == null)
            result = null;
        else
            result = String.valueOf(customer.getId());

        return result;
    }
}
