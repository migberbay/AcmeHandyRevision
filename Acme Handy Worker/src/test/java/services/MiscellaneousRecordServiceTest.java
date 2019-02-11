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
import domain.MiscellaneousRecord;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/datasource.xml",
										"classpath:spring/config/packages.xml"})
@Transactional
public class MiscellaneousRecordServiceTest extends AbstractTest {
	
	// Service under test ---------------------------------------------------------

	@Autowired
	private CurriculaService curriculaService;
	
	@Autowired
	private MiscellaneousRecordService miscellaneousRecordService;
	
	
	// Tests ----------------------------------------------------------------------
	
	// CREATE ---------------------------------------------------------------------
	
	@Test
	public void testHandyWorkerCreate(){
		MiscellaneousRecord mr;
		super.authenticate("handyWorker1");
		mr = miscellaneousRecordService.create();

		Assert.isNull(mr.getComments());
		Assert.isNull(mr.getAttachment());
		Assert.isNull(mr.getTitle());
		

		super.authenticate(null);
	}
	
	
	
	// SAVE -----------------------------------------------------------------------
	
	@Test 
	public void testHandyWorkerSave(){
		MiscellaneousRecord	mr, saved;
		
		super.authenticate("handyWorker1");						
		mr = miscellaneousRecordService.create();			
		
			mr.setComments("comments");
			mr.setAttachment("http://www.attachementlink.com/attachement");
			mr.setTitle("title");
		
		saved = miscellaneousRecordService.save(mr);	

		Collection<MiscellaneousRecord> miscellaneousRecords = miscellaneousRecordService.findAll();						
		Assert.isTrue(miscellaneousRecords.contains(saved));
		
		boolean curriculaUpdated = false;
		for (Curricula c : curriculaService.findAll()) {
			if(c.getMiscellaneousRecords().contains(saved));
			curriculaUpdated = true;
		}
		Assert.isTrue(curriculaUpdated);
		
		super.authenticate(null);
	}
	

	// UPDATE ---------------------------------------------------------------------
	
	@Test 
	public void testHandyWorkerUpdate(){
		MiscellaneousRecord mr, recovered;
		
		super.authenticate("handyworker1");		
		mr = null;	
		for (Curricula c : curriculaService.findAll()) {
			if(c.getHandyWorker().getUserAccount().getUsername().equals("handyworker1")){
				mr = (MiscellaneousRecord) c.getMiscellaneousRecords().toArray()[0];
				break;
			}
		}
		Assert.notNull(mr);		
		
		mr.setComments("comments1234");
		mr.setAttachment("http://www.attachementlink.com/attachement");
		mr.setTitle("title");	
	
		miscellaneousRecordService.save(mr);	
				
		recovered = miscellaneousRecordService.findOne(mr.getId());						
		Assert.isTrue(recovered.getComments().equals("comments1234"));
	

		super.authenticate(null);
	}
	
	// DELETE ---------------------------------------------------------------------
	
	@Test 
	public void testHandyWorkerDelete(){
		MiscellaneousRecord	mr;
		super.authenticate("handyworker1");		
		mr = null;	
		for (Curricula c : curriculaService.findAll()) {
			if(c.getHandyWorker().getUserAccount().getUsername().equals("handyworker1")){
				mr = (MiscellaneousRecord) c.getMiscellaneousRecords().toArray()[0];
				break;
			}
		}
		Assert.notNull(mr);	
		miscellaneousRecordService.delete(mr);
		
		Assert.isTrue(!miscellaneousRecordService.findAll().contains(mr));
	
		super.authenticate(null);
	}
	

}
