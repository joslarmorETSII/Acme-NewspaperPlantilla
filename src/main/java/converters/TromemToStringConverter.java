package converters;

import domain.Tromem;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import javax.transaction.Transactional;

@Component
@Transactional
public class TromemToStringConverter implements Converter<Tromem, String> {

    @Override
    public String convert(Tromem entity) {

        String result;
        if(entity == null){
            result = null;
        }else{
            result = String.valueOf(entity.getId());
        }
        return result;
    }
}
