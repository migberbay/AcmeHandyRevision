package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RefereeRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Referee;
import domain.SocialProfile;


@Service
@Transactional
public class RefereeService {

	//Managed Repository -----
	@Autowired
	private RefereeRepository refereeRepository;
	
	@Autowired
	private UserAccountService userAccountService;
	
	//Simple CRUD methods -----
	public Referee create(){
		Referee res = new Referee();
		Authority aut = new Authority();
		aut.setAuthority("REFEREE");
		res.setSocialProfiles(new ArrayList<SocialProfile>());
		UserAccount ua = userAccountService.create();
		ua.getAuthorities().add(aut);
		res.setUserAccount(userAccountService.save(ua));
		return res;
	}
	
	public Collection<Referee> findAll(){
		return refereeRepository.findAll();
	}
	
	public Referee findOne(int Id){
		return refereeRepository.findOne(Id);
	}
	
	public Referee save(Referee r){
		
		Referee result;

		Assert.isTrue(LoginService.hasRole("ADMIN") || LoginService.hasRole("REFEREE"));

		result = refereeRepository.saveAndFlush(r);
		return result;
	
	}
	/*
	public void delete(Referee a){
		UserAccount userAccount = LoginService.getPrincipal();
		// modificar para aplicarlo a la entidad correspondiente.
		//Assert.isTrue(a.getUserAccount().equals(userAccount));
		
		refereeRepository.delete(a);
	}*/
	
	//Other business methods -----
	
	public Referee findByUserAccountId(Integer Id){
		Referee r;
		r = refereeRepository.findByUserAccountId(Id);
		return r;
	}
	
}