package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import security.Authority;
import security.LoginService;
import utilities.AbstractTest;
import domain.Curricula;
import domain.PersonalRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/datasource.xml",
										"classpath:spring/config/packages.xml"})
@Transactional
public class CurriculaServiceTest extends AbstractTest {
	
	// Service under test ---------------------------------------------------------

	@Autowired
	private CurriculaService curriculaService;
	
	@Autowired
	private PersonalRecordService personalRecordService;
	
	
	// Tests ----------------------------------------------------------------------
	
	// CREATE ---------------------------------------------------------------------
	
	@Test
	public void testHandyWorkerCreate(){
		Curricula curricula;
		super.authenticate("handyWorker1");
		curricula = curriculaService.create();
		Authority au = new Authority();
		au.setAuthority("HANDYWORKER");

		Assert.isTrue(curricula.getEducationRecords().isEmpty());
		Assert.isTrue(curricula.getEndorserRecords().isEmpty());
		Assert.isTrue(curricula.getProfessionalRecords().isEmpty());
		Assert.isTrue(curricula.getMiscellaneousRecords().isEmpty());
		Assert.isTrue(curricula.getHandyWorker().getUserAccount().getAuthorities().contains(au) || curricula.getHandyWorker().equals(null));
		Assert.isNull(curricula.getPersonalRecord());
		Assert.notNull(curricula.getTicker());
		
		super.authenticate(null);
	}
	
	
	
	// SAVE -----------------------------------------------------------------------
	
	@Test 
	public void testHandyWorkerSave(){
		Curricula curricula;
		
		super.authenticate("handyWorker1");		
				
		//Aseguramos un estado inicial sin curriculas.
		
		
		for (Curricula c : curriculaService.findAll()) {
			if(c.getHandyWorker().getUserAccount().equals(LoginService.getPrincipal())){
				curriculaService.delete(c);
			}
		}	
		
		curricula = curriculaService.create();
		PersonalRecord p = personalRecordService.create();
		
		p.setEmail("email@dominio.com");
		p.setFullName("pepito grillo");
		p.setLinkedInUrl("http://linkedin.com/user");
		p.setPhone("672190514");
		p.setPhoto("http://photostock.com/photo");
		
		
		personalRecordService.save(p); 

		boolean exists = false;
		for (Curricula c : curriculaService.findAll()) {
			if(c.getHandyWorker().getUserAccount().equals(LoginService.getPrincipal())){
				exists = true;
			}
		}	

		Assert.isTrue(exists);
		super.authenticate(null);
	}
	

	// UPDATE ---------------------------------------------------------------------
	
	@Test 
	public void testHandyWorkerUpdate(){
		Curricula curricula;
		super.authenticate("handyworker1");	
		
		curricula = null;	
		for (Curricula c : curriculaService.findAll()) {
			if(c.getHandyWorker().getUserAccount().getUsername().equals("handyworker1")){
				curricula = c;
				break;
			}
		}
		Assert.notNull(curricula);
		
		PersonalRecord p = curricula.getPersonalRecord();	
		
		p.setEmail("email@dominio.com");
		p.setFullName("pepito grillo");
		p.setLinkedInUrl("http://linkedin.com/user");
		p.setPhone("672190514");
		p.setPhoto("http://photostock.com/photo");
		
		boolean is_modified = false;
		for (Curricula c : curriculaService.findAll()) {
			if(c.getHandyWorker().getUserAccount().equals(LoginService.getPrincipal())){
				if(c.getPersonalRecord().getFullName().equals("pepito grillo"))
				is_modified = true;
			}
		}	

		Assert.isTrue(is_modified);

		super.authenticate(null);
	}
	
	// DELETE ---------------------------------------------------------------------
	
	@Test 
	public void testHandyWorkerDelete(){
		Curricula curricula;
		Collection<Curricula> curriculas;
		super.authenticate("handyworker1");							

		curricula = null;	
		for (Curricula c : curriculaService.findAll()) {
			if(c.getHandyWorker().getUserAccount().getUsername().equals("handyworker1")){
				curricula = c;
				break;
			}
		}
		Assert.notNull(curricula);							  
		
		curriculaService.delete(curricula);							
		curriculas = curriculaService.findAll();						
		Assert.isTrue(!curriculas.contains(curricula));
			
		super.authenticate(null);
	}
	

}
