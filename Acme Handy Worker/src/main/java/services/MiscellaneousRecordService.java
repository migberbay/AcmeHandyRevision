package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MiscellaneousRecordRepository;
import security.LoginService;
import security.UserAccount;
import domain.Curricula;
import domain.MiscellaneousRecord;



@Service
@Transactional
public class MiscellaneousRecordService {

	//Managed Repository -----
	@Autowired
	private MiscellaneousRecordRepository miscellaneousRecordRepository;
	
	//Supporting Services -----
	
	@Autowired
	private CurriculaService curriculaService; 
	
	//Simple CRUD methods -----
	public MiscellaneousRecord create(){
		MiscellaneousRecord res = new MiscellaneousRecord();
		return res;
	}
	
	public Collection<MiscellaneousRecord> findAll(){
		return miscellaneousRecordRepository.findAll();
	}
	
	public MiscellaneousRecord findOne(int Id){
		return miscellaneousRecordRepository.findOne(Id);
	}
	
	public MiscellaneousRecord save(MiscellaneousRecord a){
		
		//si el HandyWorker tiene una curricula se le guarda/actualiza el MR, si no simplemente se guarda MR sin vincular.
		boolean hasCurricula = false;
		MiscellaneousRecord res = null;
		Assert.isTrue(LoginService.hasRole("HANDYWORKER"));
		UserAccount logged = LoginService.getPrincipal();
		
		for (Curricula c : curriculaService.findAll()) {
			if(c.getHandyWorker().getUserAccount().equals(logged)){
				if(c.getMiscellaneousRecords().contains(a)){
				//ya existe en un miscellaneous record
				res = miscellaneousRecordRepository.saveAndFlush(a);
				}else{
				//exite la curricula del handyworker.
				
				res = miscellaneousRecordRepository.saveAndFlush(a);
				Collection<MiscellaneousRecord> aux = c.getMiscellaneousRecords();
				aux.add(res);
				curriculaService.save(c);
				}
				hasCurricula = true;
			}
		}
		if(!hasCurricula){
			res = miscellaneousRecordRepository.saveAndFlush(a);
		}
		Assert.notNull(res);
		return res;
	}
	
	public void delete(MiscellaneousRecord a){
		// probar si necesita borrarse de la lista de curricula manualmente.
				Assert.isTrue(LoginService.hasRole("HANDYWORKER"));
				UserAccount logged = LoginService.getPrincipal();
				for (Curricula c : curriculaService.findAll()) {
					if (c.getMiscellaneousRecords().contains(a)
							&& c.getHandyWorker().getUserAccount().equals(logged)) {
						c.getMiscellaneousRecords().remove(a);
						curriculaService.save(c);
						miscellaneousRecordRepository.delete(a);
						//System.out.println("se borra el miscellaneousRecord");
					}
				}
			}
	
	//Other business methods -----
	
	
}