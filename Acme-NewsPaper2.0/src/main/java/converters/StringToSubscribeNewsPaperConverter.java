package converters;

import domain.SubscribeNewsPaper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;


@Component
@Transactional
public class StringToSubscribeNewsPaperConverter implements Converter<SubscribeNewsPaper, String> {

    @Override
    public String convert(SubscribeNewsPaper subscribeNewsPaper) {

        String result;
        if(subscribeNewsPaper == null){
            result = null;
        }else{
            result = String.valueOf(subscribeNewsPaper.getId());
        }
        return result;
    }
}
