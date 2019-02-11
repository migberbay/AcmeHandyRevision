package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PersonalRecordRepository;
import security.LoginService;
import security.UserAccount;
import domain.Curricula;

import domain.PersonalRecord;


@Service
@Transactional
public class PersonalRecordService {

	//Managed Repository -----
	@Autowired
	private PersonalRecordRepository personalRecordRepository;
	
	//Supporting Services -----
	
	@Autowired
	private CurriculaService curriculaService; 

	
	//Simple CRUD methods -----
	public PersonalRecord create(){

		PersonalRecord res = new PersonalRecord();
		return res;
	}
	
	public Collection<PersonalRecord> findAll(){
		return personalRecordRepository.findAll();
	}
	
	public PersonalRecord findOne(int Id){
		return personalRecordRepository.findOne(Id);
	}
	
	public PersonalRecord save(PersonalRecord a){
		
		PersonalRecord res = null;
		//si la id del record es cero no tiene curricula, si la id del record existe si tiene curricula, no se contemplan mas estados.
		if(a.getId() == 0){// no tiene curricula
			
			System.out.println("no tiene curricula y le creamos una");
			res = personalRecordRepository.saveAndFlush(a);
			Curricula c = curriculaService.create();
			c.setPersonalRecord(res);
			System.out.println("se intenta guardar la curricula con ticker: "+c.getTicker());
			curriculaService.save(c);
			
		}else{//Si tiene curricula
			System.out.println("si tiene curricula se actualiza su personal Record");
			res = personalRecordRepository.saveAndFlush(a); 
		}
		
		return res;
	}
	
	public void delete(PersonalRecord a){
		
		Assert.isTrue(LoginService.hasRole("HANDYWORKER"));
		boolean hasCurricula = false;
		//vemos si existe la curricula, si, si llamamos al delete de curricula si no borramos el personalrecord
		for (Curricula c : curriculaService.findAll()) {
			if(c.getPersonalRecord().equals(a)){
				curriculaService.delete(c);
				hasCurricula = true;
			}
		}
		if(!hasCurricula){
			personalRecordRepository.delete(a);
		}
	}
	
	//Other business methods -----
	
	
}