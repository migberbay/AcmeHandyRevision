package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Complaint;
import domain.Report;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/datasource.xml",
										"classpath:spring/config/packages.xml"})
@Transactional
public class ReportServiceTest extends AbstractTest {
	
	// Service under test ---------------------------------------------------------	
	@Autowired
	private ReportService reportService;
	
	@Autowired
	private ComplaintService complaintService;
	
	// Tests ----------------------------------------------------------------------
	
	// CREATE ---------------------------------------------------------------------
	
	@Test
	public void testCreateReports(){
		Report report;
		super.authenticate("referee1");
		report = reportService.create();
		Assert.isTrue(report.getAttachments().isEmpty());
		Assert.isNull(report.getComplaint());
		Assert.isNull(report.getDescription());
		Assert.isNull(report.getIsDraft());
		Assert.notNull(report.getMoment());
		Assert.isTrue(report.getNotes().isEmpty());
		Assert.notNull(report.getReferee());
		super.authenticate(null);
	}
	
	
	// SAVE -----------------------------------------------------------------------
	
	@Test 
	public void testSaveReports(){
		Report report,saved;
		Complaint complaint;
		Collection<Report> reports;
		super.authenticate("referee1");						// Nos autenticamos como Referee
		report = reportService.create();					// Creamos el reporte
		complaint = (Complaint) complaintService.findAll().toArray()[0];
		
		report.setDescription("Description test");
		report.getAttachments().add("Attachment test");
		report.setComplaint(complaint);
		
		saved = reportService.save(report);					// Guardamos el reporte	

		reports = reportService.findAll();					// Comprobamos que el reporte se ha guardado correctamente en el archivo de reportes

		Assert.isTrue(reports.contains(saved));
		super.authenticate(null);
	}
	

	// UPDATE ---------------------------------------------------------------------
	
	@Test 
	public void testUpdateReports(){
		Report report = new Report();
		Collection<Report> reports;
		super.authenticate("referee2");						// Nos autenticamos como referee
		reports = reportService.findAll();
		for(Report r: reports){
			if(r.getIsDraft()) report=r;							// Recuperamos el reporte
		}
		report.getAttachments().add("Attachment test 2");	// Modificamos algunos atributos

		reportService.save(report);				// Guardamos el reporte	

		super.authenticate(null);
	}
	
	// DELETE ---------------------------------------------------------------------

	@Test 
	public void testDeleteReports(){
		Report report;
		Collection<Report> reports;
		super.authenticate("referee1");								// Nos autenticamos como referee

		report = (Report) reportService.findAll().toArray()[0];						// Recuperamos el report al que queremos eliminar la nota
		

		reportService.delete(report);									// Eliminamos la nota	
		reports = reportService.findAll();						
		Assert.isTrue(!reports.contains(report));						// Comprobamos que la nota se ha eliminado correctamente en el archivo de notas
		
		super.authenticate(null);
	}
	
	
}