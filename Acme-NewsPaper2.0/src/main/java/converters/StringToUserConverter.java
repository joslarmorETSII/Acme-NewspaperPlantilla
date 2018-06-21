package converters;

import domain.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import repositories.UserRepository;

import javax.transaction.Transactional;

@Component
@Transactional
public class StringToUserConverter  implements Converter<String, User> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User convert(final String text) {
        User result;
        int id;

        try {
            if (StringUtils.isEmpty(text))
                result = null;
            else {
                id = Integer.valueOf(text);
                result = userRepository.findOne(id);
            }
        } catch (final Throwable oops) {
            throw new IllegalArgumentException(oops);
        }
        return result;
    }
}