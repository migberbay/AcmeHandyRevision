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
import domain.Category;
import domain.FixUpTask;
import domain.Warranty;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/datasource.xml",
										"classpath:spring/config/packages.xml"})
@Transactional
public class FixUpTaskServiceTest extends AbstractTest {
	
	// Service under test ---------------------------------------------------------	
	@Autowired
	private FixUpTaskService fixUpTaskService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private WarrantyService warrantyService;
		
	// Tests ----------------------------------------------------------------------
	
	// CREATE ---------------------------------------------------------------------
	
	@Test
	public void testCreateFixUpTask(){
		FixUpTask fixUpTask;
		super.authenticate("customer1");
		fixUpTask = fixUpTaskService.create();
		Assert.isTrue(fixUpTask.getApplications().isEmpty());
		Assert.isNull(fixUpTask.getCategory());
		Assert.isNull(fixUpTask.getDescription());
		Assert.isNull(fixUpTask.getAddress());
		Assert.isTrue(fixUpTask.getComplaints().isEmpty());
		Assert.isNull(fixUpTask.getEndMoment());
		Assert.isNull(fixUpTask.getStartMoment());
		Assert.isNull(fixUpTask.getMaxPrice());
		Assert.isNull(fixUpTask.getWarranty());
		super.authenticate(null);
	}
	
	
	// SAVE -----------------------------------------------------------------------
	
	@Test 
	public void testSaveFixUpTask(){
		FixUpTask fixUpTask, saved;
		Collection<FixUpTask> fixUpTasks;
		Category category;

		super.authenticate("customer1");						// Nos autenticamos como Referee
		fixUpTask = fixUpTaskService.create();					// Creamos el reporte
		
		category = (Category) categoryService.findAll().toArray()[0];
		
		fixUpTask.setDescription("Description fixuptask test");
		fixUpTask.setAddress("Address test");
		fixUpTask.setCategory(category);
		fixUpTask.setMaxPrice(600d);
		fixUpTask.setStartMoment(Date.valueOf("2020-05-12"));
		fixUpTask.setEndMoment(Date.valueOf("2021-01-13"));
		
		Warranty warranty, Wsaved;
		warranty= warrantyService.create();
		warranty.setTitle("Warranty title test");
		warranty.setTerms("Terms warranty test");
		warranty.setLaws("Law test warranty");
		warranty.setIsDraft(true);
		Wsaved = warrantyService.save(warranty);
		
		fixUpTask.setWarranty(Wsaved);
		saved = fixUpTaskService.save(fixUpTask);
		
		fixUpTasks = fixUpTaskService.findAll();

		Assert.isTrue(fixUpTasks.contains(saved));
		super.authenticate(null);
	}
	

	// UPDATE ---------------------------------------------------------------------
	
	@Test 
	public void testUpdateFixUpTasks(){
		FixUpTask fixUpTask,saved;
		super.authenticate("customer1");
		fixUpTask = (FixUpTask) fixUpTaskService.findAll().toArray()[0];
		fixUpTask.setDescription("Oh no");
		fixUpTask.setEndMoment(Date.valueOf("2019-02-03"));

		saved = fixUpTaskService.save(fixUpTask);

		Assert.isTrue(saved.getEndMoment().equals(Date.valueOf("2019-02-03")));
		Assert.isTrue(saved.getDescription().equals("Oh no"));
		super.authenticate(null);
	}
	
	// DELETE ---------------------------------------------------------------------

	@Test 
	public void testDeleteFixUpTask(){
		FixUpTask fixUpTask;
		Collection<FixUpTask> fixUpTasks;
		super.authenticate("customer1");				

		fixUpTask = (FixUpTask) fixUpTaskService.findAll().toArray()[0];
		
		fixUpTaskService.delete(fixUpTask);	
		fixUpTasks = fixUpTaskService.findAll();						
		Assert.isTrue(!fixUpTasks.contains(fixUpTask));	
		
		super.authenticate(null);
	}
	
	// OTHERS ---------------------------------------------------------------------
/*	
	@Test
	public void FixUpTasksHandyWorker(){
		super.authenticate("handyworker1");
		Collection<FixUpTask> fixUpTasks;
		Collection<FixUpTask> tasksM = new ArrayList<FixUpTask>();
		Collection<Application> apps = applicationService.findAll();
		HandyWorker hw;
//		hw= handyWorkerService.findByUserAccountId(LoginService.getPrincipal().getId());
		fixUpTasks = fixUpTaskService.getFixUpTasksHandyWorker(hw.getId());
		for(Application a: apps){
			if(a.getHandyWorker().equals(hw))
				tasksM.add(a.getFixUpTask());
		}
		
		Assert.isTrue(fixUpTasks.equals(tasksM));
		
	}*/
	
}