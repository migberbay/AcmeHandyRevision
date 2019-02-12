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

import domain.Actor;
import domain.Administrator;
import domain.Referee;

import security.Authority;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
public class AdministratorServiceTest extends AbstractTest {

	@Autowired
	private AdministratorService administratorService;

	@Autowired
	private CustomerService customerService;

	// @Autowired
	// private ActorService actorService;

	@Test
	public void testCreateAdministrator() {

		authenticate("admin");

		Administrator administrator = administratorService
				.createAdministrator();

		Authority authority = new Authority();
		authority.setAuthority("ADMIN");

		Assert.isTrue(administrator.getName() != "");
		Assert.isTrue(administrator.getSurname() != "");
		Assert.isTrue(administrator.getMiddleName() != "");
		Assert.isTrue(administrator.getAddress() != "");
		Assert.isTrue(administrator.getUserAccount().getAuthorities()
				.contains(authority));
		Assert.isTrue(administrator.getSocialProfiles().isEmpty());

		unauthenticate();

	}

	@Test
	public void testCreateReferee() {

		authenticate("admin");

		Referee referee = administratorService.createReferee();

		Authority authority = new Authority();
		authority.setAuthority("REFEREE");

		Assert.isTrue(referee.getName() != "");
		Assert.isTrue(referee.getSurname() != "");
		Assert.isTrue(referee.getMiddleName() != "");
		Assert.isTrue(referee.getAddress() != "");
		Assert.isTrue(referee.getUserAccount().getAuthorities()
				.contains(authority));
		Assert.isTrue(referee.getSocialProfiles().isEmpty());

		unauthenticate();
	}

	@Test
	public void testFindSuspicious() {

		Collection<Actor> result = new ArrayList<Actor>();

		result = administratorService.findSuspicious();

		Assert.isTrue(result.size() == 2);
	}

	@Test
	public void testBanActor() {

		authenticate("admin");

		// Actor actor = actorService.findOne(15724); // Customer 4

		Actor actor = (Actor) customerService.findAll().toArray()[3];

		actor.setIsSuspicious(true);
		actor.setIsBanned(false);
		
		System.out.println("Actor:" + actor.getUserAccount().getUsername());

		administratorService.ban(actor);

		Assert.isTrue(actor.getIsBanned().equals(true));

		unauthenticate();
	}

	@Test
	public void testUnbanActor() {
		authenticate("admin");

		// Actor actor = actorService.findOne(15723); // Customer 3

		Actor actor = (Actor) customerService.findAll().toArray()[2];

		// System.out.println("Actor" + actor);

		administratorService.unBan(actor);

		Assert.isTrue(actor.getIsBanned().equals(false));

		unauthenticate();
	}
}
