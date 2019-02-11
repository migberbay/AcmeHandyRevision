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

import domain.ProfessionalRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/datasource.xml",
										"classpath:spring/config/packages.xml"})
@Transactional
public class ProfessionalRecordServiceTest extends AbstractTest {
	
	// Service under test ---------------------------------------------------------

	@Autowired
	private CurriculaService curriculaService;
	
	@Autowired
	private ProfessionalRecordService professionalRecordService;
	
	
	// Tests ----------------------------------------------------------------------
	
	// CREATE ---------------------------------------------------------------------
	
	@Test
	public void testHandyWorkerCreate(){
		ProfessionalRecord er;
		super.authenticate("handyWorker1");
		er = professionalRecordService.create();

		Assert.isNull(er.getAttachment());
		Assert.isNull(er.getComments());
		Assert.isNull(er.getCompanyName());
		Assert.isNull(er.getEndDate());
		Assert.isNull(er.getRole());
		Assert.notNull(er.getStartDate());

		super.authenticate(null);
	}
	
	
	
	// SAVE -----------------------------------------------------------------------
	
	@Test 
	public void testHandyWorkerSave(){
		ProfessionalRecord	er, saved;
		
		
		super.authenticate("handyWorker1");						
		er = professionalRecordService.create();			
		Date fecha = new Date();
		
			er.setAttachment("http://www.attachment.com");
			er.setComments("comments");
			er.setCompanyName("companyName");
			er.setRole("CEO");
			er.setEndDate(new Date(fecha.getTime()-100000));
			er.setStartDate(new Date(fecha.getTime()-1000000));
		
		saved = professionalRecordService.save(er);	

		Collection<ProfessionalRecord> professionalRecords = professionalRecordService.findAll();						
		Assert.isTrue(professionalRecords.contains(saved));
		
		boolean curriculaUpdated = false;
		for (Curricula c : curriculaService.findAll()) {
			if(c.getProfessionalRecords().contains(saved));
			curriculaUpdated = true;
		}
		Assert.isTrue(curriculaUpdated);
		
		super.authenticate(null);
	}
	

	// UPDATE ---------------------------------------------------------------------
	
	@Test 
	public void testHandyWorkerUpdate(){
		ProfessionalRecord er, recovered;
		
		super.authenticate("handyworker1");		
		er = null;	
		for (Curricula c : curriculaService.findAll()) {
			if(c.getHandyWorker().getUserAccount().getUsername().equals("handyworker1")){
				er = (ProfessionalRecord) c.getProfessionalRecords().toArray()[0];
				break;
			}
		}
		Assert.notNull(er);			
		
		Date fecha = new Date();
		
		er.setAttachment("http://www.attachment.com");
		er.setComments("comments");
		er.setCompanyName("companyName");
		er.setRole("CEO");
		er.setEndDate(new Date(fecha.getTime()-100000));
		er.setStartDate(new Date(fecha.getTime()-1000000));
		
		professionalRecordService.save(er);
				
		
		recovered = professionalRecordService.findOne(er.getId());						
		Assert.isTrue(recovered.getRole().equals("CEO"));
	

		super.authenticate(null);
	}
	
	// DELETE ---------------------------------------------------------------------
	
	@Test 
	public void testHandyWorkerDelete(){
		ProfessionalRecord	er;
		super.authenticate("handyworker1");		
		er = null;	
		for (Curricula c : curriculaService.findAll()) {
			if(c.getHandyWorker().getUserAccount().getUsername().equals("handyworker1")){
				er = (ProfessionalRecord) c.getProfessionalRecords().toArray()[0];
				break;
			}
		}
		Assert.notNull(er);			
		professionalRecordService.delete(er);
		
		Assert.isTrue(!professionalRecordService.findAll().contains(er));
	
		super.authenticate(null);
	}
	

}
