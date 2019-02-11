package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EndorserRecordRepository;
import security.LoginService;
import security.UserAccount;
import domain.Curricula;
import domain.EndorserRecord;

@Service
@Transactional
public class EndorserRecordService {

	// Managed Repository -----
	@Autowired
	private EndorserRecordRepository endorserRecordRepository;

	// Supporting Services -----

	@Autowired
	private CurriculaService curriculaService;

	// Simple CRUD methods -----
	public EndorserRecord create() {

		EndorserRecord res = new EndorserRecord();
		return res;
	}

	public Collection<EndorserRecord> findAll() {
		return endorserRecordRepository.findAll();
	}

	public EndorserRecord findOne(int Id) {
		return endorserRecordRepository.findOne(Id);
	}

	public EndorserRecord save(EndorserRecord a){
		
		EndorserRecord res = null;
		for (Curricula c : curriculaService.findAll()) {
			if(c.getHandyWorker().getUserAccount().equals(LoginService.getPrincipal())){
				//se encuentra la curricula del handyworker
				if(a.getId() == 0){
					endorserRecordRepository.saveAndFlush(a);
					List<EndorserRecord> aux =  new ArrayList<>(c.getEndorserRecords());
					aux.add(a);
					c.setEndorserRecords(aux);
					
				}else{
					endorserRecordRepository.saveAndFlush(a);
				}
			}
		}
		
		return res;
	}

	public void delete(EndorserRecord a) {
		// probar si necesita borrarse de la lista de curricula manualmente.
		Assert.isTrue(LoginService.hasRole("HANDYWORKER"));
		UserAccount logged = LoginService.getPrincipal();
		for (Curricula c : curriculaService.findAll()) {
			if (c.getEndorserRecords().contains(a)
					&& c.getHandyWorker().getUserAccount().equals(logged)) {
				c.getEndorserRecords().remove(a);
				curriculaService.save(c);
				endorserRecordRepository.delete(a);
				// System.out.println("se borra el endorserRecord");
			}
		}
	}

	// Other business methods -----

}