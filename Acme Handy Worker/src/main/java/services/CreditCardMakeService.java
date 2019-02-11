package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CreditCardMakeRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.CreditCardMake;


@Service
@Transactional
public class CreditCardMakeService {

	//Managed Repository -----
	@Autowired
	private CreditCardMakeRepository creditCardMakeRepository;
	
	//Supporting Services -----
	
	//Constructors -----
	public CreditCardMakeService(){
		super();
	}
	
	//Simple CRUD methods -----
	public CreditCardMake create(){
		CreditCardMake res = new CreditCardMake();
		return res;
	}
	
	public Collection<CreditCardMake> findAll(){
		return creditCardMakeRepository.findAll();
	}
	
	public CreditCardMake findOne(int Id){
		return creditCardMakeRepository.findOne(Id);
	}
	
	public CreditCardMake save(CreditCardMake a){		
		CreditCardMake saved;
		UserAccount userAccount = LoginService.getPrincipal();
		Authority n = new Authority();
		n.setAuthority("ADMIN");
		Assert.isTrue(userAccount.getAuthorities().contains(n));
		saved = creditCardMakeRepository.save(a);
		return saved;
	}
	
	public void delete(CreditCardMake a){
		UserAccount userAccount = LoginService.getPrincipal();
		Authority n = new Authority();
		n.setAuthority("ADMIN");
		Assert.isTrue(userAccount.getAuthorities().contains(n));
		creditCardMakeRepository.delete(a);
	}
	
	//Other business methods -----
	
	
}