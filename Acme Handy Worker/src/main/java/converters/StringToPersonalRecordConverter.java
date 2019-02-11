package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.PersonalRecordRepository;

import domain.PersonalRecord;



@Component
@Transactional
public class StringToPersonalRecordConverter implements Converter<String,PersonalRecord> {

	@Autowired
	PersonalRecordRepository repository;
	
	@Override
	public PersonalRecord convert(String s) {
		PersonalRecord res;
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
