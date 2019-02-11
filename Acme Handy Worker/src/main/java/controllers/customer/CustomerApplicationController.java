/*
 * CustomerController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.customer;

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
import services.CustomerService;
import controllers.AbstractController;
import domain.Application;
import domain.CreditCard;

@Controller
@RequestMapping("customer/application/")
public class CustomerApplicationController extends AbstractController {
	
	// Services ---------------------------------------------------------------
	
	@Autowired
	private ApplicationService appService;
	
	@Autowired
	private CustomerService customerService;
	
	// Constructors -----------------------------------------------------------

	public CustomerApplicationController() {
		super();
	}

	// Listing ----------------------------------------------------------------		

	@RequestMapping(value="/list", method=RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Application> applications;
		
		int id = customerService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
		
		applications = appService.applicationByCustomer(id);

		result = new ModelAndView("application/list");
		result.addObject("applications",applications);
		result.addObject("requestURI","customer/application/list.do");

		return result;
	}
	
	// Edit ------------------------------------------------------------------
	
	@RequestMapping(value="/edit", method=RequestMethod.GET)
	public ModelAndView edit(@RequestParam int appId) {
		ModelAndView result;
		Application app;
		
		app = appService.findOne(appId);
		
		result = createEditModelAndView(app);
		
		return result;
	}
	
	// Save ------------------------------------------------------------------
	
	@RequestMapping(value="/edit", method=RequestMethod.POST, params="save")
	public ModelAndView save(@Valid Application app, BindingResult binding) {
		ModelAndView result;
		Application saved;
		if(binding.hasErrors()){
			result = createEditModelAndView(app);
		}else{
			try{
				saved = appService.save(app);
				if(saved.getStatus().equals("ACCEPTED")){
					
					result = new ModelAndView("redirect:/creditCard/create.do?appId=" + app.getId());
				}else{
					result = new ModelAndView("redirect:/customer/application/edit.do?appId=" + app.getId());
				}
				
			}catch(Throwable oops){
				result = createEditModelAndView(app,"application.commit.error");
			}
		}
		
		return result;
	}
	
	@RequestMapping(value="/edit", method=RequestMethod.POST, params="saveAll")
	public ModelAndView saveAll(@Valid Application app, BindingResult binding) {
		ModelAndView result;
		if(binding.hasErrors()){
			result = createEditModelAndView(app);
		}else{
			try{
				appService.save(app);
				result = new ModelAndView("redirect:/customer/application/list.do");
			}catch(Throwable oops){
				result = createEditModelAndView(app,"application.commit.error");
			}
		}
		
		return result;
	}
	
	// Cancel -----------------------------------------------------------------
	
	@RequestMapping(value="/edit", method=RequestMethod.POST, params="cancel")
	public ModelAndView cancel(@Valid Application app) {
		ModelAndView result;
		app.setStatus("PENDING");
		
			try{
				appService.save(app);
				result = new ModelAndView("redirect:/customer/application/list.do");
			}catch(Throwable oops){
				result = createEditModelAndView(app,"application.commit.error");
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
		System.out.println(app.getStatus());
		if(app.getStatus().equals("REJECTED")){
			
			result = new ModelAndView("application/comment");
			
			result.addObject("application",app);
			result.addObject("menssage",msgCode);
		}else{
		
			result = new ModelAndView("application/edit");
		
			result.addObject("application",app);
			result.addObject("creditCard",cc);
			result.addObject("menssage",msgCode);
		}
		return result;
	}
	
}
