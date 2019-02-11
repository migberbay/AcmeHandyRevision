package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.WorkPlanPhase;


@Component
@Transactional
public class WorkPlanPhaseToStringConverter implements Converter<WorkPlanPhase,String> {

	@Override
	public String convert(WorkPlanPhase o) {
		String res;
		
		if(o == null)
			res = null;
		else
			res= String.valueOf(o.getId());
		
		return res;
	}

}
