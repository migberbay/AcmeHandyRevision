package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.EducationRecordRepository;

import domain.EducationRecord;



@Component
@Transactional
public class StringToEducationRecordConverter implements Converter<String,EducationRecord> {

	@Autowired
	EducationRecordRepository repository;
	
	@Override
	public EducationRecord convert(String s) {
		EducationRecord res;
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
