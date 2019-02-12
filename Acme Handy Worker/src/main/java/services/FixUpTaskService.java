package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FixUpTaskRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Application;
import domain.Complaint;
import domain.Customer;
import domain.FixUpTask;
import domain.Warranty;
import domain.WorkPlanPhase;


@Service
@Transactional
public class FixUpTaskService {

	//Managed Repository -----
	@Autowired
	private FixUpTaskRepository fixUpTaskRepository;
	
	//Supporting Services -----
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private ComplaintService complaintService;
	
	@Autowired
	private ApplicationService applicationService;
	
	@Autowired
	private WorkPlanPhaseService workPlanPhaseService;
	
	@Autowired
	private ConfigurationService configurationService;

	//Simple CRUD methods -----
	public FixUpTask create(){
		FixUpTask res = new FixUpTask();
		res.setApplications(new ArrayList<Application>());
		res.setComplaints(new ArrayList<Complaint>());
		res.setCustomer(customerService.findByUserAccountId(LoginService.getPrincipal().getId()));
		Date current = new Date(System.currentTimeMillis() - 1000);
		res.setMoment(current);
		res.setTicker(generateTicker());

		return res;
	}
	
	public Collection<FixUpTask> findAll(){
		return fixUpTaskRepository.findAll();
	}
	
	public FixUpTask findOne(int Id){
		return fixUpTaskRepository.findOne(Id);
	}
	
	public FixUpTask save(FixUpTask fx){
		FixUpTask saved;
		Collection<FixUpTask> fixUpTasks;
		Assert.isTrue( fx.getId()==0 || fx.getId() != 0  &&
				fx.getCustomer().getUserAccount().equals(LoginService.getPrincipal()));
		if(fx.getId()==0){
			Assert.isTrue(fx.getStartMoment().after(new Date()));
			Assert.isTrue(fx.getEndMoment().after(new Date()));
			System.out.println(fx.getMaxPrice() + (fx.getMaxPrice()*(configurationService.find().getVatPercentage()/100)));
			fx.setMaxPrice(fx.getMaxPrice() + (fx.getMaxPrice()*(configurationService.find().getVatPercentage()/100)));
		}
		
		saved = fixUpTaskRepository.save(fx);
		fixUpTasks = fixUpTaskRepository.findAll();
		Assert.isTrue(fixUpTasks.contains(saved));
		return saved;
	}
	
	public void delete(FixUpTask fx){
		Assert.isTrue(fx.getCustomer().getUserAccount().equals(LoginService.getPrincipal()));	
		Collection<Complaint> complaints = fx.getComplaints();
		Collection<Application> applications = fx.getApplications();
		Customer c = fx.getCustomer();
		Collection<WorkPlanPhase> wp = workPlanPhaseService.findByFixUpTaskId(fx.getId());

		for(Complaint co: complaints){
			co.setFixUpTask(null);
			complaintService.delete(co);
		}
		for(Application a: applications) applicationService.deleteAut(a);
		c.getFixUpTasks().remove(fx);
		for(WorkPlanPhase w: wp) workPlanPhaseService.delete(w);
		
		customerService.save(c);
		
		fixUpTaskRepository.delete(fx);
	}
	
	//Other business methods -----
	
	public Double getRatioTasksWComplaints(){
		Double res;
		res = fixUpTaskRepository.getRatioTasksWComplaints();
		return res;
	}
	
	//C-RF 11.1
	public Collection<FixUpTask> getFixUpTasksHandyWorker(int handyWorkerId){
		Collection<FixUpTask> res;
		res = fixUpTaskRepository.getFixUpTasksHandyWorker(handyWorkerId);
		return res;
	}
	
	public Collection<FixUpTask> getFixUpTasksCustomer(){
		Collection<FixUpTask> res;
		Customer c = customerService.findByUserAccountId(LoginService.getPrincipal().getId());
		res = fixUpTaskRepository.getFixUpTasksCustomer(c.getId());
		return res;
	}
	
	public Collection<FixUpTask> getTasksAccepted(){
		Collection<FixUpTask> res;
		res = fixUpTaskRepository.getTasksAccepted();
		return res;
	}
	
	public Double getAvgTasksPerCustomer(){
		Double res;
		res = fixUpTaskRepository.getAvgTasksPerCustomer();
		return res;
	}
	
	public Integer getMinTasksPerCustomer(){
		Integer res;
		res = fixUpTaskRepository.getMinTasksPerCustomer();
		return res;
	}
	
	public Integer getMaxTasksPerCustomer(){
		Integer res;
		res = fixUpTaskRepository.getMaxTasksPerCustomer();
		return res;
	}
	
	public Double getStdevTasksPerCustomer(){
		Double res;
		res = fixUpTaskRepository.getStdevTasksPerCustomer();
		return res;
	}
	//
	
	
	
	public Double getAvgMaxPriceTasks(){
		Double res;
		res = fixUpTaskRepository.getAvgMaxPriceTasks();
		return res;
	}
	
	public Integer getMaximumMaxPriceTasks(){
		Integer res;
		res = fixUpTaskRepository.getMaximumMaxPriceTasks();
		return res;
	}
	
	public Integer getMinimumMaxPriceTasks(){
		Integer res;
		res = fixUpTaskRepository.getMinimumMaxPriceTasks();
		return res;
	}
	
	public Double getStdevMaxPriceTasks(){
		Double res;
		res = fixUpTaskRepository.getStdevMaxPriceTasks();
		return res;
	}
	
	private String generateTicker(){
		Date date = new Date(); // your date
		Calendar n = Calendar.getInstance();
		n.setTime(date);
		String t = "";
		String s = Integer.toString((n.get(Calendar.DAY_OF_MONTH)));
		String m = Integer.toString(n.get(Calendar.MONTH)+1);
		if(s.length()==1) s= "0"+Integer.toString((n.get(Calendar.DAY_OF_MONTH)));
		if(m.length()==1) m = "0"+ Integer.toString(n.get(Calendar.MONTH) +1);
		t = t + Integer.toString(n.get(Calendar.YEAR) - 2000)
				+ m
				+ s
				+ "-"+ randomWordAndNumber();

		return t;
	}
	
	private String randomWordAndNumber(){
		 String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	        StringBuilder salt = new StringBuilder();
	        Random rnd = new Random();
	        while (salt.length() < 6) { // length of the random string.
	            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
	            salt.append(SALTCHARS.charAt(index));
	        }
	        String saltStr = salt.toString();
	        return saltStr;
	}
	
	public FixUpTask saveAdmin(FixUpTask fx){
		UserAccount userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("ADMIN");
		Assert.isTrue(userAccount.getAuthorities().contains(au));
		
		return fixUpTaskRepository.save(fx);
	}
	
	
}