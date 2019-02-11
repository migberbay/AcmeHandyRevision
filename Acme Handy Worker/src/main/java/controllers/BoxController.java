package controllers;

import java.util.ArrayList;
import java.util.List;

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
import domain.Actor;
import domain.Box;

@Controller
@RequestMapping("box/")
public class BoxController extends AbstractController {
	
	//Services
	
	@Autowired
	private BoxService boxService;
	
	@Autowired
	private ActorService actorService;

	// Constructors -----------------------------------------------------------

	public BoxController() {
		super();
	}
	
	//Listing-----------------------------------------------------------


	@RequestMapping(value="/list" , method=RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView res;
		List<Box> boxes = new ArrayList<>();
		
		Actor logged = actorService.getByUserAccountId(LoginService.getPrincipal());
		
		System.out.println(logged);
		for (Box b : boxService.findAll()) {
			if(b.getActor().equals(logged)){
				boxes.add(b);
			}
		}

		res = new ModelAndView("box/list");
		res.addObject("boxes", boxes);

		return res;
	}
	
	
	//Create-----------------------------------------------------------
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public ModelAndView create(){
		ModelAndView res;
		Box box = boxService.create(actorService.getByUserAccountId(LoginService.getPrincipal()));
		
		res = this.createEditModelAndView(box);
		return res;
		
	}
	
	//Edit-------------------------------------------------------------
	@RequestMapping(value="/edit", method=RequestMethod.GET)
	public ModelAndView edit(@RequestParam int boxId){
		ModelAndView res;
		Box box = boxService.findOne(boxId);
		
		Assert.notNull(box);
		res = this.createEditModelAndView(box);
		return res;
	}
	
	//Save-------------------------------------------------------------	
	@RequestMapping(value="/edit", method=RequestMethod.POST, params="save")
	public ModelAndView save(@Valid Box box, BindingResult binding){
		ModelAndView res;
		
		
		if(binding.hasErrors()){
			res = createEditModelAndView(box);
		}else{
			try {
				boxService.save(box);
				res = new ModelAndView("redirect:list.do");
			} catch (Throwable e) {
				res = createEditModelAndView(box, "message.commit.error");
				
				//TODO: añadir el commit error al box.properties de message.
			}
		}
		return res;
	}
	
	
	//Delete-----------------------------------------------------------
	@RequestMapping(value="/delete", method=RequestMethod.GET)
	public ModelAndView delete(@RequestParam int boxId){
		ModelAndView res;
		Box box = boxService.findOne(boxId);

			try {
				boxService.delete(box);
				res = new ModelAndView("redirect:list.do");
			} catch (Throwable e) {
				res = createEditModelAndView(box, "message.commit.error");
			}
		return res;
	}
	
	
	//Helper methods---------------------------------------------------
	protected ModelAndView createEditModelAndView(Box box){
		ModelAndView res;
		res = createEditModelAndView(box, null);
		return res;
	}
	protected ModelAndView createEditModelAndView(Box box, String messageCode){
		ModelAndView res;
		
		res = new ModelAndView("box/edit");
		res.addObject("box", box);
		res.addObject("errorMessage", messageCode);
		
		return res;
	}
	
}