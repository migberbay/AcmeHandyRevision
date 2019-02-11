package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.HandyWorkerEndorsementRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;

import domain.HandyWorker;
import domain.HandyWorkerEndorsement;
import domain.Word;



@Service
@Transactional
public class HandyWorkerEndorsementService {

	//Managed Repository -----
	@Autowired
	private HandyWorkerEndorsementRepository handyWorkerEndorsementRepository;
	
	//Supporting Services -----
	@Autowired
	private HandyWorkerService handyWorkerService;
	
	@Autowired
	private WordService wordService;

	
	//Simple CRUD methods -----
	public HandyWorkerEndorsement create(){
		HandyWorkerEndorsement res = new HandyWorkerEndorsement();
//		res.setCustomer(new Customer());
//		res.setHandyWorker(new HandyWorker());
		return res;
	}
	
	public Collection<HandyWorkerEndorsement> findAll(){
		return handyWorkerEndorsementRepository.findAll();
	}
	
	public HandyWorkerEndorsement findOne(int HandyWorkerEndorsementId){
		return handyWorkerEndorsementRepository.findOne(HandyWorkerEndorsementId);
	}
	
	public HandyWorkerEndorsement save(HandyWorkerEndorsement handyWorkerEndorsement){
		
		HandyWorkerEndorsement saved;
		Authority e = new Authority();
		e.setAuthority("HANDYWORKER");
		UserAccount userAccount = LoginService.getPrincipal();
		HandyWorker hw = handyWorkerService.findByUserAccountId(userAccount.getId());
		//Customer c = new Customer();
		Assert.isTrue(userAccount.getAuthorities().contains(e));
		
		Date current = new Date(System.currentTimeMillis() - 1000);

		
		handyWorkerEndorsement.setMoment(current);
		handyWorkerEndorsement.setHandyWorker(hw);
	//	handyWorkerEndorsement.setCustomer(c);
		saved = handyWorkerEndorsementRepository.save(handyWorkerEndorsement);
		
		return saved;
	}
	
	public void delete(HandyWorkerEndorsement handyWorkerEndorsement){
		
		Authority e = new Authority();
		e.setAuthority("HANDYWORKER");
		UserAccount userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().contains(e));	
		
		this.handyWorkerEndorsementRepository.delete(handyWorkerEndorsement);		
	}
	
	//Other business methods -----
	
	//A - RF 50.1
	public Map<HandyWorker,Double> getScoreHandyWorkerEndorsement(){
		Map<HandyWorker,Double> res = new HashMap<HandyWorker,Double>();
		Collection<HandyWorker> workers = handyWorkerService.findAll();
		Collection<HandyWorkerEndorsement> endorsements = handyWorkerEndorsementRepository.findAll();
		Double p=0d,n = 0d;
		Collection<String> positives = new ArrayList<String>();
		Collection<String> negatives = new ArrayList<String>();

		for(Word w: wordService.findPositiveWords()) positives.add(w.getContent());
		for(Word w: wordService.findNegativeWords()) negatives.add(w.getContent());

		
		for(HandyWorkerEndorsement ce: endorsements){
			for(HandyWorker c: workers){
				if(ce.getHandyWorker().equals(c)){
					String text = ce.getText();
					String[] part = text.split( "[\\ \\.\\,\\. \\, ]");		
					for(int i=0;i<part.length;i++){
						if(positives.contains(part[i])){
							p =p + 1d;
						}else if(negatives.contains(part[i])){
							n = n - 1d;
						}
					}
					Double score = 0d;
					if((n+p)!=0) score = (p/n+p)-(n/n+p);
					res.put(ce.getHandyWorker(), score);
				}
			}
		}
		
		return res;
	}
	
	
}