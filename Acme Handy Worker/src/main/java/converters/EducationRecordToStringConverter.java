package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.EducationRecord;


@Component
@Transactional
public class EducationRecordToStringConverter implements Converter<EducationRecord,String> {

	@Override
	public String convert(EducationRecord o) {
		String res;
		
		if(o == null)
			res = null;
		else
			res= String.valueOf(o.getId());
		
		return res;
	}

}
