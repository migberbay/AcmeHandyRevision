/*
 * CustomerController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.handyworker;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ApplicationService;
import services.FixUpTaskService;
import services.HandyWorkerService;
import controllers.AbstractController;
import domain.Application;
import domain.CreditCard;

@Controller
@RequestMapping("handyWorker/application/")
public class ApplicationHandyWorkerController extends AbstractController {
	
	// Services ---------------------------------------------------------------
	
	@Autowired
	private ApplicationService appService;
	
	@Autowired
	private HandyWorkerService hwService;
	
	@Autowired
	private FixUpTaskService fixUpTaskService;
	
	// Constructors -----------------------------------------------------------

	public ApplicationHandyWorkerController() {
		super();
	}

	// Listing ----------------------------------------------------------------		

	@RequestMapping(value="/list", method=RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Application> applications;
		
		int id = hwService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
		
		applications = appService.applicationByHandyWorker(id);

		result = new ModelAndView("application/list");
		result.addObject("applications",applications);
		result.addObject("requestURI","handyWorker/application/list.do");

		return result;
	}
	
	// Show ------------------------------------------------------------------

	@RequestMapping(value="/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int appId){
		ModelAndView res;
		Application app;
		
		app = appService.findOne(appId);
		
		res = new ModelAndView("application/show");
		res.addObject("application", app);
		res.addObject("requestURI", "handyWorker/application/show.do");
		
		return res; 
	}
	
	// Create ----------------------------------------------------------------		

	@RequestMapping(value="/apply", method=RequestMethod.GET)
	public ModelAndView create(@RequestParam final int fixUpTaskId) {
		ModelAndView result;
		Application application;
		
		application = appService.create();
		application.setFixUpTask(fixUpTaskService.findOne(fixUpTaskId));
		
		result = createEditModelAndView(application);

		return result;
	}
	
	// Save ------------------------------------------------------------------
	
	@RequestMapping(value="/edit", method=RequestMethod.POST, params="save")
	public ModelAndView saveHandyWorker(@Valid Application app, BindingResult binding) {
		ModelAndView result;

		if(binding.hasErrors()){
			result = createEditModelAndView(app);
		}else{
			try{
				appService.save(app);			
				result = new ModelAndView("redirect:list.do");
			}catch(Throwable oops){
				result = createEditModelAndView(app,"application.commit.error");
			}
		}
		
		return result;
	}
	
	// Ancillary methods ------------------------------------------------------
	
	protected ModelAndView createEditModelAndView(Application application){
		ModelAndView result;
		
		result = createEditModelAndView(application,null);
		
		return result;
	}
	
	protected ModelAndView createEditModelAndView(Application app, String msgCode){
		ModelAndView result;
		CreditCard cc;
		
		cc = new CreditCard();
		result = new ModelAndView("application/edit");
	
		result.addObject("application",app);
		result.addObject("creditCard",cc);
		result.addObject("menssage",msgCode);
		
		return result;
	}
}
