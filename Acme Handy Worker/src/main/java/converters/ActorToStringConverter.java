package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Actor;


@Component
@Transactional
public class ActorToStringConverter implements Converter<Actor,String> {

	@Override
	public String convert(Actor actor) {
		String res;
		
		if(actor == null)
			res = null;
		else
			res= String.valueOf(actor.getId());
		
		return res;
	}

}
