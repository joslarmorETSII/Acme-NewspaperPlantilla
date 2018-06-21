package converters;


import domain.NewsPaper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import repositories.NewsPaperRepository;

import javax.transaction.Transactional;

@Component
@Transactional
public class StringToNewsPaperConverter implements Converter<String, NewsPaper>{

@Autowired
NewsPaperRepository newsPaperRepository;

	@Override
	public NewsPaper convert(String text) {
		NewsPaper result;
		int id;
		
		try{
			if(StringUtils.isEmpty(text)){
				result = null;
			}else{
				id = Integer.valueOf(text);
				result = newsPaperRepository.findOne(id);
			}
		}catch(Throwable oops){
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}