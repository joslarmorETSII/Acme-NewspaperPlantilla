package converters;


import domain.NewsPaper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
public class NewsPaperToStringConverter implements Converter<NewsPaper, String>{

	@Override
	public String convert(NewsPaper newsPaper) {
		
		String result;
		if(newsPaper == null){
			result = null;
		}else{
			result = String.valueOf(newsPaper.getId());
		}
		return result;
	}

}