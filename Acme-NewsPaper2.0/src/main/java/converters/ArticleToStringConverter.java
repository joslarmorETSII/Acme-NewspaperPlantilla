package converters;


import domain.Actor;
import domain.Article;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
public class ArticleToStringConverter implements Converter<Article, String>{

	@Override
	public String convert(Article article) {
		
		String result;
		if(article == null){
			result = null;
		}else{
			result = String.valueOf(article.getId());
		}
		return result;
	}

}