package converters;


import domain.Pembas;
import domain.NewsPaper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import repositories.PembasRepository;
import repositories.NewsPaperRepository;

import javax.transaction.Transactional;

@Component
@Transactional
public class StringToPembasConverter implements Converter<String, Pembas>{

@Autowired
PembasRepository pembasRepository;

	@Override
	public Pembas convert(String text) {
		Pembas result;
		int id;
		
		try{
			if(StringUtils.isEmpty(text)){
				result = null;
			}else{
				id = Integer.valueOf(text);
				result = pembasRepository.findOne(id);
			}
		}catch(Throwable oops){
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}