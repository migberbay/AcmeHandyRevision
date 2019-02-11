package converters;

import java.net.URLEncoder;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.CreditCard;


@Component
@Transactional
public class CreditCardToStringConverter implements Converter<CreditCard,String> {

	@Override
	public String convert(CreditCard o) {
		String res;
		StringBuilder builder;
		
		if(o == null)
			res = null;
		else{
			try {
				builder = new StringBuilder();
				builder.append(URLEncoder.encode(o.getHolder(),"UTF-8"));
				builder.append("|");
				
				builder.append(URLEncoder.encode(o.getBrand(),"UTF-8"));
				builder.append("|");

				builder.append(URLEncoder.encode(o.getNumber(),"UTF-8"));
				builder.append("|");

				builder.append(URLEncoder.encode(o.getCVV().toString(),"UTF-8"));
				builder.append("|");
				
				res= builder.toString();
				
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			}
		}
		
		return res;
	}

}
