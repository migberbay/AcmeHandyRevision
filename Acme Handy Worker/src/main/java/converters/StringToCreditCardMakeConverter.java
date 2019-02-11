package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.CreditCardMakeRepository;

import domain.CreditCardMake;



@Component
@Transactional
public class StringToCreditCardMakeConverter implements Converter<String,CreditCardMake> {

	@Autowired
	CreditCardMakeRepository repository;
	
	@Override
	public CreditCardMake convert(String s) {
		CreditCardMake res;
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
