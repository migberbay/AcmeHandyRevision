package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.NoteRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Note;
import domain.Report;


@Service
@Transactional
public class NoteService {

	//Managed Repository -----
	@Autowired
	private NoteRepository noteRepository;
	
	@Autowired
	private ReportService reportService;
	
	//Simple CRUD methods -----
	public Note create(){
		Note res;
		res = new Note();
		return res;
	}
	
	public Collection<Note> findAll(){
		return noteRepository.findAll();
	}
	
	public Note findOne(int Id){
		return noteRepository.findOne(Id);
	}
	
	// LOS ACTORES NO PUEDEN ACTUALIZAR LAS NOTAS UNA VEZ GUARDADAS EN LA BASE DE DATOS
	public Note save(Note n){
		//puede necesitarse control de versiones por concurrencia del objeto.
		Note saved;
		Report report;
		Authority a= new Authority();
		Authority b= new Authority();
		Authority c= new Authority();
		a.setAuthority("REFEREE");
		b.setAuthority("HANDYWORKER");
		c.setAuthority("CUSTOMER");
		UserAccount userAccount = LoginService.getPrincipal();

		Assert.isTrue(userAccount.getAuthorities().contains(a)|
				userAccount.getAuthorities().contains(b)|
				userAccount.getAuthorities().contains(c));	
		
		if(userAccount.getAuthorities().contains(a)){
			Assert.isTrue(!(n.getRefereeComment() == ""));
		}else if(userAccount.getAuthorities().contains(c)){
			Assert.isTrue(!(n.getCustomerComment() == ""));
		}else if(userAccount.getAuthorities().contains(b)){
			Assert.isTrue(!(n.getHandyWorkerComment() == ""));
		}
		Date current = new Date();
		long millis;
		millis = System.currentTimeMillis() - 1000;
		current = new Date(millis);
		
		n.setMoment(current);
		
		saved = noteRepository.save(n);
		
		report = saved.getReport();
		report.getNotes().add(saved);
		reportService.saveAut(report);
		return saved;
	}
	
	// LOS ACTORES NO PUEDEN ELIMINAR LAS NOTAS UNA VEZ GUARDADAS EN LA BASE DE DATOS
	public void delete(Note n){
		
		Authority a= new Authority();
		Authority b= new Authority();
		Authority c= new Authority();
		a.setAuthority("REFEREE");
		b.setAuthority("HANDYWORKER");
		c.setAuthority("CUSTOMER");
		UserAccount userAccount = LoginService.getPrincipal();

		Assert.isTrue(userAccount.getAuthorities().contains(a)|
				userAccount.getAuthorities().contains(b)|
				userAccount.getAuthorities().contains(c));	

		noteRepository.delete(n);
		
	}
	
	//Other business methods -----
	
	public Double getAvgNotesPerReport(){
		Double res;
		res = noteRepository.getAvgNotesPerReport();
		return res;
	}
	
	public Integer getMinNotesPerReport(){
		Integer res;
		res = noteRepository.getMinNotesPerReport();
		return res;
	}
	
	public Integer getMaxNotesPerReport(){
		Integer res;
		res = noteRepository.getMaxNotesPerReport();
		return res;
	}
	
	public Double getStdevNotesPerReport(){
		Double res;
		res = noteRepository.getStdevNotesPerReport();
		return res;
	}
	

	
}