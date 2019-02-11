package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.HandyWorkerEndorsement;


@Component
@Transactional
public class HandyWorkerEndorsementToStringConverter implements Converter<HandyWorkerEndorsement,String> {

	@Override
	public String convert(HandyWorkerEndorsement o) {
		String res;
		
		if(o == null)
			res = null;
		else
			res= String.valueOf(o.getId());
		
		return res;
	}

}
