package services;

import java.sql.Date;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.FixUpTask;
import domain.HandyWorker;
import domain.WorkPlanPhase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/datasource.xml",
										"classpath:spring/config/packages.xml"})
@Transactional
public class WorkPlanPhaseServiceTest extends AbstractTest {
	
	// Service under test ---------------------------------------------------------

	@Autowired
	private WorkPlanPhaseService workPlanPhaseService;
	
	@Autowired
	private HandyWorkerService handyWorkerService;
	
	@Autowired
	private FixUpTaskService fixUpTaskService;
	
	// Tests ----------------------------------------------------------------------
	
	// CREATE ---------------------------------------------------------------------
	
	@Test
	public void testCreateWorkPlanPhases(){
		WorkPlanPhase workPlanPhase;
		super.authenticate("handyworker1");
		workPlanPhase = workPlanPhaseService.create();	
		Assert.isNull(workPlanPhase.getDescription());
		Assert.isNull(workPlanPhase.getEndMoment());
		Assert.isNull(workPlanPhase.getFixUpTask());
		Assert.notNull(workPlanPhase.getHandyWorker());
		Assert.isNull(workPlanPhase.getStartMoment());
		Assert.isNull(workPlanPhase.getTitle());
						
		super.authenticate(null);
	}
	
	
	// SAVE -----------------------------------------------------------------------
	
	@Test 
	public void testSaveWorkPlanPhase(){
		WorkPlanPhase workPlanPhase,saved;
		FixUpTask fixUpTask;
		Collection<WorkPlanPhase> workPlanPhases;
		super.authenticate("handyworker1");
		HandyWorker hw = handyWorkerService.findByPrincipal();
		workPlanPhase = workPlanPhaseService.create();
		fixUpTask = (FixUpTask) fixUpTaskService.findAll().toArray()[0];
		
		workPlanPhase.setDescription("Description test phase");
		workPlanPhase.setStartMoment(Date.valueOf("2019-02-12"));
		workPlanPhase.setEndMoment(Date.valueOf("2019-03-11"));
		workPlanPhase.setTitle("Title phase test");
		
		workPlanPhase.setFixUpTask(fixUpTask);
		workPlanPhase.setHandyWorker(hw);

		saved = workPlanPhaseService.save(workPlanPhase);
		
		workPlanPhases = workPlanPhaseService.findAll();
		Assert.isTrue(workPlanPhases.contains(saved));
		
		super.authenticate(null);
	}
	

	// UPDATE ---------------------------------------------------------------------
	
	@Test 
	public void testUpdateWorkPlanPhases(){
		WorkPlanPhase workPlanPhase,saved;
		Collection<WorkPlanPhase> workPlanPhases;
		super.authenticate("handyworker1");	
		workPlanPhase = (WorkPlanPhase) workPlanPhaseService.findAll().toArray()[0];

		workPlanPhase.setDescription("Test");
		
		saved = workPlanPhaseService.save(workPlanPhase);
		
		workPlanPhases = workPlanPhaseService.findAll();
		Assert.isTrue(workPlanPhases.contains(saved));

		super.authenticate(null);
	}
	
	// DELETE ---------------------------------------------------------------------

	@Test 
	public void testDeleteWorkPlanPhase(){
		WorkPlanPhase workPlanPhase;
		Collection<WorkPlanPhase> workPlanPhases;
		super.authenticate("handyworker1");

		workPlanPhase = (WorkPlanPhase) workPlanPhaseService.findAll().toArray()[0];
		
		workPlanPhaseService.delete(workPlanPhase);
		
		workPlanPhases = workPlanPhaseService.findAll();	
		Assert.isTrue(!workPlanPhases.contains(workPlanPhase));
		
		super.authenticate(null);
	}


}