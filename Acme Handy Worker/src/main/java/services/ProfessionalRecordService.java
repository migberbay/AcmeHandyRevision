package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ProfessionalRecordRepository;
import security.LoginService;
import security.UserAccount;
import domain.Curricula;
import domain.ProfessionalRecord;




@Service
@Transactional
public class ProfessionalRecordService {

	//Managed Repository -----
	@Autowired
	private ProfessionalRecordRepository professionalRecordRepository;
	
	//Supporting Services -----
	
	@Autowired
	private CurriculaService curriculaService; 

	
	//Simple CRUD methods -----
	public ProfessionalRecord create(){
		ProfessionalRecord res = new ProfessionalRecord();
		
		Date start = new Date();
		start.setTime(start.getTime()-1000);
		res.setStartDate(start);
		
		return res;
	}
	
	public Collection<ProfessionalRecord> findAll(){
		return professionalRecordRepository.findAll();
	}
	
	public ProfessionalRecord findOne(int Id){
		return professionalRecordRepository.findOne(Id);
	}
	
	public ProfessionalRecord save(ProfessionalRecord a){

		//si el HandyWorker tiene una curricula se le guarda/actualiza el ER, si no simplemente se guarda ER sin vincular.
		boolean hasCurricula = false;
		ProfessionalRecord res = null;
		Assert.isTrue(LoginService.hasRole("HANDYWORKER"));
		UserAccount logged = LoginService.getPrincipal();
		
		for (Curricula c : curriculaService.findAll()) {
			if(c.getHandyWorker().getUserAccount().equals(logged)){
				if(c.getProfessionalRecords().contains(a)){
				//ya existe en un professional record
				res = professionalRecordRepository.saveAndFlush(a);
				}else{
				//exite la curricula del handyworker.
				
				res = professionalRecordRepository.saveAndFlush(a);
				Collection<ProfessionalRecord> aux = c.getProfessionalRecords();
				aux.add(res);
				curriculaService.save(c);
				}
				hasCurricula = true;
			}
		}
		if(!hasCurricula){
			res = professionalRecordRepository.saveAndFlush(a);
		}
		Assert.notNull(res);
		return res;
	}
	
	public void delete(ProfessionalRecord a){
		//probar si necesita borrarse de la lista de curricula manualmente.
				Assert.isTrue(LoginService.hasRole("HANDYWORKER"));
				UserAccount logged = LoginService.getPrincipal();
				for (Curricula c : curriculaService.findAll()) {
					if(c.getProfessionalRecords().contains(a)&&c.getHandyWorker().getUserAccount().equals(logged)){
						c.getProfessionalRecords().remove(a);
						curriculaService.save(c);
						professionalRecordRepository.delete(a);
						//System.out.println("se borra el professionalrecord");
					}
				}
	}
	
	//Other business methods -----
}