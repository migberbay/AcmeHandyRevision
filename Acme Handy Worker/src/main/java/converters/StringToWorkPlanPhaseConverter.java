package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.WorkPlanPhaseRepository;

import domain.WorkPlanPhase;



@Component
@Transactional
public class StringToWorkPlanPhaseConverter implements Converter<String,WorkPlanPhase> {

	@Autowired
	WorkPlanPhaseRepository repository;
	
	@Override
	public WorkPlanPhase convert(String s) {
		WorkPlanPhase res;
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
