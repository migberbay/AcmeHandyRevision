package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.FixUpTask;


@Component
@Transactional
public class FixUpTaskToStringConverter implements Converter<FixUpTask,String> {

	@Override
	public String convert(FixUpTask fixUp) {
		String res;
		
		if(fixUp == null)
			res = null;
		else
			res= String.valueOf(fixUp.getId());
		
		return res;
	}

}
