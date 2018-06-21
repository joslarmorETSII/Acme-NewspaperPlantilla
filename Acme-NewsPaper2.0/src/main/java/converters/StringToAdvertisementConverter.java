package converters;


import domain.Actor;
import domain.Advertisement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import repositories.ActorRepository;
import repositories.AdvertisementRepository;

import javax.transaction.Transactional;

@Component
@Transactional
public class StringToAdvertisementConverter implements Converter<String, Advertisement>{
@Autowired
AdvertisementRepository advertisementRepository;

	@Override
	public Advertisement convert(String text) {
		Advertisement result;
		int id;
		
		try{
			if(StringUtils.isEmpty(text)){
				result = null;
			}else{
				id = Integer.valueOf(text);
				result = advertisementRepository.findOne(id);
			}
		}catch(Throwable oops){
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}