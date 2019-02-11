package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.SocialProfileRepository;
import domain.SocialProfile;


@Service
@Transactional
public class SocialProfileService {

	//Managed Repository -----
	@Autowired
	private SocialProfileRepository socialProfileRepository;
	
	//Supporting Services -----
	
	
	//Simple CRUD methods -----
	public SocialProfile create(){
		SocialProfile res = new SocialProfile();
		return res;
	}
	
	public Collection<SocialProfile> findAll(){
		return socialProfileRepository.findAll();
	}
	
	public SocialProfile findOne(int Id){
		return socialProfileRepository.findOne(Id);
	}
	
	public SocialProfile save(SocialProfile a){
		SocialProfile saved;
		saved = socialProfileRepository.save(a);
		return saved;
	}
	
	public void delete(SocialProfile a){
		
		socialProfileRepository.delete(a);
	}
	
	//Other business methods -----
	
	
}