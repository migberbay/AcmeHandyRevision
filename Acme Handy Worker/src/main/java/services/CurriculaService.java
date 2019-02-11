package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CurriculaRepository;
import security.LoginService;
import security.UserAccount;
import domain.Curricula;
import domain.EducationRecord;
import domain.EndorserRecord;
import domain.HandyWorker;
import domain.MiscellaneousRecord;
import domain.ProfessionalRecord;


@Service
@Transactional
public class CurriculaService {

	//Managed Repository -----
	@Autowired
	private CurriculaRepository curriculaRepository;
	
	//Supporting Services -----
	
	@Autowired
	private ActorService actorService;
	
	@Autowired
	private PersonalRecordService personalRecordService;
	
	//Constructors -----
	public CurriculaService(){
		super();
	}
	
	//Simple CRUD methods -----
	public Curricula create(){

		
		UserAccount ua = LoginService.getPrincipal();
		Curricula c = new Curricula();
		
		if (LoginService.hasRole("HANDYWORKER")) {
			HandyWorker hw  = (HandyWorker) actorService.getByUserAccountId(ua);
			c.setHandyWorker(hw);
		}

		c.setEducationRecords(new ArrayList<EducationRecord>());
		c.setEndorserRecords(new ArrayList<EndorserRecord>());
		c.setMiscellaneousRecords(new ArrayList<MiscellaneousRecord>());
		c.setProfessionalRecords(new ArrayList<ProfessionalRecord>());
		c.setTicker(this.generateTicker());
		
		return c;
	}
	
	public Collection<Curricula> findAll(){
		return curriculaRepository.findAll();
	}
	
	public Curricula findOne(int Id){
		return curriculaRepository.findOne(Id);
	}
	
	public Curricula save(Curricula c){
		UserAccount logged = LoginService.getPrincipal();
		Assert.isTrue(LoginService.hasRole("HANDYWORKER"));
		Assert.isTrue(c.getHandyWorker().getUserAccount().equals(logged));
		//Assert.notNull(c.getPersonalRecord());
		//sabemos que es un Handyworker y que es el dueño de la curricula que se quiere guardar.
		Curricula res = curriculaRepository.saveAndFlush(c);
		
		return res;
	}
	
	public void delete(Curricula c){
		
		UserAccount logged = LoginService.getPrincipal();
		Assert.isTrue(LoginService.hasRole("HANDYWORKER"));
		Assert.isTrue(c.getHandyWorker().getUserAccount().equals(logged));
	
		curriculaRepository.delete(c);
		personalRecordService.delete(c.getPersonalRecord());
	}
	
	//Other business methods -----
	
	public Curricula findByPersonalRecordId(Integer pr){
		return curriculaRepository.findByPersonalRecordId(pr);
	}
	
	public Curricula findByEducationRecordId(Integer er){
		return curriculaRepository.findByEducationRecordId(er);
	}
	
	public Curricula findByEndorserRecordId(Integer er){
		return curriculaRepository.findByEndorserRecordId(er);
	}
	
	public Curricula findByProfessionalRecordId(Integer er){
		return curriculaRepository.findByProfessionalRecordId(er);
	}
	
	public Curricula findByMiscellaneousRecordId(Integer er){
		return curriculaRepository.findByMiscellaneousRecordId(er);
	}
	
	
	private String generateTicker(){
		Date date = new Date(); // your date
		Calendar n = Calendar.getInstance();
		n.setTime(date);
		String t = "";
		String s = Integer.toString((n.get(Calendar.DAY_OF_MONTH)));
		String m = Integer.toString(n.get(Calendar.MONTH)+1);
		if(s.length()==1) s= "0"+Integer.toString((n.get(Calendar.DAY_OF_MONTH)));
		if(m.length()==1) m = "0"+ Integer.toString(n.get(Calendar.MONTH) +1);
		t = t + Integer.toString(n.get(Calendar.YEAR) - 2000)
				+ m
				+ s
				+ "-"+ randomWordAndNumber();

		return t;
	}
	
	private String randomWordAndNumber(){
		 String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	        StringBuilder salt = new StringBuilder();
	        Random rnd = new Random();
	        while (salt.length() < 6) { // length of the random string.
	            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
	            salt.append(SALTCHARS.charAt(index));
	        }
	        String saltStr = salt.toString();
	        return saltStr;
	}
	
}