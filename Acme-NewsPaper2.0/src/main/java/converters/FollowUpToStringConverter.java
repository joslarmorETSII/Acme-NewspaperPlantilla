package converters;


import domain.Article;
import domain.FollowUp;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
public class FollowUpToStringConverter implements Converter<FollowUp, String>{

	@Override
	public String convert(FollowUp followUp) {

		String result;
		if(followUp == null){
			result = null;
		}else{
			result = String.valueOf(followUp.getId());
		}
		return result;
	}

}