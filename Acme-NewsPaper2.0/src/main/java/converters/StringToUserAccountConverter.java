package converters;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


import security.UserAccount;
import security.UserAccountRepository;

@Component
@Transactional
public class StringToUserAccountConverter  implements Converter<String, UserAccount>{

	@Autowired UserAccountRepository userAccountRepository;

	@Override
	public UserAccount convert(String text) {
		UserAccount result;
		int id;
		
		try{
			if(StringUtils.isEmpty(text)){
				result = null;
			}else{
				id = Integer.valueOf(text);
				result = userAccountRepository.findOne(id);
			}
		}catch(Throwable oops){
			throw new IllegalArgumentException(oops);
		}
		return result;
	}
}
