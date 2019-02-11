package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Actor;
import domain.Application;
import domain.Box;
import domain.Customer;
import domain.HandyWorker;
import domain.Message;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
public class MessageServiceTest extends AbstractTest {

	@Autowired
	private ApplicationService applicationService;

	@Autowired
	private AdministratorService administratorService;

	@Autowired
	private HandyWorkerService handyWorkerService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private MessageService messageService;

	// @Autowired
	// private ActorService actorService;

	@Autowired
	private BoxService boxService;

	@Test
	public void testCreate() {

		// Actor sender = actorService.findOne(15730); // HandyWorker5
		// Actor recipient = actorService.findOne(15720); // Admin1

		Actor sender = (Actor) handyWorkerService.findAll().toArray()[4];

		Actor recipient = (Actor) administratorService.findAll().toArray()[0];
		List<Actor> recipients =  new ArrayList<>();
		recipients.add(recipient);

		Message message = messageService.create(sender);
		message.setRecipients(recipients);

		// System.out.println("Sender" + message.getSender());
		// System.out.println("Recipient" + message.getRecipient());

		Assert.isTrue(message.getPriority() != "");
		Assert.isTrue(message.getTags().isEmpty());
		Assert.isTrue(message.getSender().equals(sender));
		Assert.isTrue(message.getRecipients().equals(recipients));
	}

	@Test
	public void testSave() {

		// Actor sender = actorService.findOne(15730); // HandyWorker5
		// Actor recipient = actorService.findOne(15720); // Admin1

		Actor sender = (Actor) handyWorkerService.findAll().toArray()[4];

		Actor recipient = (Actor) administratorService.findAll().toArray()[0];
		List<Actor> recipients =  new ArrayList<>();
		recipients.add(recipient);

		Message message = messageService.create(sender);
		message.setRecipients(recipients);

		// System.out.println("Sender" + message.getSender());
		// System.out.println("Recipient" + message.getRecipient());

		Message result;

		message.setBody("Cuerpo del mensaje");
		message.setSubject("Asunto del mensaje");
		message.getTags().add("tag");

		result = messageService.save(message);
		Assert.isTrue(messageService.findAll().contains(result));
	}

	// Box 7
	// Box 11

	@Test
	public void testDelete() {

		authenticate("handyWorker1");

		// Message 1 -> 15943; Sender -> 15721 Customer 1; Recipient -> 15722
		// Customer 2
		// Message 2 -> 15944; Sender -> 15722 Customer 2; Recipient -> 15721
		// Customer 1
		// Message 3 -> 15945; Sender -> 15723 Customer 3; Recipient -> 15726
		// HandyWorker 1

		// HandyWorker 1; Box 25 (Spam Box) -> 15907
		// HandyWorker 1; Box 26 (In Box) -> 15908
		// HandyWorker 1; Box 27 (Out Box) -> 15909
		// HandyWorker 1; Box 28 (Trash Box) -> 15910

		// Actor actor = actorService.findOne(15726);

		// Box box = boxService.findOne(15907);
		// Box box2 = boxService.findOne(15908);
		// Box box3 = boxService.findOne(15909);
		// Box box4 = boxService.findOne(15910);

		Box box = (Box) boxService.findAll().toArray()[24];
		Box box2 = (Box) boxService.findAll().toArray()[25];
		Box box3 = (Box) boxService.findAll().toArray()[26];
		Box box4 = (Box) boxService.findAll().toArray()[27];

		// Collection<Box> boxes = boxService.findAll();

		// Message message = messageService.findOne(15945); // Message 3
		// Message message = (Message) messageService.findAll().toArray()[2];

		Actor sender = (Actor) customerService.findAll().toArray()[2];

		Actor recipient = (Actor) handyWorkerService.findAll().toArray()[0];
		List<Actor> recipients =  new ArrayList<>();
		recipients.add(recipient);

		Message message = messageService.create(sender);
		message.setRecipients(recipients);

		// Message result = messageService.save(message);

		Message result;

		message.setBody("Cuerpo del mensaje");
		message.setSubject("Asunto del mensaje");
		message.getTags().add("tag");

		result = messageService.save(message);

		Assert.isTrue(messageService.findAll().contains(result));

		// messageService.addMesageToBoxes(result);

		// Collection<Message> messages = box2.getMessages();
		Collection<Message> messages = box4.getMessages();

		messages.add(result);

		// box2.setMessages(messages);
		box4.setMessages(messages);

		// box2.getMessages().add(result);
		// box4.getMessages().add(result);

		// Assert.isTrue(box2.getMessages().contains(result));
		Assert.isTrue(box4.getMessages().contains(result));

		messageService.delete(result);

		Assert.isTrue((!box.getMessages().contains(result)
				&& !box2.getMessages().contains(result)
				&& !box3.getMessages().contains(result) && box4.getMessages()
				.contains(result))
				|| (!box.getMessages().contains(result)
						&& !box2.getMessages().contains(result)
						&& !box3.getMessages().contains(result) && !box4
						.getMessages().contains(result)));

		// Assert.isTrue(box4.getMessages().contains(result));
		Assert.isTrue(!box4.getMessages().contains(result));

		unauthenticate();
	}

	@Test
	public void testDeleteOfSystemBox() {

		authenticate("handyWorker1");

		Box box = (Box) boxService.findAll().toArray()[24];
		Box box2 = (Box) boxService.findAll().toArray()[25];
		Box box3 = (Box) boxService.findAll().toArray()[26];
		Box box4 = (Box) boxService.findAll().toArray()[27];

		Actor sender = (Actor) customerService.findAll().toArray()[2];

		Actor recipient = (Actor) handyWorkerService.findAll().toArray()[0];
		List<Actor> recipients =  new ArrayList<>();
		recipients.add(recipient);

		Message message = messageService.create(sender);
		message.setRecipients(recipients);

		Message result;

		message.setBody("Cuerpo del mensaje");
		message.setSubject("Asunto del mensaje");
		message.getTags().add("tag");

		result = messageService.save(message);

		Assert.isTrue(messageService.findAll().contains(result));

		Collection<Message> messages = box2.getMessages();

		messages.add(result);

		box2.setMessages(messages);

		Assert.isTrue(box2.getMessages().contains(result));

		messageService.delete(result);

		Assert.isTrue((!box.getMessages().contains(result)
				&& !box2.getMessages().contains(result)
				&& !box3.getMessages().contains(result) && box4.getMessages()
				.contains(result))
				|| (!box.getMessages().contains(result)
						&& !box2.getMessages().contains(result)
						&& !box3.getMessages().contains(result) && !box4
						.getMessages().contains(result)));

		Assert.isTrue(box4.getMessages().contains(result));

		unauthenticate();
	}

	// Application 5 -> 15975; HandyWorker 2 -> 15727;
	// FixUpTask 4 -> 15949; Customer 4 -> 15724

	// HandyWorker 2; Box 29 (Spam Box) -> 15911
	// HandyWorker 2; Box 30 (In Box) -> 15912
	// HandyWorker 2; Box 31 (Out Box) -> 15913
	// HandyWorker 2; Box 32 (Trash Box) -> 15914

	// Customer 4; Box 13 (Spam Box) -> 15895
	// Customer 4; Box 14 (In Box) -> 15896
	// Customer 4; Box 15 (Out Box) -> 15897
	// Customer 4; Box 16 (Trash Box) -> 15898

	@Test
	public void testSystemMessage() {

		// Application application = applicationService.findOne(15975);
		Application application = (Application) applicationService.findAll()
				.toArray()[4];

		Customer customer = application.getFixUpTask().getCustomer(); // Customer4
		HandyWorker handyWorker = application.getHandyWorker(); // HandyWorker 2

		Customer customer2 = (Customer) customerService.findAll().toArray()[3];
		HandyWorker handyWorker2 = (HandyWorker) handyWorkerService.findAll()
				.toArray()[1];

		// Assert.isTrue(customer.getId() == 15724);
		// Assert.isTrue(handyWorker.getId() == 15727);

		Assert.isTrue(customer.equals(customer2));
		Assert.isTrue(handyWorker.equals(handyWorker2));

		messageService.sendSystemMessages(application);

		Box customerBox = boxService.findOne(15896); // Box 14
		Box handyWorkerBox = boxService.findOne(15912); // Box 30
		Box administratorBox = boxService.findOne(15905); // Box 23

		// Box customerBox = (Box) boxService.findAll().toArray()[13];
		// Box handyWorkerBox = (Box) boxService.findAll().toArray()[29];
		// Box administratorBox = (Box) boxService.findAll().toArray()[22];

		Assert.isTrue(customerBox.getMessages().size() == 1);
		Assert.isTrue(handyWorkerBox.getMessages().size() == 1);
		Assert.isTrue(administratorBox.getMessages().size() == 2);
	}

	// Administrator 1; Box 21 (Spam Box) -> 15903
	// Administrator 1; Box 22 (In Box) -> 15904
	// Administrator 1; Box 23 (Out Box) -> 15905
	// Administrator 1; Box 24 (Trash Box) -> 15906

	// Customer 5; Box 17 (Spam Box) -> 15899
	// Customer 5; Box 18 (In Box) -> 15900
	// Customer 5; Box 19 (Out Box) -> 15901
	// Customer 5; Box 20 (Trash Box) -> 15902

	@Test
	public void testMessageToBox() {

		// authenticate("admin1");
		// authenticate("customer5");

		// Actor sender = actorService.findOne(15725); // Customer5
		// Actor recipient = actorService.findOne(15720); // Admin1

		Actor sender = (Actor) customerService.findAll().toArray()[4];

		Actor recipient = (Actor) administratorService.findAll().toArray()[0];
		List<Actor> recipients =  new ArrayList<>();
		recipients.add(recipient);

		// Box senderBox = boxService.findOne(15901); // Box 19
		// Box recipientBox = boxService.findOne(15904); // Box 22

		Box senderBox = (Box) boxService.findAll().toArray()[18];

		Box recipientBox = (Box) boxService.findAll().toArray()[21];

		Message message = messageService.create(sender);
		message.setRecipients(recipients);

		messageService.addMesageToBoxes(message);

		Assert.isTrue(senderBox.getMessages().contains(message));
		Assert.isTrue(recipientBox.getMessages().contains(message));

		// unauthenticate();
		// unauthenticate();
	}
}
