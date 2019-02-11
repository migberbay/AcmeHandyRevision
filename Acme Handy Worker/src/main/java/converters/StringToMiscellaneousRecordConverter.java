package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.MiscellaneousRecordRepository;

import domain.MiscellaneousRecord;



@Component
@Transactional
public class StringToMiscellaneousRecordConverter implements Converter<String,MiscellaneousRecord> {

	@Autowired
	MiscellaneousRecordRepository repository;
	
	@Override
	public MiscellaneousRecord convert(String s) {
		MiscellaneousRecord res;
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
