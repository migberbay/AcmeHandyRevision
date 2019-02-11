package services;

import java.util.Collection;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import security.LoginService;
import security.UserAccount;
import utilities.AbstractTest;
import domain.Finder;
import domain.HandyWorker;
import domain.SocialProfile;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})

@Transactional
public class FinderServiceTest extends AbstractTest {

	// Service under test --------------------

	@Autowired
	private FinderService	finderService;
	
	@Autowired
	private HandyWorkerService handyWorkerService;
	
	@Autowired
	private SocialProfileService socialProfileService;


	// Tests --------------------

	@Test
	public void testCreate() {
		HandyWorker handyWorker;
		super.authenticate("admin1");
		handyWorker = handyWorkerService.create();						
		
		handyWorker.setName("Francisco");
		handyWorker.setSurname("Cordero");
		handyWorker.setEmail("franky95@gmail.com");
		handyWorker.setPhone("678534953");
		handyWorker.setAddress("Calle San Jacinto Nº10");
		handyWorker.setMiddleName("Fran");
		handyWorker.setPhoto("http://www.linkedIn.com");

		SocialProfile savedpr;
		SocialProfile socialProfile = socialProfileService.create();
		socialProfile.setLink("http://www.twitter.com/Fran");
		socialProfile.setNick("FranC");
		socialProfile.setSocialNetwork("Twitter");
		savedpr = socialProfileService.save(socialProfile);
		handyWorker.getSocialProfiles().add(savedpr);

		UserAccount userAccount = handyWorker.getUserAccount();
		userAccount.setUsername("handyWorker12");
		userAccount.setPassword("handyWorker12");
		handyWorker.setUserAccount(userAccount);

		handyWorkerService.save(handyWorker);
		this.authenticate(null);
		
		this.authenticate("handyworker12");
		Finder finder;
		finder = this.finderService.create();
		finder.setKeyword("xxx");
		finder.setMaxPrice(100.0);
		finder.setMinPrice(50.0);
		final Date fecha = new Date(01/01/2018);
		finder.setStartDate(fecha);
		finder.setEndDate(fecha);
		finder.setCategory(null);
//		final Collection<FixUpTask> fixUpTasks = new ArrayList<FixUpTask>();
//		finder.setFixUpTasks(fixUpTasks);
		Finder savedf = finderService.save(finder);
		Collection<Finder> finders = finderService.findAll();
		Assert.isTrue(finders.contains(savedf), "----- Fallo metodo create -----");
	}

	@Test
	public void testFindAll() {
		Collection<Finder> finders = this.finderService.findAll();
		Assert.isTrue(finders.size() > 0, "----- Fallo metodo findAll -----");
	}

	@Test
	public void testSave() {
		this.authenticate("handyworker2");
		Finder finderPrueba = new Finder();
		Collection<Finder> finders = finderService.findAll();
		for(Finder fa: finders){
			if(fa.getHandyWorker().getUserAccount().equals(LoginService.getPrincipal())) finderPrueba=fa;
		}
		finderPrueba.setMaxPrice(150.0);
		Finder saved = this.finderService.save(finderPrueba);
		finders = this.finderService.findAll();
		Assert.isTrue(finders.contains(saved), "----- Fallo metodo save -----");
	}

	@Test
	public void testFindOne() {
		Finder f = (Finder) this.finderService.findAll().toArray()[0];
		Assert.isTrue(!f.equals(null), "----- Fallo metodo findOne -----");
	}

	@Test
	public void testDelete() {
		this.authenticate("handyworker3");
		final Finder finder = this.finderService.findOne(15786);
		this.finderService.delete(finder);
	}

}
