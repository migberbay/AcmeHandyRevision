package controllers;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ActorService;
import services.BoxService;
import services.MessageService;
import domain.Actor;
import domain.Box;
import domain.Message;

@Controller
@RequestMapping("message/")
public class MessageController extends AbstractController {
	
	//Services
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private BoxService boxService;
	
	@Autowired
	private ActorService actorService;

	// Constructors -----------------------------------------------------------

	public MessageController() {
		super();
	}
	
	//Listing-----------------------------------------------------------


	@RequestMapping(value="/list" , method=RequestMethod.GET)
	public ModelAndView list(@RequestParam int boxId) {
		ModelAndView res;
		
		if (!LoginService.getPrincipal().equals(boxService.findOne(boxId).getActor().getUserAccount())) {
			res = new ModelAndView("error/access");
		}else{
		
		Collection<Box> boxes = boxService.findByActorId(actorService.getByUserAccountId(LoginService.getPrincipal()).getId());
		Collection<Message> messages = new ArrayList<>();
		for (Integer i : boxService.findOne(boxId).getMessages()) {
			messages.add(messageService.findOne(i));
		}

		res = new ModelAndView("message/list");
		res.addObject("messages", messages);
		res.addObject("boxes", boxes);
		}
		return res;
	}
	
	
	//Create-----------------------------------------------------------
	@RequestMapping(value="/create" , method=RequestMethod.GET)
	public ModelAndView create(){
		ModelAndView res;
		Message message = messageService.create(actorService.getByUserAccountId(LoginService.getPrincipal()));
		
		res = this.createEditModelAndView(message,false);
		return res;
		
	}
	
	@RequestMapping(value="/createBroadcast" , method=RequestMethod.GET)
	public ModelAndView createBroadcast(){
		ModelAndView res;
		if(!LoginService.hasRole("ADMIN")){
			res = new ModelAndView("error/access");
		}else{
		Message message = messageService.create(actorService.getByUserAccountId(LoginService.getPrincipal()));
		message.setRecipients(actorService.findAll());
		res = this.createEditModelAndView(message,true);
		}
		return res;
		
	}
	
	//Save-------------------------------------------------------------
	
	@RequestMapping(value="/edit", method=RequestMethod.POST, params="save")
	public ModelAndView save(@Valid Message message, BindingResult binding){
		ModelAndView res;
		
		if(binding.hasErrors()){
			System.out.println(binding.toString());
			res = createEditModelAndView(message,false);
		}else{
			try {
				
				Message saved = messageService.save(message);
				messageService.addMesageToBoxes(saved);
				res = new ModelAndView("redirect:/box/list.do");
			} catch (Throwable e) {
				res = createEditModelAndView(message, false, "message.commit.error");
				for (StackTraceElement st : e.getStackTrace()) {
					System.out.println(st);
				}
				
			}
		}
		return res;
	}
	
	
	//Delete-----------------------------------------------------------
	@RequestMapping(value="/delete", method=RequestMethod.GET)
	public ModelAndView delete(@RequestParam int messageId){
		ModelAndView res;
		Message message = messageService.findOne(messageId);
		Collection<Box> boxes = boxService.findByActorId(actorService.getByUserAccountId(LoginService.getPrincipal()).getId());
		boolean hasMessage = false;
		for (Box b : boxes) {
			if(b.getMessages().contains(messageId)){hasMessage=true;}
		}
		if(!hasMessage){
			res = new ModelAndView("error/access");
		}else{
			try {
				messageService.delete(message);
				res = new ModelAndView("redirect:/box/list.do");
			} catch (Throwable e) {
				res = createEditModelAndView(message,false, "message.commit.error");
			}
		}
		return res;
	}
	
	//Move message ---------------------------------------------------
	
	@RequestMapping(value="/moveToBox", method=RequestMethod.GET)
	public ModelAndView moveToBox(@RequestParam int messageId, @RequestParam int boxId, @RequestParam int newBoxId){
		ModelAndView res;
		Message message = messageService.findOne(messageId);
		Box original =  boxService.findOne(boxId);
		Box destination = boxService.findOne(newBoxId);
		if(!original.getActor().getUserAccount().equals(LoginService.getPrincipal()) 
				|| !destination.getActor().getUserAccount().equals(LoginService.getPrincipal())){
			res = new ModelAndView("error/access");
		}else{
			try {
				Collection<Integer>originalMessages = original.getMessages();
				originalMessages.remove(message.getId());
				original.setMessages(originalMessages);
				boxService.save(original);
				
				Collection<Integer>newMessages = destination.getMessages();
				newMessages.add(message.getId());
				destination.setMessages(newMessages);
				boxService.save(destination);
				
				res = new ModelAndView("redirect:/box/list.do");
			} catch (Throwable e) {
				res = createEditModelAndView(message,false, "message.commit.error");
			}
		}
		return res;
	}
	
	
	//Helper methods---------------------------------------------------
	protected ModelAndView createEditModelAndView(Message message,boolean isBroadcast){
		ModelAndView res;
		res = createEditModelAndView(message,isBroadcast, null);
		return res;
	}
	protected ModelAndView createEditModelAndView(Message message,boolean isBroadcast, String messageCode){
		ModelAndView res;
		
		//aqui habria que contemplar si el mensaje esta siendo editado o creado
		// y añadir metodos en consecuencia, pero como los mensajes no pueden ser 
		//editados no es necesario
		
		res = new ModelAndView("message/edit");
		res.addObject("userMessage", message);
		res.addObject("actors",actorService.findAll());
		res.addObject("message", messageCode);
		res.addObject("isBroadcast",isBroadcast);
		
		return res;
	}
	
}