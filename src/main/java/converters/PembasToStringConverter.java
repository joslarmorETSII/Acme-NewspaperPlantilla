package converters;


import domain.Pembas;
import domain.NewsPaper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
public class PembasToStringConverter implements Converter<Pembas, String>{

	@Override
	public String convert(Pembas pembas) {
		
		String result;
		if(pembas == null){
			result = null;
		}else{
			result = String.valueOf(pembas.getId());
		}
		return result;
	}

}