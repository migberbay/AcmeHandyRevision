package controllers.customer;

import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.CustomerService;
import services.HandyWorkerEndorsementService;
import services.HandyWorkerService;
import controllers.AbstractController;
import domain.Complaint;
import domain.Customer;
import domain.HandyWorker;
import domain.HandyWorkerEndorsement;
import domain.Report;


@Controller
@RequestMapping("handyWorkerEndorsement/customer/")
public class HandyWorkerEndorsementCustomerController extends AbstractController{

public HandyWorkerEndorsementCustomerController(){
	super();
}

	@Autowired
	HandyWorkerEndorsementService handyWorkerEndorsementService;
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	HandyWorkerService handyWorkerService;


	//Listing---
		@RequestMapping(value="/list", method = RequestMethod.GET)
		public ModelAndView list(){
			
			ModelAndView res;
			Collection<HandyWorkerEndorsement> handyWorkerEndorsements;
			Customer c;
			c = customerService.findByUserAccountId(LoginService.getPrincipal().getId());
			handyWorkerEndorsements = handyWorkerEndorsementService.hwEndorsementsByCustomer(c.getId());
			
			res = new ModelAndView("handyWorkerEndorsement/list");
			res.addObject("handyWorkerEndorsements", handyWorkerEndorsements);
			res.addObject("customer",c);
			res.addObject("requestURI", "handyWorkerEndorsement/customer/list.do");
			
			return res; 
		}

		// Create -----------------------------------------------------------------

		@RequestMapping(value = "/create", method = RequestMethod.GET)
		public ModelAndView create() {
			ModelAndView result;
			HandyWorkerEndorsement handyWorkerEndorsement;
			handyWorkerEndorsement = this.handyWorkerEndorsementService.create();
			
			Customer logged = customerService.findByUserAccountId(LoginService.getPrincipal().getId());

			if(handyWorkerEndorsement.getCustomer().equals(logged)){
				result = this.createEditModelAndView(handyWorkerEndorsement);
			}else{
				result = new ModelAndView("error/access");
			}

			return result;
		}

		// Edit -----------------------------------------------------------------

		@RequestMapping(value = "/edit", method = RequestMethod.GET)
		public ModelAndView edit(@RequestParam final int handyWorkerEndorsementId) {
			ModelAndView result;
			HandyWorkerEndorsement handyWorkerEndorsement;
			
			handyWorkerEndorsement = this.handyWorkerEndorsementService.findOne(handyWorkerEndorsementId);
			Customer logged = customerService.findByUserAccountId(LoginService.getPrincipal().getId());

			if(handyWorkerEndorsement.getCustomer().equals(logged)){
				result = this.createEditModelAndView(handyWorkerEndorsement);
			}else{
				result = new ModelAndView("error/access");
			}

			return result;
		}

	//Delete list ----------------------------------------------------------------------------------------------------------------------------------------
	
		@RequestMapping(value="/delete", method = RequestMethod.GET)
		public ModelAndView delete(@RequestParam final int handyWorkerEndorsementId){
			ModelAndView res;
			HandyWorkerEndorsement handyWorkerEndorsement;
			handyWorkerEndorsement = handyWorkerEndorsementService.findOne(handyWorkerEndorsementId);
			
			try{
				this.handyWorkerEndorsementService.delete(handyWorkerEndorsement);
				res= new ModelAndView("redirect:list.do");
			} catch (Throwable oops) {
				res = createEditModelAndView(handyWorkerEndorsement,"handyWorkerEndorsement.commit.error");
			}
			return res;
		}
		
	//Save---------------------------------------------------------------	
		
		@RequestMapping(value="/edit", method=RequestMethod.POST, params="save")
		public ModelAndView save(@Valid HandyWorkerEndorsement handyWorkerEndorsement, BindingResult binding){
			ModelAndView res;
			if(binding.hasErrors()){
				System.out.println("Fallos en: \n" + binding.getAllErrors());
				res = this.createEditModelAndView(handyWorkerEndorsement);
			}else{
				try {
					this.handyWorkerEndorsementService.save(handyWorkerEndorsement);
					res = new ModelAndView("redirect:list.do");
				} catch (Throwable oops) {
					System.out.println(oops.getCause());
					res = this.createEditModelAndView(handyWorkerEndorsement, "handyWorkerEndorsement.commit.error");
				}
			}
			return res;
		}
		
	// Showing ----------------------------------------------------------------
		
		@RequestMapping(value="/show", method = RequestMethod.GET)
		public ModelAndView show(@RequestParam final int handyWorkerEndorsementId){
			ModelAndView result;

			HandyWorkerEndorsement handyWorkerEndorsement = handyWorkerEndorsementService.findOne(handyWorkerEndorsementId);
			result = new ModelAndView("handyWorkerEndorsement/show");

			result.addObject("handyWorkerEndorsement", handyWorkerEndorsement);
		return result; 
		}
		
	//Ancillary Methods---------
		
		protected ModelAndView createEditModelAndView(HandyWorkerEndorsement handyWorkerEndorsement){
			ModelAndView res;
			res= this.createEditModelAndView(handyWorkerEndorsement, null);
			return res;
		}
		
		protected ModelAndView createEditModelAndView(HandyWorkerEndorsement handyWorkerEndorsement, String messageCode){
			ModelAndView res;
			
			Integer customerId = handyWorkerEndorsement.getCustomer().getId();
			Collection<HandyWorker> handyWorkers = handyWorkerService.getHandyWorkersByCustomerTasks(customerId);
			res= new ModelAndView("handyWorkerEndorsement/edit");
			res.addObject("handyWorkerEndorsement", handyWorkerEndorsement);
			res.addObject("handyWorkers",handyWorkers);
			res.addObject("message", messageCode);
			
			return res;
		}

}
