package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.HandyWorkerRepository;

import domain.HandyWorker;



@Component
@Transactional
public class StringToHandyWorkerConverter implements Converter<String,HandyWorker> {

	@Autowired
	HandyWorkerRepository repository;
	
	@Override
	public HandyWorker convert(String s) {
		HandyWorker res;
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
