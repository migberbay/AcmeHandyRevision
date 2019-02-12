package services;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import security.LoginService;
import utilities.AbstractTest;
import domain.Complaint;
import domain.FixUpTask;
import domain.Referee;
import domain.Report;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/datasource.xml",
										"classpath:spring/config/packages.xml"})
@Transactional
public class ComplaintServiceTest extends AbstractTest {
	
	// Service under test ---------------------------------------------------------	
	@Autowired
	private ReportService reportService;
	
	@Autowired
	private ComplaintService complaintService;
	
	@Autowired
	private FixUpTaskService fixUpTaskService;
		
	@Autowired
	private RefereeService refereeService;
	
	// Tests ----------------------------------------------------------------------
	
	// CREATE ---------------------------------------------------------------------
	
	@Test
	public void testCreateComplaints(){
		Complaint complaint;
		super.authenticate("customer1");
		complaint = complaintService.create();
		Assert.isTrue(complaint.getAttachments().isEmpty());
		Assert.isNull(complaint.getFixUpTask());
		Assert.isNull(complaint.getDescription());
		super.authenticate(null);
	}
	
	
	// SAVE -----------------------------------------------------------------------
	
	@Test 
	public void testSaveComplaints(){
		Complaint complaint,saved;
		FixUpTask fixUpTask;
		Collection<Complaint> complaints;

		super.authenticate("customer1");						// Nos autenticamos como Referee
		complaint = complaintService.create();					// Creamos el reporte
		
		//TODO: falla porque no cumple la condicion del ticker revisar

		fixUpTask = (FixUpTask) fixUpTaskService.findAll().toArray()[0];
		
		complaint.getAttachments().add("Attachment test complaint");
		complaint.setDescription("Description complaint test");
		complaint.setFixUpTask(fixUpTask);
		
		saved = complaintService.save(complaint);					// Guardamos el reporte	
		complaints = complaintService.findAll();					// Comprobamos que el reporte se ha guardado correctamente en el archivo de reportes

		Assert.isTrue(complaints.contains(saved));
		super.authenticate(null);
	}
	

	// UPDATE ---------------------------------------------------------------------
	/*
	@Test 
	public void testUpdateComplaints(){
		Complaint complaint,saved;
		super.authenticate("customer1");						// Nos autenticamos como referee
		complaint = (Complaint) complaintService.findAll().toArray()[0];			// Recuperamos el reporte
		complaint.getAttachments().add("Attachment test 2");	// Modificamos algunos atributos
		complaint.setDescription("Description test");
		saved = complaintService.save(complaint);				// Guardamos el reporte	

		Assert.isTrue(saved.getAttachments().contains("Attachment test 2"));
		Assert.isTrue(saved.getDescription().equals("Description test"));
		super.authenticate(null);
	}*/
	
	// DELETE ---------------------------------------------------------------------
/*
	@Test 
	public void testDeleteComplaints(){
		Complaint complaint;
		Collection<Complaint> complaints;
		super.authenticate("customer1");								// Nos autenticamos como referee

		complaint = (Complaint) complaintService.findAll().toArray()[0];						// Recuperamos el report al que queremos eliminar la nota
		
		complaintService.delete(complaint);									// Eliminamos la nota	
		complaints = complaintService.findAll();						
		Assert.isTrue(!complaints.contains(complaint));						// Comprobamos que la nota se ha eliminado correctamente en el archivo de notas
		
		super.authenticate(null);
	}*/
	
	@Test 
	public void testComplaintsWithNoReports(){
		Collection<Complaint> complaints;
		Collection<Complaint> allComps = new ArrayList<Complaint>();
		Collection<Report> reports = reportService.findAll();
		complaints = complaintService.getComplaintsWithNoReports();			
		for(Report r : reports){
			allComps.add(r.getComplaint());
		}
		Assert.isTrue(!allComps.contains(complaints));						
		
	}
	
	@Test 
	public void testComplaintsReferee(){
		super.authenticate("referee1");
		Collection<Complaint> complaints;
		Collection<Complaint> compManual = new ArrayList<Complaint>();
		Collection<Report> reports = reportService.findAll();
		Referee r = refereeService.findByUserAccountId(LoginService.getPrincipal().getId());
		complaints = complaintService.getComplaintsReferee(r.getId());			
		for(Report rep : reports){
			if(rep.getReferee().equals(r))
				compManual.add(rep.getComplaint());
		}
		Assert.isTrue(compManual.equals(complaints));		
	}
/*	
	@Test
	public void testComplaintsHandyWorker(int handyWorkerId){
		super.authenticate("handyWorker1");
		Collection<Complaint> complaints;
		Collection<Complaint> compManual = new ArrayList<Complaint>();
		Collection<Application> applications = applicationService.findAll();
//		HandyWorker hw = handyWorkerService.findByUserAccountId(LoginService.getPrincipal().getId());
		complaints = complaintService.getComplaintsHandyWorker(hw.getId());			
		for(Application a : applications){
			for(Complaint com: a.getFixUpTask().getComplaints())
				if(a.getHandyWorker().equals(hw))
				compManual.add(com);
		}
		Assert.isTrue(compManual.equals(complaints));
	}*/
	
}