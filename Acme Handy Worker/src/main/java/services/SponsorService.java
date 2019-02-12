package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.SponsorRepository;
import security.Authority;
import security.UserAccount;
import security.UserAccountService;
import domain.Customer;
import domain.SocialProfile;
import domain.Sponsor;


@Service
@Transactional
public class SponsorService {

	//Managed Repository -----
	
	@Autowired
	private SponsorRepository sponsorRepository;
		
	@Autowired
	private UserAccountService userAccountService;
	//Supporting Services -----
	
	@Autowired
	private UserAccountService uaService;
	
	//Simple CRUD methods -----
	public Sponsor create(){
		Authority authority = new Authority();
		authority.setAuthority("SPONSOR");

		UserAccount user = new UserAccount();
		user.addAuthority(authority);

		Sponsor sponsor = new Sponsor();
		sponsor.setUserAccount(user);
		sponsor.setSocialProfiles(new ArrayList<SocialProfile>());
		sponsor.setIsBanned(false);
		sponsor.setIsSuspicious(false);
		System.out.println(sponsor.getUserAccount().getPassword());
		
		return sponsor;
	}
	
	public Collection<Sponsor> findAll(){
		return sponsorRepository.findAll();
	}
	
	public Sponsor findOne(int Id){
		return sponsorRepository.findOne(Id);
	}
	
	public Sponsor save(Sponsor a){
	/*	UserAccount saved = userAccountService.save(a.getUserAccount());
		a.setUserAccount(saved);*/
		return sponsorRepository.saveAndFlush(a);
	}
	
	public void delete(Sponsor a){

		sponsorRepository.delete(a);
	}
	
	//Other business methods -----
	
	public Sponsor findSponsorByUserAccount(UserAccount ua){
		return sponsorRepository.findSponsorByUserAccount(ua.getId());
	}
}