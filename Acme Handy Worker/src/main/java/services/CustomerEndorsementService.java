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
	
	//Constructors -----
	public CustomerEndorsementService(){
		super();
	}
	
	//Simple CRUD methods -----
	public CustomerEndorsement create(){
		CustomerEndorsement res = new CustomerEndorsement();
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
		e.setAuthority("CUSTOMER");
		UserAccount userAccount = LoginService.getPrincipal();
		Customer c = customerService.findByUserAccountId(userAccount.getId());
	//	HandyWorker hw = handyWorkerService.findByUserAccountId(userAccount.getId());
		Assert.isTrue(userAccount.getAuthorities().contains(e));
		
		Date current = new Date(System.currentTimeMillis() - 1000);
		
		customerEndorsement.setMoment(current);
		customerEndorsement.setCustomer(c);
	//	customerEndorsement.setHandyWorker(hw);
		saved = customerEndorsementRepository.save(customerEndorsement);
		return saved;
	}
	
	

	public void delete(CustomerEndorsement customerEndorsement){
		Authority e = new Authority();
		e.setAuthority("CUSTOMER");
		UserAccount userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().contains(e));	
		
		this.customerEndorsementRepository.delete(customerEndorsement);	
		
	}
	
	//Other business methods -----
	
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
					if((n+p)!=0) score = (p/n+p)-(n/n+p);
					res.put(ce.getCustomer(), score);
				}
			}
		}
		
		return res;
	}
	
}