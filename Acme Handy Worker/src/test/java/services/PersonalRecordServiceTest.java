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

import domain.PersonalRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/datasource.xml",
										"classpath:spring/config/packages.xml"})
@Transactional
public class PersonalRecordServiceTest extends AbstractTest {
	
	// Service under test ---------------------------------------------------------

	@Autowired
	private CurriculaService curriculaService;
	
	@Autowired
	private PersonalRecordService personalRecordService;
	
	
	// Tests ----------------------------------------------------------------------
	
	// CREATE ---------------------------------------------------------------------
	
	@Test
	public void testHandyWorkerCreate(){
		PersonalRecord pR;
		super.authenticate("handyWorker1");
		pR = personalRecordService.create();

		Assert.isNull(pR.getEmail());
		Assert.isNull(pR.getFullName());
		Assert.isNull(pR.getLinkedInUrl());
		Assert.isNull(pR.getPhone());
		Assert.isNull(pR.getPhoto());
		
		super.authenticate(null);
	}
	
	
	
	// SAVE -----------------------------------------------------------------------
	
	@Test 
	public void testHandyWorkerSave(){
		PersonalRecord	pr, saved;
		super.authenticate("handyWorker4");						
		pr = personalRecordService.create();			
		
		pr.setEmail("email@dominio.com");
		pr.setFullName("pepito grillo");
		pr.setLinkedInUrl("http://linkedin.com/user");
		pr.setPhone("672190514");
		pr.setPhoto("http://photostock.com/photo");
		saved = personalRecordService.save(pr); 		
		Collection<PersonalRecord> personalRecords = personalRecordService.findAll();						
		Assert.isTrue(personalRecords.contains(saved));
		boolean curriculaUpdated = false;
		for (Curricula c : curriculaService.findAll()) {
			if(c.getPersonalRecord().equals(saved));
			curriculaUpdated = true;
		}
		Assert.isTrue(curriculaUpdated);
		
		super.authenticate(null);
	}
	

	// UPDATE ---------------------------------------------------------------------
	
	@Test 
	public void testHandyWorkerUpdate(){
		PersonalRecord pr, saved;
		Collection<PersonalRecord> personalRecords;
		super.authenticate("handyworker1");		
		pr = null;	
		for (Curricula c : curriculaService.findAll()) {
			if(c.getHandyWorker().getUserAccount().getUsername().equals("handyworker1")){
				pr = (PersonalRecord) c.getPersonalRecord();
				break;
			}
		}
		Assert.notNull(pr);	

		pr.setFullName("this name now belongs to me.");
		
		saved = personalRecordService.save(pr);		
		
		personalRecords = personalRecordService.findAll();						
		Assert.isTrue(personalRecords.contains(saved));
		
		Collection<Curricula> curriculas = curriculaService.findAll();						
		for (Curricula c : curriculas) {
			if(c.getPersonalRecord().equals(saved)){
				Assert.isTrue(c.getPersonalRecord().getFullName().equals("this name now belongs to me."));
			}
		}

		super.authenticate(null);
	}
	
	// DELETE ---------------------------------------------------------------------
	
	@Test 
	public void testHandyWorkerDelete(){
		PersonalRecord	pr, saved;
		super.authenticate("handyworker4");		
		
		pr = personalRecordService.create();			
		
		pr.setEmail("email@dominio.com");
		pr.setFullName("pepito grillo");
		pr.setLinkedInUrl("http://linkedin.com/user");
		pr.setPhone("672190514");
		pr.setPhoto("http://photostock.com/photo");
		
		saved = personalRecordService.save(pr); 		
		Assert.isTrue(personalRecordService.findAll().contains(saved));
		personalRecordService.delete(saved);
		

		Assert.isTrue(!personalRecordService.findAll().contains(saved));
	
		super.authenticate(null);
	}
	

}
