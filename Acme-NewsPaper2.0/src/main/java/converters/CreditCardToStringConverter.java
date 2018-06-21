
package converters;

import domain.CreditCard;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.net.URLEncoder;

@Component
@Transactional
public class CreditCardToStringConverter implements Converter<CreditCard, String> {

    @Override
    public String convert(final CreditCard creditCard) {
        String result;
        StringBuilder builder;

        if (creditCard == null)
            result = null;
        else
            try {
                builder = new StringBuilder();
                builder.append(URLEncoder.encode(creditCard.getBrand(), "UTF-8"));
                builder.append("|");
                builder.append(URLEncoder.encode(Integer.toString(creditCard.getCvv()), "UTF-8"));
                builder.append("|");
                builder.append(URLEncoder.encode(Integer.toString(creditCard.getExpirationMonth()), "UTF-8"));
                builder.append("|");
                builder.append(URLEncoder.encode(Integer.toString(creditCard.getExpirationYear()), "UTF-8"));
                builder.append("|");
                builder.append(URLEncoder.encode(creditCard.getHolder(), "UTF-8"));
                builder.append("|");
                builder.append(URLEncoder.encode(creditCard.getNumber(), "UTF-8"));
                builder.append("|");
                result = builder.toString();

            } catch (final Throwable oops) {
                throw new RuntimeException(oops);
            }

        return result;
    }
}

