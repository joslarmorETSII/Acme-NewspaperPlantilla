package converters;

import domain.Picture;
import domain.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import repositories.PictureRepository;
import repositories.UserRepository;

import javax.transaction.Transactional;

@Component
@Transactional
public class StringToPictureConverter implements Converter<String, Picture> {

    @Autowired
    private PictureRepository pictureRepository;

    @Override
    public Picture convert(final String text) {
        Picture result;
        int id;

        try {
            if (StringUtils.isEmpty(text))
                result = null;
            else {
                id = Integer.valueOf(text);
                result = pictureRepository.findOne(id);
            }
        } catch (final Throwable oops) {
            throw new IllegalArgumentException(oops);
        }
        return result;
    }
}