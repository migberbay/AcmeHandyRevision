package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.CustomerEndorsement;


@Component
@Transactional
public class CustomerEndorsementToStringConverter implements Converter<CustomerEndorsement,String> {

	@Override
	public String convert(CustomerEndorsement o) {
		String res;
		
		if(o == null)
			res = null;
		else
			res= String.valueOf(o.getId());
		
		return res;
	}

}
