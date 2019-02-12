package controllers.administrator;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import security.UserAccount;
import security.UserAccountService;
import services.AdministratorService;
import services.RefereeService;
import controllers.AbstractController;
import domain.Actor;
import domain.Administrator;
import domain.Referee;

@Controller 
@RequestMapping("/userAccount/admin")
public class UserAccountAdministratorController extends AbstractController {

	// Services ----------------------------------------------------------------

	@Autowired
	private UserAccountService userAccountService;
	
	@Autowired
	private AdministratorService adminService;

	@Autowired
	private RefereeService refereeService;
	// Constructors ------------------------------------------------------------

	public UserAccountAdministratorController() {
		super();
	}

	// Create -----------------------------------------------------------------

	@RequestMapping(value = "/createAdmin", method = RequestMethod.GET)
	public ModelAndView createAdmin() {

		ModelAndView result;

		result = this.createEditModelAndView();

		return result;
	}

	// SAVE ADMIN
	@RequestMapping(value = "/createAdmin", method = RequestMethod.POST, params = "saveAdmin")
	public ModelAndView saveAdmin(@Valid final Administrator admin , final BindingResult binding) {
		ModelAndView result;
		
		System.out.println("saving the admin account: "+admin.getUserAccount().getUsername()+"  "+admin.getUserAccount().getPassword());
		

		if (binding.hasErrors()) {
			System.out.println(binding.getFieldErrors());
			result = this.createEditModelAndView();
		} else
			try {
				UserAccount savedUA = userAccountService.save(admin.getUserAccount());
				admin.setUserAccount(savedUA);
				adminService.save(admin);
				result = new ModelAndView("redirect:/");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView("admin.commit.error");
				System.out.println("EXCEPCION CAPTURADA!!!!!!: "+oops.getStackTrace());
			}
		return result;
	}
	
	// SAVE REFEREE
	@RequestMapping(value = "/createAdmin", method = RequestMethod.POST, params = "saveReferee")
	public ModelAndView saveReferee(@Valid final Referee referee, final BindingResult binding) {
		ModelAndView result;
		System.out.println("saving the referee account: "+referee.getUserAccount().getUsername()+"  "+referee.getUserAccount().getPassword());
		
		if (binding.hasErrors()) {
			System.out.println(binding.getFieldErrors());
			result = this.createEditModelAndView();
		} else
			try {
				UserAccount savedUA = userAccountService.save(referee.getUserAccount());
				referee.setUserAccount(savedUA);
				refereeService.save(referee);
				result = new ModelAndView("redirect:/");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView("admin.commit.error");
				System.out.println("EXCEPCION CAPTURADA!!!!!!:" + oops.getStackTrace());
			}
		return result;
	}

	protected ModelAndView createEditModelAndView() {
		ModelAndView result;

		result = this.createEditModelAndView(null);

		return result;
	}

	private ModelAndView createEditModelAndView(final String message) {

		ModelAndView result;

		result = new ModelAndView("userAccount/createAdmin");

		//creating admin UserAccount and Actor 
		UserAccount administrator = userAccountService.create();
		Authority authority = new Authority();
		authority.setAuthority("ADMIN");
		administrator.getAuthorities().add(authority);
		Administrator admin = adminService.createAdministrator();
		admin.setUserAccount(administrator);
		
		//creating referee UserAccount and Actor
		UserAccount refereeUA = userAccountService.create();
		Authority authority2 = new Authority();
		authority2.setAuthority("REFEREE");
		refereeUA.getAuthorities().add(authority2);
		Referee referee = adminService.createReferee();
		referee.setUserAccount(refereeUA);
		
		
		result.addObject("admin", admin);
		result.addObject("referee", referee);
		result.addObject("message", message);

		return result;
	}

}
