package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.ProfessionalRecord;


@Component
@Transactional
public class ProfessionalRecordToStringConverter implements Converter<ProfessionalRecord,String> {

	@Override
	public String convert(ProfessionalRecord o) {
		String res;
		
		if(o == null)
			res = null;
		else
			res= String.valueOf(o.getId());
		
		return res;
	}

}
