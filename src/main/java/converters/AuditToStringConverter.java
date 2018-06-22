package converters;


import domain.Audit;
import domain.NewsPaper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
public class AuditToStringConverter implements Converter<Audit, String>{

	@Override
	public String convert(Audit audit) {
		
		String result;
		if(audit == null){
			result = null;
		}else{
			result = String.valueOf(audit.getId());
		}
		return result;
	}

}