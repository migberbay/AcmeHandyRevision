package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.CreditCardMake;


@Component
@Transactional
public class CreditCardMakeToStringConverter implements Converter<CreditCardMake,String> {

	@Override
	public String convert(CreditCardMake o) {
		String res;
		
		if(o == null)
			res = null;
		else
			res= String.valueOf(o.getId());
		
		return res;
	}

}
