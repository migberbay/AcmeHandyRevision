package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ApplicationRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Application;
import domain.HandyWorker;

@Service
@Transactional
public class ApplicationService {

	@Autowired
	private ApplicationRepository applicationRepository;

	@Autowired
	private MessageService messageService;
	
	@Autowired
	private HandyWorkerService actorService;
	
	@Autowired
	private ConfigurationService configurationService;

	// Simple CRUD methods -----

	public Application create() {

		Application result = new Application();
		
		HandyWorker a = actorService.findByPrincipal();
		Date moment = new Date(System.currentTimeMillis() - 1000);
		
		result.setMoment(moment);
		result.setStatus("PENDING");
		result.setHandyWorker(a);
		
		return result;
	}

	public Application save(Application application) {

		Application result;

		Authority handyWorker = new Authority();
		Authority customer = new Authority();

		handyWorker.setAuthority("HANDYWORKER");
		customer.setAuthority("CUSTOMER");

		UserAccount userAccount = LoginService.getPrincipal();

		Assert.isTrue(userAccount.getAuthorities().contains(handyWorker)
				|| userAccount.getAuthorities().contains(customer));
		
		if(application.getId()==0){
			application.setPrice(application.getPrice() + (application.getPrice()*(configurationService.find().getVatPercentage()/100)));
		}
		
		result = applicationRepository.save(application);
		return result;
	}

	public void delete(Application application) {

		UserAccount userAccount = LoginService.getPrincipal();
		Assert.isTrue(application.getHandyWorker().getUserAccount()
				.equals(userAccount));

		applicationRepository.delete(application);
	}

	public void deleteAut(Application a) {
		applicationRepository.delete(a);
	}

	public Collection<Application> findAll() {
		return applicationRepository.findAll();
	}

	public Application findOne(int Id) {
		return applicationRepository.findOne(Id);
	}

	// Other business methods -----

	public void changeStatus(Application application, String status) {

		// String a = "PENDING";
		// String b = "ACCEPTED";
		// String c = "REJECTED";

		Authority authority = new Authority();
		authority.setAuthority("CUSTOMER");

		UserAccount userAccount = LoginService.getPrincipal();

		Assert.isTrue(userAccount.getAuthorities().contains(authority));

		application.setStatus(status);

		Assert.isTrue((application.getStatus().equals("ACCEPTED") && application
				.getCreditCard() != null)
				|| (application.getStatus().equals("REJECTED") && application
						.getCustomerComment() != ""));

		//TODO:arreglar esto
		messageService.sendSystemMessages(application);

		this.save(application);
	}
	
	public Collection<Application> findApplicationsAccepted(int handyWorkerId, int taskId){
		Collection<Application> res;
		res = applicationRepository.findApplicationsAccepted(handyWorkerId, taskId);
		return res;
	}

	public Collection<Application> applicationByHandyWorker(int handyWorkerId) {
		Collection<Application> res = new ArrayList<Application>();
		res = applicationRepository.applicationByHandyWorker(handyWorkerId);
		return res;
	}
	
	public Collection<Application> applicationByCustomer(Integer Id){
		return applicationRepository.applicationByCustomer(Id);
	}
	
	public Double getAverageApplicationsPerFixUpTask(){
		return applicationRepository.getAverageApplicationsPerFixUpTask();
	}
	
	public Integer getMinimumApplicationsPerFixUpTask(){
		return applicationRepository.getMinimumApplicationsPerFixUpTask();
	}
	
	public Integer getMaximumApplicationsPerFixUpTask(){
		return applicationRepository.getMaximumApplicationsPerFixUpTask();
	}

	public Double getStdevApplicationsPerFixUpTask(){
		return applicationRepository.getStdevApplicationsPerFixUpTask();
	}
	
	public Double getAveragePriceApplication(){
		return applicationRepository.getAveragePriceApplication();
	}
	
	public Integer getMaximumPriceApplications(){
		return applicationRepository.getMaximumPriceApplications();
	}
	
	public Integer getMinimumPriceApplications(){
		return applicationRepository.getMinimumPriceApplications();
	}

	public Double getStdevPriceApplications(){
		return applicationRepository.getStdevPriceApplications();
	}

	public Double getRatioPendingApplications(){
		return applicationRepository.getRatioPendingApplications();
	}

	public Double getRatioAcceptedApplications(){
		return applicationRepository.getRatioAcceptedApplications();
	}
	
	public Double getRatioRejectedApplications(){
		return applicationRepository.getRatioRejectedApplications();
	}
	
	public Double getRatioPendingApplicationsTime(){
		return applicationRepository.getRatioPendingApplicationsTime();
	}
}