package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.WarrantyRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Warranty;


@Service
@Transactional
public class WarrantyService {

	//Managed Repository -----
	
	@Autowired
	private WarrantyRepository warrantyRepository;
	
	//Supporting Services -----

	
	
	//Simple CRUD methods -----
	
	public Warranty create(){
		Warranty res = new Warranty();
		
		res.setIsDraft(true);
		
		return res;
	}
	
	public Collection<Warranty> findAll(){
		return warrantyRepository.findAll();
	}
	
	public Warranty findOne(int Id){
		return warrantyRepository.findOne(Id);
	}
	
	public Warranty save(Warranty a){
		
		UserAccount userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		Authority b = new Authority();
		
		au.setAuthority("ADMIN");
		b.setAuthority("CUSTOMER");
		
		Assert.isTrue(userAccount.getAuthorities().contains(au) || userAccount.getAuthorities().contains(b));
		
		return warrantyRepository.save(a);
	}
	
	public void delete(Warranty a){
		UserAccount userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("ADMIN");
		Assert.isTrue(userAccount.getAuthorities().contains(au));
		Assert.isTrue(a.getIsDraft());
		
		warrantyRepository.delete(a);
	}
	
	//Other business methods -----
	
	public Collection<Warranty> findWarrantiesWNoTask(){
		Collection<Warranty> res;
		res = warrantyRepository.findWarrantiesWNoTask();
		return res;
	}
	
	
}