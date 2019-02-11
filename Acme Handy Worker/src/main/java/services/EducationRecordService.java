package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EducationRecordRepository;
import security.LoginService;
import security.UserAccount;
import domain.Curricula;
import domain.EducationRecord;



@Service
@Transactional
public class EducationRecordService {

	//Managed Repository -----
	@Autowired
	private EducationRecordRepository educationRecordRepository;
	
	//Supporting Services -----
	
	@Autowired
	private CurriculaService curriculaService; 
	
	//Simple CRUD methods -----
	public EducationRecord create(){
		EducationRecord res = new EducationRecord();
		
		Date start = new Date();
		start.setTime(start.getTime()-1000);
		res.setStartDate(start);
		
		return res;
	}
	
	public Collection<EducationRecord> findAll(){
		return educationRecordRepository.findAll();
	}
	
	public EducationRecord findOne(int Id){
		return educationRecordRepository.findOne(Id);
	}
	
	public EducationRecord save(EducationRecord a){
		
		//si el HandyWorker tiene una curricula se le guarda/actualiza el ER, si no simplemente se guarda ER sin vincular.
		boolean hasCurricula = false;
		EducationRecord res = null;
		Assert.isTrue(LoginService.hasRole("HANDYWORKER"));
		UserAccount logged = LoginService.getPrincipal();
		
		for (Curricula c : curriculaService.findAll()) {
			if(c.getHandyWorker().getUserAccount().equals(logged)){
				if(c.getEducationRecords().contains(a)){
				//ya existe en un education record
				res = educationRecordRepository.saveAndFlush(a);
				}else{
				//exite la curricula del handyworker.
				
				res = educationRecordRepository.saveAndFlush(a);
				Collection<EducationRecord> aux = c.getEducationRecords();
				aux.add(res);
				curriculaService.save(c);
				}
				hasCurricula = true;
			}
		}
		if(!hasCurricula){
			res = educationRecordRepository.saveAndFlush(a);
		}
		Assert.notNull(res);
		return res;
	}
	
	public void delete(EducationRecord a){
		//probar si necesita borrarse de la lista de curricula manualmente.
		Assert.isTrue(LoginService.hasRole("HANDYWORKER"));
		UserAccount logged = LoginService.getPrincipal();
		for (Curricula c : curriculaService.findAll()) {
			if(c.getEducationRecords().contains(a)&&c.getHandyWorker().getUserAccount().equals(logged)){
				c.getEducationRecords().remove(a);
				curriculaService.save(c);
				educationRecordRepository.delete(a);
				//System.out.println("se borra el educationrecord");
			}
		}
	}
	
	//Other business methods -----
	
}