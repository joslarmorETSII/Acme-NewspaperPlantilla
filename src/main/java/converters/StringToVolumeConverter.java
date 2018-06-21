package converters;


import domain.Volume;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import repositories.VolumeRepository;

import javax.transaction.Transactional;

@Component
@Transactional
public class StringToVolumeConverter implements Converter<String, Volume>{

@Autowired
VolumeRepository volumeRepository;

	@Override
	public Volume convert(String text) {
		Volume result;
		int id;
		
		try{
			if(StringUtils.isEmpty(text)){
				result = null;
			}else{
				id = Integer.valueOf(text);
				result = volumeRepository.findOne(id);
			}
		}catch(Throwable oops){
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}