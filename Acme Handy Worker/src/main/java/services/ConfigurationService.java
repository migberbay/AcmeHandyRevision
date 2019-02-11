package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ConfigurationRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Configuration;


@Service
@Transactional
public class ConfigurationService {

	//Managed Repository -----
	@Autowired
	private ConfigurationRepository configurationRepository;
	
	//Supporting Services -----
	
	//Simple CRUD methods -----
	public Configuration create(){
		Configuration res = new Configuration();
		return res;
	}
	
	public Collection<Configuration> findAll(){
		return configurationRepository.findAll();
	}
	
	public Configuration findOne(int Id){
		return configurationRepository.findOne(Id);
	}

	/*find(): Since only one instance of Configuration must exist,
	we need to retrieve always the same object*/
	public Configuration find(){return configurationRepository.findAll().iterator().next();}
	
	public Configuration save(Configuration a){
		Configuration saved;
		LoginService.hasRole("ADMIN");
		//a configuration must be unique in the database.
		Assert.isTrue(this.findAll().size()==1);
		Assert.isTrue(this.findAll().toArray()[0].equals(a));
		
		saved = configurationRepository.saveAndFlush(a);
		return saved;
	}
	
	public void delete(Configuration a){
		LoginService.hasRole("ADMIN");
		configurationRepository.delete(a);
	}
	
	//Other business methods -----

	
}