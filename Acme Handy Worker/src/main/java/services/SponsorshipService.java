package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SponsorshipRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Sponsor;
import domain.Sponsorship;


@Service
@Transactional
public class SponsorshipService {

	//Managed Repository -----
	@Autowired
	private SponsorshipRepository sponsorshipRepository;
	
	//Supporting Services -----

	@Autowired
	private SponsorService sponsorService; 
	
	//Constructors -----
	public SponsorshipService(){
		super();
	}
	
	//Simple CRUD methods -----
	public Sponsorship create(){
		Sponsorship res = new Sponsorship();
		res.setSponsor(sponsorService.findSponsorByUserAccount(LoginService.getPrincipal()));
		return res;
	}
	
	public Collection<Sponsorship> findAll(){
		return sponsorshipRepository.findAll();
	}
	
	public Sponsorship findOne(int Id){
		return sponsorshipRepository.findOne(Id);
	}
	
	public Sponsorship save(Sponsorship a){
		UserAccount userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("SPONSOR");
		Assert.isTrue(userAccount.getAuthorities().contains(au));

		return sponsorshipRepository.save(a);
	}
	
	public void delete(Sponsorship a){
		UserAccount userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		Authority n = new Authority();
		au.setAuthority("SPONSOR");
		n.setAuthority("HANDYWORKER");
		Assert.isTrue(userAccount.getAuthorities().contains(au) || userAccount.getAuthorities().contains(n));
		
		sponsorshipRepository.delete(a);
	}
	
	//Other business methods -----
	
	public Collection<Sponsorship> getSponsorshipsSponsor(int sponsorId){
		Collection<Sponsorship> res;
		res = sponsorshipRepository.getSponsorshipsSponsor(sponsorId);
		return res;
	}
	
	public Collection<Sponsorship> getSponsorshipsTutorial(int tutorialId){
		Collection<Sponsorship> res;
		res = sponsorshipRepository.getSponsorshipsTutorial(tutorialId);
		return res;
	}
	
	
}