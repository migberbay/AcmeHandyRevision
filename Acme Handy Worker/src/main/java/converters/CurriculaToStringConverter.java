package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Curricula;


@Component
@Transactional
public class CurriculaToStringConverter implements Converter<Curricula,String> {

	@Override
	public String convert(Curricula o) {
		String res;
		
		if(o == null)
			res = null;
		else
			res= String.valueOf(o.getId());
		
		return res;
	}

}
