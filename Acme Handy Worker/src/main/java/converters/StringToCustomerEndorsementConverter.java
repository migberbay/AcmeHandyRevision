package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.CustomerEndorsementRepository;

import domain.CustomerEndorsement;



@Component
@Transactional
public class StringToCustomerEndorsementConverter implements Converter<String,CustomerEndorsement> {

	@Autowired
	CustomerEndorsementRepository repository;
	
	@Override
	public CustomerEndorsement convert(String s) {
		CustomerEndorsement res;
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
