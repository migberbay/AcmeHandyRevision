package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.WorkPlanPhaseRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.FixUpTask;
import domain.HandyWorker;
import domain.WorkPlanPhase;


@Service
@Transactional
public class WorkPlanPhaseService {

	//Managed Repository -----
	@Autowired
	private WorkPlanPhaseRepository workPlanPhaseRepository;
	
	//Supporting Services -----
	
	@Autowired
	private HandyWorkerService handyWorkerService;
	
	@Autowired
	private FixUpTaskService fixUpTaskService;
	
	//Simple CRUD methods -----
	public WorkPlanPhase create(){
		WorkPlanPhase res;
		res = new WorkPlanPhase();
		HandyWorker hw = handyWorkerService.findByPrincipal();
		res.setHandyWorker(hw);
		return res;
	}
	
	public Collection<WorkPlanPhase> findAll(){
		return workPlanPhaseRepository.findAll();
	}
	
	public WorkPlanPhase findOne(int Id){
		return workPlanPhaseRepository.findOne(Id);
	}
	
	public WorkPlanPhase save(WorkPlanPhase w){
		WorkPlanPhase saved;
		Collection<FixUpTask> tasks;
		Authority a= new Authority();
		a.setAuthority("HANDYWORKER");
		UserAccount userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().contains(a));
		
		tasks = fixUpTaskService.getTasksAccepted();
		Assert.isTrue(tasks.contains(w.getFixUpTask()), "The fix-up task must have at least one accepted application");

		w.setHandyWorker(handyWorkerService.findByPrincipal());

		saved = workPlanPhaseRepository.save(w);
		return saved;
	}
	
	public void delete(WorkPlanPhase wp){
		Authority a= new Authority();
		Authority b = new Authority();
		a.setAuthority("HANDYWORKER");
		b.setAuthority("CUSTOMER");
		UserAccount userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().contains(a) || userAccount.getAuthorities().contains(b));
		
		workPlanPhaseRepository.delete(wp);
	}
	
	//Other business methods -----
	
	public Collection<WorkPlanPhase> findByFixUpTaskId(Integer Id){
		Collection<WorkPlanPhase> res;
		res = workPlanPhaseRepository.findByFixUpTaskId(Id);
		return res;
	}
	
	
}