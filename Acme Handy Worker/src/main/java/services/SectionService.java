package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SectionRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Section;


@Service
@Transactional
public class SectionService {

	//Managed Repository -----
	
	@Autowired
	private SectionRepository sectionRepository;
	
	//Supporting Services -----
	
	@Autowired
	private TutorialService tutorialService;
	
	//Simple CRUD methods -----
	
	public Section create(){
		Section res = new Section();
		
		res.setPictures(new ArrayList<String>());
		
		return res;
	}
	
	public Collection<Section> findAll(){
		return sectionRepository.findAll();
	}
	
	public Section findOne(int Id){
		return sectionRepository.findOne(Id);
	}
	
	public Section save(Section section){		
		UserAccount userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("HANDYWORKER");
		Assert.isTrue(userAccount.getAuthorities().contains(au));
		
		return sectionRepository.save(section);
	}
	
//	private TutorialService tutorialService;
	public void delete(Section section){		
		UserAccount userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("HANDYWORKER");
		Assert.isTrue(userAccount.getAuthorities().contains(au));
		
		sectionRepository.delete(section);	
	}
	
	//Other business methods -----
	
	public Collection<Section> sectionsByTutorial(int Id){
		return sectionRepository.sectionsByTutorial(Id);
	}
}