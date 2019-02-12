package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Application;
import domain.CreditCard;
import domain.FixUpTask;
import domain.HandyWorker;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
public class ApplicationServiceTest extends AbstractTest {

	@Autowired
	private ApplicationService applicationService;

	@Autowired
	private HandyWorkerService handyWorkerService;

	@Autowired
	private FixUpTaskService fixUpTaskService;

	@Test
	public void testCreate() {
		authenticate("handyworker1");
		Application application = applicationService.create();
		Assert.state(application.getStatus() != "",
				"El estado de la aplicación no debe ser nulo");
		unauthenticate();
	}

	@Test
	public void testSave() {
		authenticate("handyworker1");

		Application application, result;
		application = applicationService.create();

		// Date current = new Date(System.currentTimeMillis() - 1000);
		Date fecha = new Date();

		// https://codechi.com/dev-tools/date-to-millisecond-calculators/
		// Añadir una f al final para indicar que es un long
		fecha.setTime(1608591600000l);

		// application.setMoment(current);
		application.setPrice(68.5);
		application.setStatus("PENDING");
		application.setHandyWorkerComment("");
		application.setCustomerComment("");

		CreditCard credit = new CreditCard();
		credit.setBrand("brand1");
		credit.setCVV(773);
		credit.setExpirationDate(fecha);
		credit.setHolder("holder1");
		credit.setNumber("1234567891234567");

		application.setCreditCard(credit);

		// FixUpTask fixUpTask = fixUpTaskService.findOne(15949);
		FixUpTask fixUpTask = (FixUpTask) fixUpTaskService.findAll().toArray()[3];
		application.setFixUpTask(fixUpTask);

		// HandyWorker handyWorker = handyWorkerService.findOne(15726);
		HandyWorker handyWorker = (HandyWorker) handyWorkerService.findAll()
				.toArray()[0];
		application.setHandyWorker(handyWorker);

		result = applicationService.save(application);
		Assert.isTrue(applicationService.findAll().contains(result));
		unauthenticate();
	}

	@Test
	public void testUpdateHandyWorker() {

		authenticate("handyWorker1");

		// Application application = applicationService.findOne(15974);
		Application application = (Application) applicationService.findAll()
				.toArray()[3];
		Application result;

		// HandyWorker 1 -> 15726
		// Application 4 -> 15974

		application.setHandyWorkerComment("Comentario trabajador modificado");

		result = applicationService.save(application);

		Assert.isTrue(applicationService.findAll().contains(result));

		unauthenticate();
	}

	@Test
	public void testUpdateCustomer() {

		authenticate("customer1");

		// Application application = applicationService.findOne(15982);
		Application application = (Application) applicationService.findAll()
				.toArray()[11];
		Application result;

		// Customer 1 -> 15721
		// FixUpTask 8 -> 15953
		// Application 12 -> 15982

		application.setCustomerComment("Comentario cliente modificado");

		result = applicationService.save(application);

		Assert.isTrue(applicationService.findAll().contains(result));

		unauthenticate();
	}

	@Test
	public void testDelete() {

		authenticate("handyWorker2");

		// HandyWorker 2 -> 15727
		// Application 5 -> 15975

		// Application application = applicationService.findOne(15975);
		Application application = (Application) applicationService.findAll()
				.toArray()[4];
		applicationService.delete(application);

		Assert.isTrue(!applicationService.findAll().contains(application));

		unauthenticate();
	}

	@Test
	public void testApplicationByHandyWorker() {

		Collection<Application> res = new ArrayList<Application>();

		// HandyWorker handyWorker = handyWorkerService.findOne(15726);

		HandyWorker handyWorker = (HandyWorker) handyWorkerService.findAll()
				.toArray()[0];

		res = applicationService.applicationByHandyWorker(handyWorker.getId());

		Assert.isTrue(res.size() == 4);
	}

	@Test
	public void testChangeStatus() {
		authenticate("customer1"); // 15721

		// String a = "PENDING";
		// String b = "ACCEPTED";
		String c = "REJECTED";

		// Application application = applicationService.findOne(15982);
		Application application = (Application) applicationService.findAll()
				.toArray()[11];
		applicationService.changeStatus(application, c);
		//TODO:Falla en el sendSystemMessage no puedo hacer nada por ahora 

		unauthenticate();
	}
}
