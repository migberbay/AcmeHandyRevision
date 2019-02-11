package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.HandyWorker;


@Component
@Transactional
public class HandyWorkerToStringConverter implements Converter<HandyWorker,String> {

	@Override
	public String convert(HandyWorker o) {
		String res;
		
		if(o == null)
			res = null;
		else
			res= String.valueOf(o.getId());
		
		return res;
	}

}
