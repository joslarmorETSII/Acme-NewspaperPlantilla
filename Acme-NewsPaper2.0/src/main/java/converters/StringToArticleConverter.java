package converters;


import domain.Actor;
import domain.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import repositories.ActorRepository;
import repositories.ArticleRepository;

import javax.transaction.Transactional;

@Component
@Transactional
public class StringToArticleConverter implements Converter<String, Article>{

@Autowired
ArticleRepository articleRepository;

	@Override
	public Article convert(String text) {
		Article result;
		int id;
		
		try{
			if(StringUtils.isEmpty(text)){
				result = null;
			}else{
				id = Integer.valueOf(text);
				result = articleRepository.findOne(id);
			}
		}catch(Throwable oops){
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}