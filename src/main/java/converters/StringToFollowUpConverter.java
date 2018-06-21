package converters;


import domain.Actor;
import domain.FollowUp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import repositories.ActorRepository;
import repositories.FollowUpRepository;

import javax.transaction.Transactional;

@Component
@Transactional
public class StringToFollowUpConverter implements Converter<String, FollowUp>{
@Autowired
FollowUpRepository followUpRepository;

	@Override
	public FollowUp convert(String text) {
		FollowUp result;
		int id;
		
		try{
			if(StringUtils.isEmpty(text)){
				result = null;
			}else{
				id = Integer.valueOf(text);
				result = followUpRepository.findOne(id);
			}
		}catch(Throwable oops){
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}