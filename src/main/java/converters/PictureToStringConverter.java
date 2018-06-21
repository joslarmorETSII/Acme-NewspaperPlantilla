package converters;


import domain.Actor;
import domain.Picture;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
public class PictureToStringConverter implements Converter<Picture, String>{

	@Override
	public String convert(Picture picture) {
		
		String result;
		if(picture == null){
			result = null;
		}else{
			result = String.valueOf(picture.getId());
		}
		return result;
	}

}