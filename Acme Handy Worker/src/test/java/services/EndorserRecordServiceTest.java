package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Curricula;
import domain.EndorserRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/datasource.xml",
										"classpath:spring/config/packages.xml"})
@Transactional
public class EndorserRecordServiceTest extends AbstractTest {
	
	// Service under test ---------------------------------------------------------

	@Autowired
	private CurriculaService curriculaService;
	
	@Autowired
	private EndorserRecordService endorserRecordService;
	
	
	// Tests ----------------------------------------------------------------------
	
	// CREATE ---------------------------------------------------------------------
	
	@Test
	public void testHandyWorkerCreate(){
		EndorserRecord er;
		super.authenticate("handyWorker1");
		er = endorserRecordService.create();

		Assert.isNull(er.getComments());
		Assert.isNull(er.getEmail());
		Assert.isNull(er.getEndorserName());
		Assert.isNull(er.getLinkedInProfile());
		Assert.isNull(er.getPhone());

		super.authenticate(null);
	}
	
	
	
	// SAVE -----------------------------------------------------------------------
	
	@Test 
	public void testHandyWorkerSave(){
		EndorserRecord	er, saved;
		
		super.authenticate("handyWorker1");						
		er = endorserRecordService.create();			
		
			er.setComments("comments");
			er.setEmail("email@domain.com");
			er.setEndorserName("endorserName");
			er.setLinkedInProfile("http://www.linkedin.com/user");
			er.setPhone("672190514");	
		
		saved = endorserRecordService.save(er);	

		Collection<EndorserRecord> endorserRecords = endorserRecordService.findAll();						
		Assert.isTrue(endorserRecords.contains(saved));
		
		boolean curriculaUpdated = false;
		for (Curricula c : curriculaService.findAll()) {
			if(c.getEndorserRecords().contains(saved));
			curriculaUpdated = true;
		}
		Assert.isTrue(curriculaUpdated);
		
		super.authenticate(null);
	}
	

	// UPDATE ---------------------------------------------------------------------
	
	@Test 
	public void testHandyWorkerUpdate(){
		EndorserRecord er, recovered;
		
		super.authenticate("handyworker1");		
				
		er = null;	
		for (Curricula c : curriculaService.findAll()) {
			if(c.getHandyWorker().getUserAccount().getUsername().equals("handyworker1")){
				er = (EndorserRecord) c.getEndorserRecords().toArray()[0];
				break;
			}
		}
		Assert.notNull(er);		
		
		er.setComments("comments");
		er.setEmail("email@domain.com");
		er.setEndorserName("endorserName");
		er.setLinkedInProfile("http://www.linkedin.com/user");
		er.setPhone("672190514");	
	
		endorserRecordService.save(er);	
				
		recovered = endorserRecordService.findOne(er.getId());						
		Assert.isTrue(recovered.getPhone().equals("672190514"));
	

		super.authenticate(null);
	}
	
	// DELETE ---------------------------------------------------------------------
	
	@Test 
	public void testHandyWorkerDelete(){
		EndorserRecord	er;
		super.authenticate("handyworker1");		
		er = null;	
		for (Curricula c : curriculaService.findAll()) {
			if(c.getHandyWorker().getUserAccount().getUsername().equals("handyworker1")){
				er = (EndorserRecord) c.getEndorserRecords().toArray()[0];
				break;
			}
		}
		Assert.notNull(er);			
		endorserRecordService.delete(er);
		
		Assert.isTrue(!endorserRecordService.findAll().contains(er));
	
		super.authenticate(null);
	}
	

}
