package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.WelcomeMessageRepository;

import domain.WelcomeMessage;



@Component
@Transactional
public class StringToWelcomeMessageConverter implements Converter<String,WelcomeMessage> {

	@Autowired
	WelcomeMessageRepository repository;
	
	@Override
	public WelcomeMessage convert(String s) {
		WelcomeMessage res;
		int id;
		
		try {
			if(StringUtils.isEmpty(s))
				res=null;
			else{
				id = Integer.valueOf(s);
				res = repository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return res;
	}

}
