package controllers.handyworker;

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
import services.CustomerEndorsementService;
import services.CustomerService;
import services.HandyWorkerService;
import controllers.AbstractController;
import domain.Customer;
import domain.CustomerEndorsement;
import domain.HandyWorker;


@Controller
@RequestMapping("customerEndorsement/handyWorker/")
public class CustomerEndorsementHandyWorkerController extends AbstractController{

public CustomerEndorsementHandyWorkerController(){
	super();
}

	@Autowired
	CustomerEndorsementService customerEndorsementService;
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	HandyWorkerService handyWorkerService;


	//Listing---
		@RequestMapping(value="/list", method = RequestMethod.GET)
		public ModelAndView list(){
			
			ModelAndView res;
			Collection<CustomerEndorsement> customerEndorsements;
			HandyWorker hw;
			hw = handyWorkerService.findByUserAccountId(LoginService.getPrincipal().getId());
			customerEndorsements = customerEndorsementService.findAll();
			
			res = new ModelAndView("customerEndorsement/list");
			res.addObject("customerEndorsements", customerEndorsements);
			res.addObject("handyWorker",hw);
			res.addObject("requestURI", "customerEndorsement/handyWorker/list.do");
			
			return res; 
		}

		// Create -----------------------------------------------------------------

		@RequestMapping(value = "/create", method = RequestMethod.GET)
		public ModelAndView create() {
			ModelAndView result;
			CustomerEndorsement customerEndorsement;
			customerEndorsement = this.customerEndorsementService.create();
			
			result = this.createEditModelAndView(customerEndorsement);

			return result;
		}

		// Edit -----------------------------------------------------------------

		@RequestMapping(value = "/edit", method = RequestMethod.GET)
		public ModelAndView edit(@RequestParam final int customerEndorsementId) {
			ModelAndView result;
			CustomerEndorsement customerEndorsement;

			customerEndorsement = this.customerEndorsementService.findOne(customerEndorsementId);
			result = this.createEditModelAndView(customerEndorsement);

			return result;
		}
		
	//Delete edit----------------------------------------------------------------------------------------------------------------------------------------
		
		@RequestMapping(value="/edit", method = RequestMethod.POST, params = "delete")
		public ModelAndView delete(CustomerEndorsement customerEndorsement, BindingResult binding){
			
			ModelAndView res;
			
			try{
				this.customerEndorsementService.delete(customerEndorsement);
				res= new ModelAndView("redirect:list.do");
			} catch (Throwable oops) {
				res = createEditModelAndView(customerEndorsement,"customerEndorsement.commit.error");
			}
			return res;
		}
		
	//Delete list ----------------------------------------------------------------------------------------------------------------------------------------
	
		@RequestMapping(value="/delete", method = RequestMethod.GET)
		public ModelAndView delete(@RequestParam final int customerEndorsementId){
			
			ModelAndView res;
			CustomerEndorsement customerEndorsement;
			customerEndorsement = customerEndorsementService.findOne(customerEndorsementId);
			
			try{
				this.customerEndorsementService.delete(customerEndorsement);
				res= new ModelAndView("redirect:list.do");
			} catch (Throwable oops) {
				res = createEditModelAndView(customerEndorsement,"customerEndorsement.commit.error");
			}
			return res;
		}
		
	//Save---------------------------------------------------------------	
		
		@RequestMapping(value="/edit", method=RequestMethod.POST, params="save")
		public ModelAndView save(@Valid CustomerEndorsement customerEndorsement, BindingResult binding){
			ModelAndView res;
			if(binding.hasErrors()){
				System.out.println("Fallos en: \n" + binding.getAllErrors());
				res = this.createEditModelAndView(customerEndorsement);
			}else{
				try {
					this.customerEndorsementService.save(customerEndorsement);
					res = new ModelAndView("redirect:list.do");
				} catch (Throwable oops) {
					System.out.println(oops.getCause());
					res = this.createEditModelAndView(customerEndorsement, "customerEndorsement.commit.error");
				}
			}
			return res;
		}
		
	//Ancillary Methods---------
		
		protected ModelAndView createEditModelAndView(CustomerEndorsement customerEndorsement){
			ModelAndView res;
			res= this.createEditModelAndView(customerEndorsement, null);
			return res;
		}
		
		protected ModelAndView createEditModelAndView(CustomerEndorsement customerEndorsement, String messageCode){
			ModelAndView res;
			
			Integer customerId = customerEndorsement.getCustomer().getId();
			Customer customer = customerService.findOne(customerId);
			Integer handyWorkerId = customerEndorsement.getHandyWorker().getId() ;
			HandyWorker handyWorker = handyWorkerService.findOne(handyWorkerId);
			
			res= new ModelAndView("customerEndorsement/edit");
			res.addObject("customerEndorsement", customerEndorsement);
			res.addObject("now",new Date());
			res.addObject("customer", customer);
			res.addObject("handyWorker", handyWorker);
			res.addObject("message", messageCode);
			
			return res;
		}

}
