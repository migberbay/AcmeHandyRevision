package controllers.actor;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.UserAccount;
import security.UserAccountService;
import services.CustomerService;
import services.HandyWorkerService;
import services.SponsorService;
import controllers.AbstractController;
import domain.Customer;
import domain.HandyWorker;
import domain.Sponsor;

@Controller 
@RequestMapping("actor/")
public class ActorCreateController extends AbstractController {

	// Services ----------------------------------------------------------------

	@Autowired
	private UserAccountService userAccountService;
	
	@Autowired
	private HandyWorkerService handyWorkerService;

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private SponsorService sponsorService;
	
	// Constructors ------------------------------------------------------------

	public ActorCreateController() {
		super();
	}

	// Create -----------------------------------------------------------------

	@RequestMapping(value = "/createCustomer", method = RequestMethod.GET)
	public ModelAndView createCustomer() {

		ModelAndView result;
		
		Customer customer = customerService.create();
		result = this.createEditModelAndViewCustomer(customer);

		return result;
	}
	
	@RequestMapping(value = "/createHandyWorker", method = RequestMethod.GET)
	public ModelAndView createHandyWorker() {

		ModelAndView result;
		
		HandyWorker hw = handyWorkerService.create();
		result = this.createEditModelAndViewHandyWorker(hw);

		return result;
	}
	
	@RequestMapping(value = "/createSponsor", method = RequestMethod.GET)
	public ModelAndView createSponsor() {

		ModelAndView result;
		
		Sponsor sponsor = sponsorService.create();
		result = this.createEditModelAndViewSponsor(sponsor);

		return result;
	}

	// SAVES-------------------------------------------------------
	
	// CUSTOMER
	@RequestMapping(value = "/createCustomer", method = RequestMethod.POST, params = "save")
	public ModelAndView saveCustomer(@Valid final Customer customer , final BindingResult binding) {
		ModelAndView result;		
		
		if (binding.hasErrors()) {
			result = this.createEditModelAndViewCustomer(customer);
		} else
			try {
				UserAccount savedUA = userAccountService.save(customer.getUserAccount());
				customer.setUserAccount(savedUA);
				customerService.save(customer);
				result = new ModelAndView("redirect:/security/login.do");
				
			} catch (final Throwable oops) {
				result = this.createEditModelAndViewCustomer(customer,"actor.commit.error");
			}
		return result;
	}
	
	// SPONSOR
		@RequestMapping(value = "/createSponsor", method = RequestMethod.POST, params = "save")
		public ModelAndView saveSponsor(@Valid final Sponsor sponsor , final BindingResult binding) {
			ModelAndView result;

			if (binding.hasErrors()) {
				result = this.createEditModelAndViewSponsor(sponsor);
			} else
				try {
					UserAccount savedUA = userAccountService.save(sponsor.getUserAccount());
					sponsor.setUserAccount(savedUA);
					
					sponsorService.save(sponsor);
					result = new ModelAndView("redirect:/security/login.do");
					
				} catch (final Throwable oops) {
					result = this.createEditModelAndViewSponsor(sponsor,"actor.commit.error");
				}
			return result;
		}
		
		// HANDYWORKER
		@RequestMapping(value = "/createHandyWorker", method = RequestMethod.POST, params = "save")
		public ModelAndView saveHandyWorker(@Valid final HandyWorker handyworker , final BindingResult binding) {
			ModelAndView result;

			if (binding.hasErrors()) {
				result = this.createEditModelAndViewHandyWorker(handyworker);
			} else
				try {
					UserAccount savedUA = userAccountService.save(handyworker.getUserAccount());
					handyworker.setUserAccount(savedUA);
					
					handyWorkerService.save(handyworker);
					result = new ModelAndView("redirect:/security/login.do");
					
				} catch (final Throwable oops) {
					result = this.createEditModelAndViewHandyWorker(handyworker,"actor.commit.error");
				}
			return result;
		}
	

	protected ModelAndView createEditModelAndViewCustomer(Customer c) {
		ModelAndView result;

		result = this.createEditModelAndViewCustomer(c, null);

		return result;
	}
	
	protected ModelAndView createEditModelAndViewCustomer(Customer c, String messageCode){
		ModelAndView res;
		
		res= new ModelAndView("actor/createCustomer");
		res.addObject("customer",c);
		res.addObject("message", messageCode);	
		
		return res;
	}
	
	protected ModelAndView createEditModelAndViewSponsor(Sponsor c) {
		ModelAndView result;

		result = this.createEditModelAndViewSponsor(c, null);

		return result;
	}
	
	protected ModelAndView createEditModelAndViewSponsor(Sponsor c, String messageCode){
		ModelAndView res;
		
		res= new ModelAndView("actor/createSponsor");
		res.addObject("sponsor",c);
		res.addObject("message", messageCode);	
		
		return res;
	}
	
	protected ModelAndView createEditModelAndViewHandyWorker(HandyWorker c) {
		ModelAndView result;

		result = this.createEditModelAndViewHandyWorker(c, null);

		return result;
	}
	
	protected ModelAndView createEditModelAndViewHandyWorker(HandyWorker c, String messageCode){
		ModelAndView res;
		
		res= new ModelAndView("actor/createHandyWorker");
		res.addObject("handyworker",c);
		res.addObject("message", messageCode);	
		
		return res;
	}

}
