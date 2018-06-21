package converters;


import domain.Actor;
import domain.Advertisement;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
public class AdvertisementToStringConverter implements Converter<Advertisement, String>{

	@Override
	public String convert(Advertisement advertisement) {
		
		String result;
		if(advertisement == null){
			result = null;
		}else{
			result = String.valueOf(advertisement.getId());
		}
		return result;
	}

}