package converters;

import domain.Tromem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import repositories.TromemRepository;

import javax.transaction.Transactional;

@Component
@Transactional
public class StringToTromemConverter implements Converter<String, Tromem> {

    @Autowired
    private TromemRepository tromemRepository;

    @Override
    public Tromem convert(String text) {
        Tromem result;
        int id;

        try{
            if(StringUtils.isEmpty(text)){
                result = null;
            }else{
                id = Integer.valueOf(text);
                result = tromemRepository.findOne(id);
            }
        }catch(Throwable oops){
            throw new IllegalArgumentException(oops);
        }
        return result;
    }
}
