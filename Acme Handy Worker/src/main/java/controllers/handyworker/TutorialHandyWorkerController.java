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
import services.HandyWorkerService;
import services.TutorialService;
import controllers.AbstractController;
import domain.HandyWorker;
import domain.Tutorial;

@Controller
@RequestMapping("/tutorial/handyWorker")
public class TutorialHandyWorkerController extends AbstractController {

	// Services ----------------------------------------------------------------

	@Autowired
	private TutorialService tutorialService;

	@Autowired
	private HandyWorkerService handyWorkerService;

	// Constructors ------------------------------------------------------------

	public TutorialHandyWorkerController() {
		super();
	}

	// Listing -----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;
		Collection<Tutorial> tutorials;
		
		int id = handyWorkerService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
		
		tutorials = tutorialService.tutorialsByHandyWorker(id);

		result = new ModelAndView("tutorial/list");
		result.addObject("tutorials", tutorials);
		result.addObject("requestURI", "tutorial/list.do");

		return result;
	}

	// Create -----------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView result;
		Tutorial tutorial = tutorialService.create();

		result = this.createEditModelAndView(tutorial);

		return result;
	}

	// Edit -----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int tutorialId) {

		ModelAndView result;
		Tutorial tutorial;
		HandyWorker logged;
		
		tutorial = tutorialService.findOne(tutorialId);
		logged = handyWorkerService.findByPrincipal();
		
		if(tutorial.getHandyWorker().equals(logged)){
			result = createEditModelAndView(tutorial);
		}else{
			result = new ModelAndView("error/access");
		}

		return result;
	}

	// Save -----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Tutorial tutorial, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(tutorial);
		} else{
			try {
				tutorialService.save(tutorial);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(tutorial,"tutorial.commit.error");
			}
		}
		
		return result;
	}

	// Delete -----------------------------------------------------------------

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int tutorialId) {
		ModelAndView result;
		Tutorial tut;
		HandyWorker logged;
		
		tut = tutorialService.findOne(tutorialId);
		logged = handyWorkerService.findByPrincipal();
		
		if(tut.getHandyWorker().equals(logged)){
			try {
				this.tutorialService.delete(tut);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(tut,"tutorial.commit.error");
			}
		}else{
			result = new ModelAndView("error/access");
		}

		return result;
	}
	
	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(Tutorial tutorial) {
		ModelAndView result;

		result = this.createEditModelAndView(tutorial, null);

		return result;
	}

	private ModelAndView createEditModelAndView(final Tutorial tutorial,final String message) {

		ModelAndView result;

		result = new ModelAndView("tutorial/edit");

		result.addObject("tutorial", tutorial);
		result.addObject("message", message);

		return result;
	}
}
