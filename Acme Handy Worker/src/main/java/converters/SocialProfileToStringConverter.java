package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.SocialProfile;


@Component
@Transactional
public class SocialProfileToStringConverter implements Converter<SocialProfile,String> {

	@Override
	public String convert(SocialProfile o) {
		String res;
		
		if(o == null)
			res = null;
		else
			res= String.valueOf(o.getId());
		
		return res;
	}

}
