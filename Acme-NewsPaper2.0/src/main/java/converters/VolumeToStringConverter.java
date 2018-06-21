package converters;


import domain.Volume;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
public class VolumeToStringConverter implements Converter<Volume, String>{

	@Override
	public String convert(Volume volume) {
		
		String result;
		if(volume == null){
			result = null;
		}else{
			result = String.valueOf(volume.getId());
		}
		return result;
	}

}