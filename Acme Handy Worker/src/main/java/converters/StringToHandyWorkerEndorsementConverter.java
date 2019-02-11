package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.HandyWorkerEndorsementRepository;

import domain.HandyWorkerEndorsement;



@Component
@Transactional
public class StringToHandyWorkerEndorsementConverter implements Converter<String,HandyWorkerEndorsement> {

	@Autowired
	HandyWorkerEndorsementRepository repository;
	
	@Override
	public HandyWorkerEndorsement convert(String s) {
		HandyWorkerEndorsement res;
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
