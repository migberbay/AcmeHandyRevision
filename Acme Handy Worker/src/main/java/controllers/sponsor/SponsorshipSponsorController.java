package controllers.sponsor;

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
import services.SponsorService;
import services.SponsorshipService;
import services.TutorialService;
import controllers.AbstractController;
import domain.Sponsor;
import domain.Sponsorship;
import domain.Tutorial;

@Controller
@RequestMapping("sponsorship/sponsor/")
public class SponsorshipSponsorController extends AbstractController{

public SponsorshipSponsorController(){
	super();
}

	@Autowired
	SponsorshipService sponsorshipService;
	
	@Autowired
	SponsorService sponsorService;
	
	@Autowired
	TutorialService tutorialService;

	//Listing---
		@RequestMapping(value="/list", method = RequestMethod.GET)
		public ModelAndView list(){
			
			ModelAndView res;
			Collection<Sponsorship> sponsorships;
			Sponsor s;
			s = sponsorService.findSponsorByUserAccount(LoginService.getPrincipal());
			sponsorships = sponsorshipService.getSponsorshipsSponsor(s.getId());
			
			res = new ModelAndView("sponsorship/list");
			res.addObject("sponsorships", sponsorships);
			res.addObject("requestURI", "sponsorship/sponsor/list.do");
			return res; 
		}

		// Create -----------------------------------------------------------------

		@RequestMapping(value = "/create", method = RequestMethod.GET)
		public ModelAndView create() {
			ModelAndView result;
			Sponsorship sponsorship;
			sponsorship = this.sponsorshipService.create();
			result = this.createEditModelAndView(sponsorship);

			return result;
		}

		// Edit -----------------------------------------------------------------

		@RequestMapping(value = "/edit", method = RequestMethod.GET)
		public ModelAndView edit(@RequestParam final int sponsorshipId) {
			ModelAndView result;
			Sponsorship sponsorship;
			Sponsor logged;
			
			sponsorship = this.sponsorshipService.findOne(sponsorshipId);
			logged = sponsorService.findSponsorByUserAccount(LoginService.getPrincipal());
			
			if(sponsorship.getSponsor().equals(logged)){
				result = this.createEditModelAndView(sponsorship);
			}else{
				result = new ModelAndView("error/access");
			}

			return result;
		}
		
	//Delete edit----------------------------------------------------------------------------------------------------------------------------------------
		
		@RequestMapping(value="/edit", method = RequestMethod.POST, params = "delete")
		public ModelAndView delete(Sponsorship sponsorship, BindingResult binding){
			
			ModelAndView res;
			
			try{
				this.sponsorshipService.delete(sponsorship);
				res= new ModelAndView("redirect:list.do");
			} catch (Throwable oops) {
				res = createEditModelAndView(sponsorship,"sponsorship.commit.error");
			}
			return res;
		}
		
	//Delete list ----------------------------------------------------------------------------------------------------------------------------------------
	
		@RequestMapping(value="/delete", method = RequestMethod.GET)
		public ModelAndView delete(@RequestParam final int sponsorshipId){
			
			ModelAndView res;
			Sponsorship sponsorship;
			Sponsor logged;
			
			sponsorship = this.sponsorshipService.findOne(sponsorshipId);
			logged = sponsorService.findSponsorByUserAccount(LoginService.getPrincipal());
			
			if(sponsorship.getSponsor().equals(logged)){
				try{
					this.sponsorshipService.delete(sponsorship);
					res= new ModelAndView("redirect:list.do");
				} catch (Throwable oops) {
					res = createEditModelAndView(sponsorship,"sponsorship.commit.error");
				}
			}else{
				res = new ModelAndView("error/access");
			}
			
			return res;
		}
		
	//Save---------------------------------------------------------------	
		
		@RequestMapping(value="/edit", method=RequestMethod.POST, params="save")
		public ModelAndView save(@Valid Sponsorship sponsorship, BindingResult binding){
			ModelAndView res;
			if(binding.hasErrors()){
				System.out.println("Fallos en: \n" + binding.getAllErrors());
				res = this.createEditModelAndView(sponsorship);
			}else{
				try {
					this.sponsorshipService.save(sponsorship);
					res = new ModelAndView("redirect:list.do");
				} catch (Throwable oops) {
					System.out.println(oops.getCause());
					res = this.createEditModelAndView(sponsorship, "sponsorship.commit.error");
				}
			}
			return res;
		}
		
	//Ancillary Methods---------
		
		protected ModelAndView createEditModelAndView(Sponsorship sponsorship){
			ModelAndView res;
			res= this.createEditModelAndView(sponsorship,null);
			return res;
		}
		
		protected ModelAndView createEditModelAndView(Sponsorship sponsorship, String messageCode){
			ModelAndView res;
			Collection<Tutorial> tutorials = tutorialService.findAll();
			res= new ModelAndView("sponsorship/edit");
			res.addObject("sponsorship", sponsorship);
			res.addObject("tutorials",tutorials);

			res.addObject("message", messageCode);
			
			return res;
		}

}
