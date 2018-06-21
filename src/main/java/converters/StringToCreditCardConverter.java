package converters;

import domain.CreditCard;
import java.net.URLDecoder;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class StringToCreditCardConverter implements Converter<String, CreditCard> {

    @Override
    public CreditCard convert(final String text) {
        CreditCard result;
        String parts[];

        if (text == null || text.isEmpty())
            result = null;
        else
            try {
                parts = text.split("\\|");
                result = new CreditCard();
                result.setBrand(URLDecoder.decode(parts[0], "UTF-8"));
                result.setCvv(Integer.valueOf(URLDecoder.decode(parts[1], "UTF-8")));
                result.setExpirationMonth(Integer.valueOf(URLDecoder.decode(parts[2], "UTF-8")));
                result.setExpirationYear(Integer.valueOf(URLDecoder.decode(parts[3], "UTF-8")));
                result.setHolder(URLDecoder.decode(parts[4], "UTF-8"));
                result.setNumber(URLDecoder.decode(parts[5], "UTF-8"));

            } catch (final Throwable oops) {
                throw new RuntimeException(oops);

            }

        return result;
    }
}
