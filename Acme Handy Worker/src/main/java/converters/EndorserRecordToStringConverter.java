package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.EndorserRecord;


@Component
@Transactional
public class EndorserRecordToStringConverter implements Converter<EndorserRecord,String> {

	@Override
	public String convert(EndorserRecord o) {
		String res;
		
		if(o == null)
			res = null;
		else
			res= String.valueOf(o.getId());
		
		return res;
	}

}
