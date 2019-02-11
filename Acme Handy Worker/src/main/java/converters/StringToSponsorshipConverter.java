package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.SponsorshipRepository;

import domain.Sponsorship;



@Component
@Transactional
public class StringToSponsorshipConverter implements Converter<String,Sponsorship> {

	@Autowired
	SponsorshipRepository repository;
	
	@Override
	public Sponsorship convert(String s) {
		Sponsorship res;
		int id;
		
		try {
			if(StringUtils.isEmpty(s))
				res=null;
			else{
				id = Integer.valueOf(s);
				res = repository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return res;
	}

}
