package converters;


import domain.Audit;
import domain.NewsPaper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import repositories.AuditRepository;
import repositories.NewsPaperRepository;

import javax.transaction.Transactional;

@Component
@Transactional
public class StringToAuditConverter implements Converter<String, Audit>{

@Autowired
AuditRepository auditRepository;

	@Override
	public Audit convert(String text) {
		Audit result;
		int id;
		
		try{
			if(StringUtils.isEmpty(text)){
				result = null;
			}else{
				id = Integer.valueOf(text);
				result = auditRepository.findOne(id);
			}
		}catch(Throwable oops){
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}