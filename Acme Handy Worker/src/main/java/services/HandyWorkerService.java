package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.HandyWorkerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.HandyWorker;
import domain.SocialProfile;

@Service
@Transactional
public class HandyWorkerService {

	// Managed Repository -----
	@Autowired
	private HandyWorkerRepository handyWorkerRepository;

	// Supporting Services -----

	// Simple CRUD methods -----
	public HandyWorker create() {
		Authority authority = new Authority();
		authority.setAuthority("HANDYWORKER");

		UserAccount user = new UserAccount();
		user.addAuthority(authority);

		HandyWorker handyWorker = new HandyWorker();
		
		handyWorker.setUserAccount(user);
		handyWorker.setSocialProfiles(new ArrayList<SocialProfile>());
		handyWorker.setIsBanned(false);
		handyWorker.setIsSuspicious(false);
		handyWorker.setMake("");
		
		return handyWorker;
	}

	public Collection<HandyWorker> findAll() {
		return handyWorkerRepository.findAll();
	}

	public HandyWorker findOne(int handyWorkerId) {
		return handyWorkerRepository.findOne(handyWorkerId);
	}

	public HandyWorker save(HandyWorker hw) {

//		UserAccount userAccount = LoginService.getPrincipal();
//		if(hw.getId()!=0)Assert.isTrue(userAccount.equals(hw.getUserAccount()));
//		
	
//
//		System.out.println("el make es:"+hw.getMake());
		if(hw.getMake()==null || hw.getMake().equals("")){
			hw.setMake(hw.getName()+" " + hw.getMiddleName()+ " " + hw.getSurname());
		}
		HandyWorker saved;
		saved = handyWorkerRepository.saveAndFlush(hw);

		return saved;
	}

//	public void delete(HandyWorker handyWorker) {
//		Assert.notNull(handyWorker);
//		Assert.isTrue(handyWorker.getId() != 0);
//
//		Collection<SocialProfile> socialprofiles = handyWorker
//				.getSocialProfiles();
//
//		// Borrar los identities de ese handy worker
//		for (SocialProfile sp : socialprofiles) {
//			socialProfileService.delete(sp);
//		}
//		this.handyWorkerRepository.delete(handyWorker);
//	}

	// Other business methods -----
	
	public HandyWorker findByUserAccountId(Integer Id){
		HandyWorker hw;
		hw = handyWorkerRepository.findByUserAccountId(Id);
		return hw;
	}

	public HandyWorker findByPrincipal() {
		HandyWorker hw;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		hw = handyWorkerRepository.findByPrincipal(userAccount.getId());
		return hw;
	}
	
	public Collection<HandyWorker> getHandyWorkersByCustomerTasks(int customerId){
		Collection<HandyWorker> res;
		res = handyWorkerRepository.getHandyWorkersByCustomerTasks(customerId);
		return res;
	}
	
	public Collection<HandyWorker> getHandyWorkersWMoreApplicationsThanAvg(){
		Collection<HandyWorker> res;
		res = handyWorkerRepository.getHandyWorkersWMoreApplicationsThanAvg();
		return res;
	}
	
	public Collection<HandyWorker> TopThreeInComplaints(){
		Collection<HandyWorker> res;
		res = handyWorkerRepository.topThreeInComplaints();
		return res;
	}
	

}