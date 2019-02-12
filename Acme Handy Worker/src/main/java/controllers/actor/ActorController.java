package controllers.actor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import security.LoginService;
import security.UserAccount;
import services.ActorService;
import services.AdministratorService;
import services.ApplicationService;
import services.CustomerEndorsementService;
import services.CustomerService;
import services.HandyWorkerEndorsementService;
import services.HandyWorkerService;
import services.RefereeService;
import services.SponsorService;
import services.TutorialService;

import controllers.AbstractController;
import domain.Actor;
import domain.Administrator;
import domain.Application;
import domain.Customer;
import domain.FixUpTask;
import domain.HandyWorker;
import domain.Referee;
import domain.SocialProfile;
import domain.Sponsor;
import domain.Tutorial;

@Controller
@RequestMapping("/actor")
public class ActorController extends AbstractController {

	// Services
	// -------------------------------------------------------------------

	@Autowired
	private ActorService actorService;

	@Autowired
	private SponsorService sponsorService;

	@Autowired
	private RefereeService refereeService;

	@Autowired
	private AdministratorService administratorService;

	@Autowired
	private HandyWorkerService handyWorkerService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private TutorialService tutorialService;
	
	@Autowired
	private HandyWorkerEndorsementService handyWorkerEndorsementService;
	
	@Autowired
	private CustomerEndorsementService customerEndorsementService;

	// Constructors
	// ---------------------------------------------------------------

	public ActorController() {
		super();
	}

	// Create handyWorker -----------------------------------------------------

	@RequestMapping(value = "/createHandyWorker", method = RequestMethod.GET)
	public ModelAndView createHandyWorker() {

		ModelAndView result;

		HandyWorker handyWorker = handyWorkerService.create();

		result = this.createEditModelAndView(handyWorker);

		return result;
	}

	// Create customer --------------------------------------------------------

	@RequestMapping(value = "/createCustomer", method = RequestMethod.GET)
	public ModelAndView createCustomer() {

		ModelAndView result;

		Customer customer = customerService.create();

		result = this.createEditModelAndView(customer);
		result.addObject("customer", customer);

		return result;
	}

	// Create sponsor ---------------------------------------------------------

	@RequestMapping(value = "/createSponsor", method = RequestMethod.GET)
	public ModelAndView createSponsor() {

		ModelAndView result;

		Sponsor sponsor = sponsorService.create();

		result = this.createEditModelAndView(sponsor);

		return result;
	}

	// Show --------------------------------------------------------------------

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam(required = false) Integer actorId) {

		ModelAndView result;

		// Diferentes autoridades:
		Authority authority = new Authority();
		authority.setAuthority("CUSTOMER");

		Authority authority2 = new Authority();
		authority2.setAuthority("HANDYWORKER");

		Authority authority3 = new Authority();
		authority3.setAuthority("SPONSOR");

		Authority authority4 = new Authority();
		authority4.setAuthority("REFEREE");

		Authority authority5 = new Authority();
		authority5.setAuthority("ADMIN");
		
		Boolean custProfileHw = false;
		Double score = 0d;
		Boolean hw = false,cust=false;
		result = new ModelAndView("actor/show");
		if (actorId != null) {
			Actor actor = actorService.findOne(actorId);
			Boolean logged = false;
			if(LoginService.getPrincipal().equals(actor.getUserAccount())) logged=true;
			result.addObject("actor", actor);
			result.addObject("logged",logged);
			
			if(actor.getUserAccount().getAuthorities().contains(authority)){
				System.out.println("wtf ha pasado");
				cust=true;
				Customer customer = customerService.findOne(actor.getId()); 
				Map<Customer, Double> scoreC = customerEndorsementService.getScoreCustomerEndorsement();
				 score = scoreC.get(customer);
				 System.out.println("Controller actor score" + score);
			}
			if(actor.getUserAccount().getAuthorities().contains(authority2)){
				hw=true;
				HandyWorker handyw = handyWorkerService.findOne(actor.getId()); 
				Map<HandyWorker, Double> scoreC = handyWorkerEndorsementService.getScoreHandyWorkerEndorsement();
				score = scoreC.get(handyw);
				Collection<Tutorial> tutorials = tutorialService.tutorialsByHandyWorker(actor.getId());
				result.addObject("tutorials", tutorials);
			}
	
			if(LoginService.getPrincipal().getAuthorities().contains(authority2) &&
					actor.getUserAccount().getAuthorities().contains(authority)){ //Si el logeado es hw y vemos el perfil de customer
				custProfileHw = true;
				Customer customer = customerService.findOne(actor.getId());
				result.addObject("fixUpTasks", customer.getFixUpTasks());
				result.addObject("custProfileHw",custProfileHw);
			}
			
		}else{
			Actor actor = actorService.getByUserAccountId(LoginService.getPrincipal());
			Collection<SocialProfile> socialProfiles = actor.getSocialProfiles();
			Boolean logged = true;
			result.addObject("logged",logged);

		if (actor.getUserAccount().getAuthorities().contains(authority)) {
			custProfileHw=true;
			Customer customer = (Customer) actorService
					.getByUserAccountId(LoginService.getPrincipal());
			cust= true;
			customer = customerService.findOne(actor.getId());

			Collection<FixUpTask> fixUpTasks = customer.getFixUpTasks();
			Map<Customer, Double> scoreC = customerEndorsementService.getScoreCustomerEndorsement();
			 score = scoreC.get(customer);
			 System.out.println("Controller actor score" + score);

			result.addObject("fixUpTasks", fixUpTasks);
			result.addObject("custProfileHw",custProfileHw);
			result.addObject("actor", customer);
		}

		if (actor.getUserAccount().getAuthorities().contains(authority2)) {
			HandyWorker handyWorker = (HandyWorker) actorService
					.getByUserAccountId(LoginService.getPrincipal());
			hw=true;
			handyWorker = handyWorkerService.findOne(actor.getId());
			Map<HandyWorker, Double> scoreHW = handyWorkerEndorsementService.getScoreHandyWorkerEndorsement();
			score = scoreHW.get(handyWorker);
			int handyWorkerId = handyWorker.getId();

			Collection<Tutorial> tutorials = tutorialService.tutorialsByHandyWorker(handyWorkerId);
			result.addObject("tutorials", tutorials);
			result.addObject("actor", handyWorker);
		}

		if (actor.getUserAccount().getAuthorities().contains(authority3)) {
			Sponsor sponsor = (Sponsor) actorService
					.getByUserAccountId(LoginService.getPrincipal());

			sponsor = sponsorService.findOne(actor.getId());

			result.addObject("actor", sponsor);
		}

		if (actor.getUserAccount().getAuthorities().contains(authority4)) {
			Referee referee = (Referee) actorService
					.getByUserAccountId(LoginService.getPrincipal());

			referee = refereeService.findOne(actor.getId());

			result.addObject("actor", referee);
		}

		if (actor.getUserAccount().getAuthorities().contains(authority5)) {
			Administrator administrator = (Administrator) actorService
					.getByUserAccountId(LoginService.getPrincipal());

			administrator = administratorService.findOne(actor.getId());

			result.addObject("actor", administrator);
		}
		result.addObject("socialProfiles", socialProfiles);
		}
		result.addObject("score",score);
		result.addObject("hw",hw);
		result.addObject("cust",cust);
		result.addObject("requestURI", "actor/show.do");

		return result;
	}

	// Edit -----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {

		ModelAndView result = new ModelAndView();

		Actor actor = actorService.getByUserAccountId(LoginService
				.getPrincipal());
				
		result = createEditModelAndView(actor);

		return result;
	}
	
	// Save -----------------------------------------------------------------

		@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
		public ModelAndView save(@Valid final Actor actor,
				final BindingResult binding) {
			ModelAndView result;
			Actor actorDB = actorService.findOne(actor.getId());

			Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			 String pass = encoder.encodePassword(actor.getUserAccount()
			 .getPassword(), null);
			 actor.getUserAccount().setPassword(pass);

			if (binding.hasErrors()) {
				System.out.println(binding.getFieldErrors());
				result = this.createEditModelAndView(actor);
			} else
				try {
					if(customerService.findOne(actor.getId())!=null) customerService.save(customerService.findOne(actor.getId()));
					if(handyWorkerService.findOne(actor.getId())!=null) handyWorkerService.save(handyWorkerService.findOne(actor.getId()));
					if(refereeService.findOne(actor.getId())!=null) refereeService.save(refereeService.findOne(actor.getId()));
					if(sponsorService.findOne(actor.getId())!=null) sponsorService.save(sponsorService.findOne(actor.getId()));
					if(administratorService.findOne(actor.getId())!=null) administratorService.save(administratorService.findOne(actor.getId()));

					if(actorDB.getUserAccount().getUsername() != actor.getUserAccount().getUsername() ||
							actorDB.getUserAccount().getPassword() != actorDB.getUserAccount().getPassword())
					result = new ModelAndView("j_spring_security_logout");
					else
						result = new ModelAndView("redirect:show.do");

				} catch (final Throwable oops) {
					result = this.createEditModelAndView(actor,
							"actor.commit.error");
				}
			return result;
		}

	// Save -----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveAdministrator")
	public ModelAndView save(@Valid final Administrator administrator,
			final BindingResult binding) {

		ModelAndView result;
		Administrator administratorDB = administratorService.findOne(administrator.getId());
		Boolean uaChange = false;
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		String pass = encoder.encodePassword(administrator.getUserAccount().getPassword(), null);

		if(!administrator.getUserAccount().getUsername().equals(administratorDB.getUserAccount().getUsername()) ||
				!pass.equals(administratorDB.getUserAccount().getPassword())){
			uaChange = true;
			String a = administrator.getUserAccount().getUsername();
			administrator.setUserAccount(administratorDB.getUserAccount());
			administrator.getUserAccount().setUsername(a);
			administrator.getUserAccount().setPassword(pass);
		}else{
			administrator.setUserAccount(administratorDB.getUserAccount());
		}
		
		


		
		if (binding.hasErrors()) {
			System.out.println(binding.getFieldErrors());
			result = this.createEditModelAndView(administrator);
		} else
			try {
				administratorService.save(administrator);
				if(uaChange) result = new ModelAndView("redirect:/j_spring_security_logout");				
				else result = new ModelAndView("redirect:show.do");
				
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(administrator,
						"actor.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveHandyWorker")
	public ModelAndView save(@Valid final HandyWorker handyWorker,
			final BindingResult binding) {


		ModelAndView result;
		HandyWorker handyWorkerDB = handyWorkerService.findOne(handyWorker.getId());
		Boolean uaChange = false;
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		String pass = encoder.encodePassword(handyWorker.getUserAccount().getPassword(), null);

		if(!handyWorker.getUserAccount().getUsername().equals(handyWorkerDB.getUserAccount().getUsername()) ||
				!pass.equals(handyWorkerDB.getUserAccount().getPassword())){
			uaChange = true;
			String a = handyWorker.getUserAccount().getUsername();
			handyWorker.setUserAccount(handyWorkerDB.getUserAccount());
			handyWorker.getUserAccount().setUsername(a);
			handyWorker.getUserAccount().setPassword(pass);
		}else{
			handyWorker.setUserAccount(handyWorkerDB.getUserAccount());
		}
		
		


		
		if (binding.hasErrors()) {
			System.out.println(binding.getFieldErrors());
			result = this.createEditModelAndView(handyWorker);
		} else
			try {
				handyWorkerService.save(handyWorker);
				if(uaChange) result = new ModelAndView("redirect:/j_spring_security_logout");				
				else result = new ModelAndView("redirect:show.do");
				
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(handyWorker,
						"actor.commit.error");
			}
		return result;
	}


	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveCustomer")
	public ModelAndView save(@Valid final Customer customer,
			final BindingResult binding) {

		ModelAndView result;
		Customer customerDB = customerService.findOne(customer.getId());
		Boolean uaChange = false;
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		String pass = encoder.encodePassword(customer.getUserAccount().getPassword(), null);

		if(!customer.getUserAccount().getUsername().equals(customerDB.getUserAccount().getUsername()) ||
				!pass.equals(customerDB.getUserAccount().getPassword())){
			uaChange = true;
			String a = customer.getUserAccount().getUsername();
			customer.setUserAccount(customerDB.getUserAccount());
			customer.getUserAccount().setUsername(a);
			customer.getUserAccount().setPassword(pass);
		}else{
			customer.setUserAccount(customerDB.getUserAccount());
		}
		
		


		
		if (binding.hasErrors()) {
			System.out.println(binding.getFieldErrors());
			result = this.createEditModelAndView(customer);
		} else
			try {
				customerService.save(customer);
				if(uaChange) result = new ModelAndView("redirect:/j_spring_security_logout");				
				else result = new ModelAndView("redirect:show.do");
				
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(customer,
						"actor.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveReferee")
	public ModelAndView save(@Valid final Referee referee,
			final BindingResult binding) {
		System.out.println("entra al save referee");
		ModelAndView result;
		Referee refereeDB = refereeService.findOne(referee.getId());
		Boolean uaChange = false;
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		String pass = encoder.encodePassword(referee.getUserAccount().getPassword(), null);

		if(!referee.getUserAccount().getUsername().equals(refereeDB.getUserAccount().getUsername()) ||
				!pass.equals(refereeDB.getUserAccount().getPassword())){
			uaChange = true;
			String a = referee.getUserAccount().getUsername();
			referee.setUserAccount(refereeDB.getUserAccount());
			referee.getUserAccount().setUsername(a);
			referee.getUserAccount().setPassword(pass);
		}else{
			referee.setUserAccount(refereeDB.getUserAccount());
		}
		
		


		
		if (binding.hasErrors()) {
			System.out.println(binding.getFieldErrors());
			result = this.createEditModelAndView(referee);
		} else
			try {
				refereeService.save(referee);
				if(uaChange) result = new ModelAndView("redirect:/j_spring_security_logout");				
				else result = new ModelAndView("redirect:show.do");
				
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(referee,
						"actor.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveSponsor")
	public ModelAndView save(@Valid final Sponsor sponsor,
			final BindingResult binding) {
		System.out.println("entra al save sponsor?");

		ModelAndView result;
		Sponsor sponsorDB = sponsorService.findOne(sponsor.getId());
		Boolean uaChange = false;
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		String pass = encoder.encodePassword(sponsor.getUserAccount().getPassword(), null);

		if(!sponsor.getUserAccount().getUsername().equals(sponsorDB.getUserAccount().getUsername()) ||
				!pass.equals(sponsorDB.getUserAccount().getPassword())){
			uaChange = true;
			String a = sponsor.getUserAccount().getUsername();
			sponsor.setUserAccount(sponsorDB.getUserAccount());
			sponsor.getUserAccount().setUsername(a);
			sponsor.getUserAccount().setPassword(pass);
		}else{
			sponsor.setUserAccount(sponsorDB.getUserAccount());
		}
		
		


		
		if (binding.hasErrors()) {
			System.out.println(binding.getFieldErrors());
			result = this.createEditModelAndView(sponsor);
		} else
			try {
				sponsorService.save(sponsor);
				if(uaChange) result = new ModelAndView("redirect:/j_spring_security_logout");				
				else result = new ModelAndView("redirect:show.do");
				
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(sponsor,
						"actor.commit.error");
			}
		return result;
	}

	protected ModelAndView createEditModelAndView(final Actor actor) {
		ModelAndView result;
		result = this.createEditModelAndView(actor, null);
		return result;
	}

	private ModelAndView createEditModelAndView(final Actor actor,
			final String message) {

		ModelAndView result;
		String type = "";

		result = new ModelAndView("actor/edit");

		if(customerService.findOne(actor.getId())!=null){
			Customer c = customerService.save(customerService.findOne(actor.getId()));
			type="customer";
			result.addObject("type",type);
			result.addObject("actor",c);
		}
		if(handyWorkerService.findOne(actor.getId())!=null){
			HandyWorker hw = handyWorkerService.save(handyWorkerService.findOne(actor.getId()));
			type="handyworker";
			result.addObject("type",type);
			result.addObject("actor",hw);
		}
		if(refereeService.findOne(actor.getId())!=null){ 
			Referee r = refereeService.save(refereeService.findOne(actor.getId()));
			type="referee";
			result.addObject("type",type);
			result.addObject("actor",r);
		}
		if(sponsorService.findOne(actor.getId())!=null){
			Sponsor s = sponsorService.save(sponsorService.findOne(actor.getId()));
			type="sponsor";
			result.addObject("type",type);
			result.addObject("actor",s);
		}
		if(administratorService.findOne(actor.getId())!=null){
			Administrator a = administratorService.save(administratorService.findOne(actor.getId()));
			type="administrator";
			result.addObject("type",type);
			result.addObject("actor",a);
		}
		result.addObject("message", message);

		return result;
	}

}