package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
import domain.Application;
import domain.CreditCard;

@Controller
@RequestMapping("creditCard/")
public class CreditCardController extends AbstractController{
	
	private int appId;
	
	@Autowired
	private ApplicationService appService;
	
	public CreditCardController(){
		super();
	}
	
	// Create ------------------------------------------------------------------------------------------	

	@RequestMapping(value="/create", method=RequestMethod.GET)
	public ModelAndView create(int appId) {
		ModelAndView result;
		CreditCard cc = new CreditCard();
		this.appId = appId;
		
		result = createEditModelAndView(cc);

		return result;
	}
	
	// Save --------------------------------------------------------------------------------------------
	
	@RequestMapping(value="/edit", method=RequestMethod.POST, params="save")
	public ModelAndView save(@Valid CreditCard cc, BindingResult binding) {
		ModelAndView result;
		Application app = appService.findOne(this.appId);
		app.setCreditCard(cc);
		
		if(binding.hasErrors()){
			result = createEditModelAndView(cc);
		}else{
			try{
				appService.save(app);
				result = new ModelAndView("redirect:/customer/application/list.do");
			}catch(Throwable oops){
				result = createEditModelAndView(cc,"creditCard.commit.error");
			}
		}
		
		return result;
	}
	
	// Cancel -------------------------------------------------------------------------------------------
	
	@RequestMapping(value="/edit", method=RequestMethod.POST, params="cancel")
	public ModelAndView cancel() {
		ModelAndView result;
		Application app = appService.findOne(this.appId);
		app.setStatus("PENDING");
		
		appService.save(app);
		result = new ModelAndView("redirect:/customer/application/list.do");
		
		return result;
	}

	//Ancillary Methods ---------------------------------------------------------------------------------
		
		protected ModelAndView createEditModelAndView(CreditCard cc){
			ModelAndView res;
			res= this.createEditModelAndView(cc,null);
			return res;
		}
		
		protected ModelAndView createEditModelAndView(CreditCard cc, String messageCode){
			ModelAndView res;
			
			res= new ModelAndView("creditCard/create");
			res.addObject("creditCard",cc);
			res.addObject("message", messageCode);	
			
			return res;
		}					

}
