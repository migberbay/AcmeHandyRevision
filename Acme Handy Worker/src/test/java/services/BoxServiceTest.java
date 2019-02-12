package services;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Actor;
import domain.Box;
import domain.Message;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
public class BoxServiceTest extends AbstractTest {

	@Autowired
	private BoxService boxService;

	// @Autowired
	// private ActorService actorService;

	@Autowired
	private AdministratorService administratorService;

	@Autowired
	private HandyWorkerService handyWorkerService;

	@Autowired
	private MessageService messageService;

	@Test
	public void testCreate() {

		authenticate("admin"); // 15720

		// Actor actor = actorService.findOne(15720);

		// Actor actor = (Actor) actorService.findAll().toArray()[0];

		Actor actor = (Actor) administratorService.findAll().toArray()[0];

		Box box = boxService.create(actor);

		Assert.isTrue(box.getActor().equals(actor));
		Assert.isTrue(box.getSystemBox().equals(false));
		Assert.isTrue(box.getMessages().isEmpty());
		Assert.isTrue(box.getName() != "");

		unauthenticate();
	}

	@Test
	public void testSave() {

		authenticate("admin"); // 15384

		// Actor actor = actorService.findOne(15720);

		// Actor actor = (Actor) actorService.findAll().toArray()[0];

		Actor actor = (Actor) administratorService.findAll().toArray()[0];

		Box result;
		Box box = boxService.create(actor);

		box.setName("Name box");

		result = boxService.save(box);

		Assert.isTrue(boxService.findByActorId(15589).contains(result));
		Assert.isTrue(boxService.findAll().contains(result));
	}

	@Test
	public void testDelete() {
		authenticate("admin"); // 15384

		// Actor actor = actorService.findOne(15720);

		// Actor actor = (Actor) actorService.findAll().toArray()[0];

		Actor actor = (Actor) administratorService.findAll().toArray()[0];

		Box result;
		Box box = boxService.create(actor);

		box.setName("Name box");

		result = boxService.save(box);

		Assert.isTrue(boxService.findByActorId(15589).contains(result));
		Assert.isTrue(boxService.findAll().contains(result));

		boxService.delete(result);

		Assert.isTrue(!boxService.findByActorId(15589).contains(result));
		Assert.isTrue(!boxService.findAll().contains(result));
	}

	@Test
	public void testCreateUserBox() {

		authenticate("admin"); // 15720

		// Actor actor = actorService.findOne(15720);

		// Actor actor = (Actor) actorService.findAll().toArray()[0];

		Actor actor = (Actor) administratorService.findAll().toArray()[0];

		Box box = boxService.createUserBox(actor);

		Assert.isTrue(box.getName().equals(actor.getName()));

		unauthenticate();

	}

	@Test
	public void testMessageToBox() {

		authenticate("admin");

		// Actor sender = actorService.findOne(15730); // HandyWorker5
		// Actor recipient = actorService.findOne(15720); // Admin1

		Actor sender = (Actor) handyWorkerService.findAll().toArray()[4];

		Actor recipient = (Actor) administratorService.findAll().toArray()[0];
		List<Actor> recipients =  new ArrayList<>();
		recipients.add(recipient);

		Box box = boxService.create(recipient);
		box.setName("test");
		Message message = messageService.create(sender);
		message.setRecipients(recipients);

		boxService.addMessageToBox(box, message);

		Assert.isTrue(box.getMessages().contains(message));

		unauthenticate();
	}
}
