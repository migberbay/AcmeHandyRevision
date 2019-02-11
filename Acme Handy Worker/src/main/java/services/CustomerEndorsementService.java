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

import repositories.CustomerEndorsementRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Customer;
import domain.CustomerEndorsement;
import domain.HandyWorkerEndorsement;
import domain.Word;


@Service
@Transactional
public class CustomerEndorsementService {

	//Managed Repository -----
	@Autowired
	private CustomerEndorsementRepository customerEndorsementRepository;
	
	//Supporting Services -----
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private WordService wordService;
	
	@Autowired
	private HandyWorkerService handyWorkerService;
	
	//Constructors -----
	public CustomerEndorsementService(){
		super();
	}
	
	//Simple CRUD methods -----
	public CustomerEndorsement create(){
		CustomerEndorsement res = new CustomerEndorsement();
		Date current = new Date(System.currentTimeMillis() - 1000);	
		res.setMoment(current);
		res.setHandyWorker(handyWorkerService.findByUserAccountId(LoginService.getPrincipal().getId()));

		return res;
	}
	
	public Collection<CustomerEndorsement> findAll(){
		return customerEndorsementRepository.findAll();
	}
	
	public CustomerEndorsement findOne(int customerEndorsementId){
		return customerEndorsementRepository.findOne(customerEndorsementId);
	}
	
	public CustomerEndorsement save(CustomerEndorsement customerEndorsement){
		CustomerEndorsement saved;
		Authority e = new Authority();
		e.setAuthority("HANDYWORKER");
		UserAccount userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().contains(e));

		saved = customerEndorsementRepository.save(customerEndorsement);
		return saved;
	}
	
	

	public void delete(CustomerEndorsement customerEndorsement){
		Authority e = new Authority();
		e.setAuthority("HANDYWORKER");
		UserAccount userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().contains(e));	
		
		this.customerEndorsementRepository.delete(customerEndorsement);	
		
	}
	
	//Other business methods -----
	
	public Collection<CustomerEndorsement> customerEndorsementsByHandyWorker(int handyWorkerId){
		Collection<CustomerEndorsement> res;
		res = customerEndorsementRepository.customerEndorsementsByHandyWorker(handyWorkerId);
		return res;
	}
	
	//A - RF 50.1
	public Map<Customer,Double> getScoreCustomerEndorsement(){
		Map<Customer,Double> res = new HashMap<Customer,Double>();
		Collection<Customer> customers = customerService.findAll();
		Collection<CustomerEndorsement> endorsements = customerEndorsementRepository.findAll();
		Double p=0d,n = 0d;
		Collection<String> positives = new ArrayList<String>();
		Collection<String> negatives = new ArrayList<String>();

		for(Word w: wordService.findPositiveWords()) positives.add(w.getContent());
		for(Word w: wordService.findNegativeWords()) negatives.add(w.getContent());
		for(CustomerEndorsement ce: endorsements){
			for(Customer c: customers){
				if(ce.getCustomer().equals(c)){
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
					if((n+p)!=0) score = ((p/(n+p))-(n/(n+p)));
					if(res.containsKey(ce.getCustomer()))
						res.put(ce.getCustomer(), res.get(ce.getCustomer())+score);
					else
					res.put(ce.getCustomer(), score);
					
					break;
				}
			}
		}
		
		for(Customer c: customers){
			if(!res.keySet().contains(c))
				res.put(c, 0d);
		}
		
		return res;
	}
	
}