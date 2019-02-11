package converters;


import java.net.URLDecoder;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import domain.CreditCard;



@Component
@Transactional
public class StringToCreditCardConverter implements Converter<String,CreditCard> {
	
	@Override
	public CreditCard convert(String o) {
		CreditCard res = null;
		String parts[];
		
		if(o==null)
			res= null;
		else{
			try {
				parts = o.split("|");
				res = new CreditCard();
				res.setHolder(URLDecoder.decode(parts[0],"UTF-8"));
				res.setBrand(URLDecoder.decode(parts[1],"UTF-8"));
				res.setNumber(URLDecoder.decode(parts[2],"UTF-8"));
				res.setCVV(Integer.valueOf(URLDecoder.decode(parts[3],"UTF-8")));
			} catch (final Throwable oops) {
				
			}
		}
		return res;
	}
}
