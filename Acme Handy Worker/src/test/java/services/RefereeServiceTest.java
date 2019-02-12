package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import security.UserAccount;
import utilities.AbstractTest;
import domain.Referee;
import domain.SocialProfile;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/datasource.xml",
										"classpath:spring/config/packages.xml"})
@Transactional
public class RefereeServiceTest extends AbstractTest {
	
	// Service under test ---------------------------------------------------------

	@Autowired
	private RefereeService refereeService;
	
	@Autowired
	private SocialProfileService socialProfileService;
		
	// Tests ----------------------------------------------------------------------

	
	// CREATE ---------------------------------------------------------------------
	
	@Test
	public void testCreateReferee(){
		Referee referee;
		super.authenticate("admin");
		referee = refereeService.create();	
		Assert.isNull(referee.getAddress());
		Assert.isNull(referee.getEmail());
		Assert.isNull(referee.getIsBanned());
		Assert.isNull(referee.getIsSuspicious());
		Assert.isNull(referee.getMiddleName());
		Assert.isNull(referee.getName());
		Assert.isNull(referee.getPhone());
		Assert.isNull(referee.getPhoto());
		Assert.isNull(referee.getSurname());
		Assert.isTrue(referee.getSocialProfiles().isEmpty());
		Assert.notNull(referee.getUserAccount());
							
		super.authenticate(null);
	}

	
	// SAVE -----------------------------------------------------------------------
	
	@Test 
	public void testSaveReferee(){
		Referee referee,saved;
		Collection<Referee> referees;
		super.authenticate("admin");						// Nos autenticamos como Referee
		referee = refereeService.create();						// Creamos la nota
		
		referee.setName("Juan");
		referee.setSurname("Serna");
		referee.setEmail("juaparser@gmail.com");
		referee.setPhone("678534953");
		referee.setAddress("Calle de la Chincheta nº10");
		referee.setMiddleName("Parra");
		referee.setPhoto("http://www.linkedIn.com");

		SocialProfile savedpr;
		SocialProfile socialProfile = socialProfileService.create();
		socialProfile.setLink("http://www.twitter.com/Juan");
		socialProfile.setNick("juaparser");
		socialProfile.setSocialNetwork("Twitter");
		savedpr = socialProfileService.save(socialProfile);
		referee.getSocialProfiles().add(savedpr);

		UserAccount userAccount = referee.getUserAccount();
		userAccount.setUsername("referee12");
		userAccount.setPassword("referee12");
		referee.setUserAccount(userAccount);

		saved = refereeService.save(referee);
		
		referees = refereeService.findAll();
		Assert.isTrue(referees.contains(saved));
		
		
		super.authenticate(null);
	}
	

	// UPDATE ---------------------------------------------------------------------

	@Test 
	public void testUpdateReferee(){
		Referee referee,saved;
		Collection<Referee> referees;
		super.authenticate("referee1");						
		referee = (Referee) refereeService.findAll().toArray()[0];
		referee.setName("Lucas");
		
		saved = refereeService.save(referee);
		
		referees = refereeService.findAll();						// Comprobamos que la nota se ha guardado correctamente en el archivo de notas
		Assert.isTrue(referees.contains(saved));

		super.authenticate(null);
	}
	
	// DELETE ---------------------------------------------------------------------

	

}