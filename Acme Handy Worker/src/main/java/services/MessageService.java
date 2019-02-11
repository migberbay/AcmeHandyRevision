package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.MessageRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Administrator;
import domain.Application;
import domain.Box;
import domain.Configuration;
import domain.Message;
import domain.Word;

@Service
@Transactional
public class MessageService {

	// Managed Repository -----
	@Autowired
	private MessageRepository messageRepository;

	// Supporting Services -----

	@Autowired
	private AdministratorService adminService;

	@Autowired
	private BoxService boxService;
	
	@Autowired
	private ConfigurationService configurationService;

	// Simple CRUD methods -----
	public Message create(Actor sender) {

		Message message = new Message();

		message.setSender(sender);
		message.setPriority("NEUTRAL");
		message.setFlagSpam(false);
		message.setTags(new ArrayList<String>());
		message.setMoment(new Date (System.currentTimeMillis()-1000));

		return message;
	}

	public Collection<Message> findAll() {
		return messageRepository.findAll();
	}

	public Message findOne(int Id) {
		return messageRepository.findOne(Id);
	}

	public Message save(Message message) {

		Message result;

		Date moment = new Date(System.currentTimeMillis() - 1000);

		message.setMoment(moment);

		result = messageRepository.saveAndFlush(message);
		return result;
	}

	public void delete(Message message) {

		// El mensaje se movera a la trashbox, si el mensaje ya estaba en la
		// trashbox se elimina del sistema.

		UserAccount userAccount = LoginService.getPrincipal();
		
		//añadimos todas las boxes de los actores que tienen el message a allActorBoxes
		Set<Box> allActorBoxes = new HashSet<>();

		Collection<Actor> recipients = message.getRecipients();
		for (Actor a : recipients) {
			allActorBoxes.addAll(boxService.findByActorId(a.getId()));
		}
		Actor sender = message.getSender();
		allActorBoxes.addAll(boxService.findByActorId(sender.getId()));
		
		
		//Vemos que actor de los que tiene el message es el que esta logeado.
		Actor logged = null;
		
		for (Actor recipient : recipients) {
			if (recipient.getUserAccount().equals(userAccount)) {
				logged = recipient;
				break;
			}
		}
		
		if (sender.getUserAccount().equals(userAccount)) {
			logged = sender;
		}

		//localizamos la trashbox y separamos los otros boxes en otherboxes para el actor logeado.
		Box trash = null;
		Collection<Box> otherboxes = new ArrayList<Box>();

		for (Box box : boxService.findByActorId(logged.getId())) {
			if (box.getName().equals("Trash Box"))
				trash = box;
			else {
				otherboxes.add(box);
			}
		}

		//comprobar si esta en trashbox
		if (trash.getMessages().contains(message.getId())) {
			Collection<Integer> aux = trash.getMessages();
			aux.remove(message.getId());
			trash.setMessages(aux);
			allActorBoxes.remove(trash);
			
			//comprobamos si el mensaje esta en alguna otra box.
			boolean isInOtherBox = false;
			for (Box b : allActorBoxes) {
				if(b.getMessages().contains(message.getId())){
					isInOtherBox = true;
					break;
				}
			}
			if(!isInOtherBox){
				messageRepository.delete(message);
			}
			
			boxService.save(trash);
		} else {
			for (Box b : otherboxes) {
				if (b.getMessages().contains(message.getId())) {
					Collection<Integer> aux = b.getMessages();
					aux.remove(message.getId());
					b.setMessages(aux);
					
					Collection<Integer> t = trash.getMessages();
					t.add(message.getId());
					trash.setMessages(t);

					boxService.save(trash);
					boxService.save(b);
				}
			}
		}
	}

	// Other business methods -----

	public void sendSystemMessages(Application application) {

		Administrator sender = (Administrator) adminService.findAll().toArray()[0];

		Actor handyWorker = application.getHandyWorker();
		Actor customer = application.getFixUpTask().getCustomer();
		
		List<Actor> recipients = new ArrayList<>();
		recipients.add(handyWorker);
		recipients.add(customer);

		Message message = this.create(sender);
		message.setRecipients(recipients);
		
		message.setSubject("The status of application: "+ application.getFixUpTask().getDescription()+ " has changed \n"+
		"El estado de la peticion: "+ application.getFixUpTask().getDescription()+" ha cambiado.");

		message.setBody("The status of the fix-up Task described as: \n"
				+ application.getFixUpTask().getDescription()
				+ "\n has changed you shoud revise it in the system. \n\n" +

				"El estado de la tarea de arreglo descrita como:\n"
				+ application.getFixUpTask().getDescription()+ "\n"+
				 "ha cambiado deberia revisar los cambios en el sistema.");

		Message saved = this.save(message);

		Box boxhw = boxService.findByActorAndName(handyWorker, "In Box");
		Box boxcust = boxService.findByActorAndName(customer, "In Box");
		Box boxadmin = boxService.findByActorAndName(sender, "Out Box");
		
		boxService.addMessageToBox(boxhw, saved);
		boxService.addMessageToBox(boxcust, saved);
		boxService.addMessageToBox(boxadmin, saved);

	}

	public void addMesageToBoxes(Message message){
		Collection<Box> boxes = new ArrayList<Box>();
		Box outBox = boxService.findByActorAndName(message.getSender(),"Out Box");

		boxes.add(outBox);
		if (findSpamWords(message.getBody()+message.getSubject())){

			for (Actor actor: message.getRecipients()){
				Box spamBox = boxService.findByActorAndName(actor,"Spam Box");
				boxes.add(spamBox);

			}
			message.getSender().setIsSuspicious(true);
			
		}else{
			for (Actor actor: message.getRecipients()){
				Box inBox = boxService.findByActorAndName(actor,"In Box");
				boxes.add(inBox);
				
		}
	}

		for (Box box: boxes){
			Collection<Integer> messages = new ArrayList<>(box.getMessages());
			messages.add(message.getId());
			box.setMessages(messages);
			boxService.save(box);
		}
	}
	
	private boolean findSpamWords(String text){
		boolean res = false;
		Configuration c = (Configuration) configurationService.findAll().toArray()[0];
		for (Word w : c.getspamWords()) {
			if(text.toLowerCase().contains(w.getContent().toLowerCase())){
				res=true;
				break;
			}
		}
		return res;
	}

}