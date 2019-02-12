package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.TutorialRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.HandyWorker;
import domain.Section;
import domain.Sponsorship;
import domain.Tutorial;


@Service
@Transactional
public class TutorialService {

	//Managed Repository -----
	@Autowired
	private TutorialRepository tutorialRepository;
	
	//Supporting Services -----
	
	@Autowired
	private SectionService sectionService; 
	
	@Autowired
	private HandyWorkerService handyWorkerService;
	
	@Autowired
	private SponsorshipService sponsorshipService;
	
	//Constructors -----
	public TutorialService(){
		super();
	}
	
	//Simple CRUD methods -----
	public Tutorial create(){
		Tutorial res;
		res = new Tutorial();
		
		HandyWorker hw = handyWorkerService.findByPrincipal();
		Date current = new Date(System.currentTimeMillis() - 1000);
		
		res.setMoment(current);
		res.setHandyWorker(hw);
		res.setPictures(new ArrayList<String>());
		
		return res;
	}
	
	public Collection<Tutorial> findAll(){
		return tutorialRepository.findAll();
	}
	
	public Tutorial findOne(int tutorialId){
		return tutorialRepository.findOne(tutorialId);
	}
	
	public Tutorial save(Tutorial tutorial){	
		Tutorial saved;
		Authority e = new Authority();
		e.setAuthority("HANDYWORKER");
		UserAccount userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().contains(e));	
		
		saved = tutorialRepository.save(tutorial);
		
		return saved;
	}
	
	public void delete(Tutorial tutorial){
		Authority e = new Authority();
		e.setAuthority("HANDYWORKER");
		UserAccount userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().contains(e));
		
		Collection<Section> sections;
		sections = sectionService.findAll();
		
		Collection<Sponsorship> sponsorships;
		sponsorships = sponsorshipService.findAll();
		
		for(Sponsorship sp: sponsorships){
			if(sp.getTutorial().equals(tutorial)){
				sponsorshipService.delete(sp);
			}
		}
		
		for(Section s: sections){
			if(s.getTutorial().getId()== tutorial.getId()){
				sectionService.delete(s);
			}
		}
		tutorialRepository.delete(tutorial);
	}
	
	//Other business methods -----
	
	public Collection<Tutorial> tutorialsByHandyWorker(int Id){
		return tutorialRepository.tutorialsByHandyWorker(Id);
	}
}