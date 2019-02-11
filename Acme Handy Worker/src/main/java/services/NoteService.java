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

		Date current = new Date();
		long millis;
		millis = System.currentTimeMillis() - 1000;
		current = new Date(millis);
		res.setMoment(current);

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
		Authority refereeAuthority= new Authority();
		Authority handyWorkerAuthority= new Authority();
		Authority customerAuthority= new Authority();
		refereeAuthority.setAuthority("REFEREE");
		handyWorkerAuthority.setAuthority("HANDYWORKER");
		customerAuthority.setAuthority("CUSTOMER");
		UserAccount userAccount = LoginService.getPrincipal();

		Assert.isTrue(userAccount.getAuthorities().contains(refereeAuthority)|
				userAccount.getAuthorities().contains(handyWorkerAuthority)|
				userAccount.getAuthorities().contains(customerAuthority));

		if (n.getId() == 0){
			Date current = new Date();
			long millis;
			millis = System.currentTimeMillis() - 1000;
			current = new Date(millis);
			n.setMoment(current);

			//Comprobamos que el campo que esté lleno sea el del actor creando la nota
			Assert.isTrue(userAccount.getAuthorities().contains(handyWorkerAuthority) || n.getHandyWorkerComment() == null);
			Assert.isTrue(userAccount.getAuthorities().contains(refereeAuthority) || n.getRefereeComment() == null);
			Assert.isTrue(userAccount.getAuthorities().contains(customerAuthority) || n.getCustomerComment() == null);

		}else{
			Note savedNote = findOne(n.getId());
			//No funciona Assert.isTrue(n.getMoment().equals(savedNote.getMoment()));
			n.setMoment(savedNote.getMoment());
			if (userAccount.getAuthorities().contains(handyWorkerAuthority)){
				n.setRefereeComment(savedNote.getRefereeComment());
				n.setCustomerComment(savedNote.getCustomerComment());
			}else if(userAccount.getAuthorities().contains(refereeAuthority)){
				n.setHandyWorkerComment(savedNote.getHandyWorkerComment());
				n.setCustomerComment(savedNote.getCustomerComment());
			}else if(userAccount.getAuthorities().contains(customerAuthority)){
				n.setRefereeComment(savedNote.getRefereeComment());
				n.setHandyWorkerComment(savedNote.getHandyWorkerComment());
			}
		}

		saved = noteRepository.save(n);
		
		report = saved.getReport();
		report.getNotes().add(saved);
		reportService.saveAut(report);
		return saved;
	}
	
	// LOS ACTORES NO PUEDEN ELIMINAR LAS NOTAS UNA VEZ GUARDADAS EN LA BASE DE DATOS
	//--- Y para que se hace un delete entonces?
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