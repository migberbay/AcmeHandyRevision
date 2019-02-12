package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ComplaintRepository;
import security.LoginService;
import domain.Complaint;
import domain.Customer;
import domain.FixUpTask;
import domain.Report;

import services.CustomerService;
import services.FixUpTaskService;
import services.ReportService;

@Service
@Transactional
public class ComplaintService {

	// Managed Repository -----
	@Autowired
	private ComplaintRepository complaintRepository;

	// Supporting Services -----

	@Autowired
	private CustomerService customerService;

	@Autowired
	private FixUpTaskService fixUpTaskService;

	@Autowired
	private ReportService reportService;

	// Simple CRUD methods -----
	public Complaint create() {
		Complaint res = new Complaint();

		Date current = new Date(System.currentTimeMillis() - 1000);
		Customer customer = customerService.findByUserAccountId(LoginService
				.getPrincipal().getId());
		res.setMoment(current);

		res.setCustomer(customer);
		res.setMoment(current);
		res.setTicker(generateTicker());

		res.setAttachments(new ArrayList<String>());
		return res;
	}

	public Collection<Complaint> findAll() {
		return complaintRepository.findAll();
	}

	public Complaint findOne(int Id) {
		return complaintRepository.findOne(Id);
	}

	// LOS ACTORES NO PUEDEN ACTUALIZAR LOS COMPLAINTS UNA VEZ GUARDADOS EN LA
	// BASE DE DATOS
	public Complaint save(Complaint c) {
		Complaint saved;
		Collection<Complaint> complaints;
		FixUpTask fx;

		saved = complaintRepository.save(c);
		complaintRepository.flush();

		fx = saved.getFixUpTask();
		fx.getComplaints().add(saved);
		fixUpTaskService.save(fx);

		complaints = complaintRepository.findAll(); // Comprobamos que el
													// reporte se ha guardado
													// correctamente en el
													// archivo de reportes

		Assert.isTrue(complaints.contains(saved));

		return saved;
	}

	/*
	 * // LOS ACTORES NO PUEDEN ACTUALIZAR LOS COMPLAINTS UNA VEZ GUARDADOS EN
	 * LA BASE DE DATOS public Complaint save(Complaint c){ Complaint saved;
	 * Collection<Complaint> complaints; FixUpTask fx;
	 * Assert.isTrue(c.getId()==0 || c.getId() != 0 &&
	 * c.getCustomer().getUserAccount().equals(LoginService.getPrincipal()));
	 * Date current = new Date(System.currentTimeMillis() - 1000); Customer
	 * customer =
	 * customerService.findByUserAccountId(LoginService.getPrincipal().getId());
	 * c.setMoment(current);
	 * 
	 * if(c.getId()==0){ c.setCustomer(customer); c.setMoment(current);
	 * c.setTicker(generateTicker()); }
	 * 
	 * saved = complaintRepository.save(c); complaintRepository.flush();
	 * 
	 * fx = saved.getFixUpTask(); fx.getComplaints().add(saved);
	 * fixUpTaskService.save(fx);
	 * 
	 * complaints = complaintRepository.findAll(); // Comprobamos que el reporte
	 * se ha guardado correctamente en el archivo de reportes
	 * 
	 * Assert.isTrue(complaints.contains(saved));
	 * 
	 * return saved; }
	 */

	// LOS ACTORES NO PUEDEN ELIMINAR LOS COMPLAINTS UNA VEZ GUARDADOS EN LA
	// BASE DE DATOS
	public void delete(Complaint c) {
		Assert.isTrue(c.getCustomer().getUserAccount()
				.equals(LoginService.getPrincipal()));
		Collection<Complaint> complaints;
		Collection<Report> reports;

		FixUpTask fx = c.getFixUpTask();

		reports = reportService.getReportsByComplaint(c.getId());

		for (Report r : reports) {
			reportService.deleteAut(r);
		}

		if (fx != null) {
			fx.getComplaints().remove(c);
			fixUpTaskService.save(fx);
		}
		complaintRepository.delete(c);

		complaints = complaintRepository.findAll();

		Assert.isTrue(!(complaints.contains(c)));
	}

	// Other business methods -----

	// B-RF 36.1
	public Collection<Complaint> getComplaintsWithNoReports() {
		Collection<Complaint> res;
		res = complaintRepository.getComplaintsWithNoReports();
		return res;
	}

	// B-RF 36.2
	public Collection<Complaint> getComplaintsReferee(int refereeId) {
		Collection<Complaint> res;
		res = complaintRepository.getComplaintsReferee(refereeId);
		return res;
	}

	public Collection<Complaint> getComplaintsCustomer(Customer customer){
		return complaintRepository.getComplaintsCustomer(customer.getId());
	}

	// B-RF 37.3
	public Collection<Complaint> getComplaintsHandyWorker(int handyWorkerId) {
		Collection<Complaint> res;
		res = complaintRepository.getComplaintsHandyWorker(handyWorkerId);
		return res;
	}

	public Collection<Complaint> getComplaintsFixUpTask(int fixUpTaskId) {
		Collection<Complaint> res;
		res = complaintRepository.getComplaintsFixUpTask(fixUpTaskId);
		return res;
	}

	public Double getAvgComplaintsPerTask() {
		Double res;
		res = complaintRepository.getAvgComplaintsPerTask();
		return res;
	}

	public Integer getMinComplaintsPerTask() {
		Integer res;
		res = complaintRepository.getMinComplaintsPerTask();
		return res;
	}

	public Integer getMaxComplaintsPerTask() {
		Integer res;
		res = complaintRepository.getMaxComplaintsPerTask();
		return res;
	}

	public Double getStdevComplaintsPerTask() {
		Double res;
		res = complaintRepository.getStdevComplaintsPerTask();
		return res;
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