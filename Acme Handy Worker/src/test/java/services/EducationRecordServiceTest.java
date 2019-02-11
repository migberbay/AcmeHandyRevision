package services;

import java.util.Collection;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Curricula;
import domain.EducationRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/datasource.xml",
										"classpath:spring/config/packages.xml"})
@Transactional
public class EducationRecordServiceTest extends AbstractTest {
	
	// Service under test ---------------------------------------------------------

	@Autowired
	private CurriculaService curriculaService;
	
	@Autowired
	private EducationRecordService educationRecordService;
	
	
	// Tests ----------------------------------------------------------------------
	
	// CREATE ---------------------------------------------------------------------
	
	@Test
	public void testHandyWorkerCreate(){
		EducationRecord er;
		super.authenticate("handyWorker1");
		er = educationRecordService.create();

		Assert.isNull(er.getAttachment());
		Assert.isNull(er.getComments());
		Assert.isNull(er.getDiplomaTitle());
		Assert.isNull(er.getEndDate());
		Assert.isNull(er.getInstitution());
		Assert.notNull(er.getStartDate());

		super.authenticate(null);
	}
	
	
	
	// SAVE -----------------------------------------------------------------------
	
	@Test 
	public void testHandyWorkerSave(){
		EducationRecord	er, saved;
		
		
		super.authenticate("handyWorker1");						
		er = educationRecordService.create();			
		Date fecha = new Date();
		
			er.setAttachment("http://www.attachment.com");
			er.setComments("comments");
			er.setDiplomaTitle("diplomaTitle");
			er.setEndDate(new Date(fecha.getTime()-100000));
			er.setInstitution("institution");
			er.setStartDate(new Date(fecha.getTime()-1000000));
		
		saved = educationRecordService.save(er);	

		Collection<EducationRecord> educationRecords = educationRecordService.findAll();						
		Assert.isTrue(educationRecords.contains(saved));
		
		boolean curriculaUpdated = false;
		for (Curricula c : curriculaService.findAll()) {
			if(c.getEducationRecords().contains(saved));
			curriculaUpdated = true;
		}
		Assert.isTrue(curriculaUpdated);
		
		super.authenticate(null);
	}
	

	// UPDATE ---------------------------------------------------------------------
	
	@Test 
	public void testHandyWorkerUpdate(){
		EducationRecord er, recovered;
		
		super.authenticate("handyworker1");		
		er = null;	
		for (Curricula c : curriculaService.findAll()) {
			if(c.getHandyWorker().getUserAccount().getUsername().equals("handyworker1")){
				er = (EducationRecord) c.getEducationRecords().toArray()[0];
				break;
			}
		}
		Assert.notNull(er);		
		
		Date fecha = new Date();
		
		er.setAttachment("http://www.attachment.com");
		er.setComments("comments");
		er.setDiplomaTitle("diplomaTitle1234");
		er.setEndDate(new Date(fecha.getTime()-100000));
		er.setInstitution("institution");
		er.setStartDate(new Date(fecha.getTime()-1000000));
		
		educationRecordService.save(er);
				
		
		recovered = educationRecordService.findOne(er.getId());						
		Assert.isTrue(recovered.getDiplomaTitle().equals("diplomaTitle1234"));
	

		super.authenticate(null);
	}
	
	// DELETE ---------------------------------------------------------------------
	
	@Test 
	public void testHandyWorkerDelete(){
		EducationRecord	er;
		super.authenticate("handyworker1");		
		er = null;	
		for (Curricula c : curriculaService.findAll()) {
			if(c.getHandyWorker().getUserAccount().getUsername().equals("handyworker1")){
				er = (EducationRecord) c.getEducationRecords().toArray()[0];
				break;
			}
		}
		Assert.notNull(er);		

		educationRecordService.delete(er);
		
		Assert.isTrue(!educationRecordService.findAll().contains(er));
	
		super.authenticate(null);
	}
	

}
