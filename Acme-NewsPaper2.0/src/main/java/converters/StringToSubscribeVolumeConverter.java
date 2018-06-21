package converters;

import domain.SubscribeVolume;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
public class StringToSubscribeVolumeConverter implements Converter<SubscribeVolume, String>{

    @Override
    public String convert(SubscribeVolume subscribeVolume) {

        String result;
        if(subscribeVolume == null){
            result = null;
        }else{
            result = String.valueOf(subscribeVolume.getId());
        }
        return result;
    }
}
