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

import services.HandyWorkerService;
import services.SectionService;
import services.TutorialService;
import controllers.AbstractController;
import domain.HandyWorker;
import domain.Section;

@Controller
@RequestMapping("/handyWorker/section")
public class SectionHandyWorkerController extends AbstractController {

	// Services ----------------------------------------------------------------

	@Autowired
	private SectionService sectionService;
	
	@Autowired
	private HandyWorkerService hwService;
	
	@Autowired
	private TutorialService tutorialService;

	// Constructors ------------------------------------------------------------

	public SectionHandyWorkerController() {
		super();
	}

	// Listing -----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int tutorialId) {

		ModelAndView result;
		Collection<Section> sections;
		
		sections = sectionService.sectionsByTutorial(tutorialId);

		result = new ModelAndView("tutorial/list");
		result.addObject("sections", sections);
		result.addObject("requestURI", "handyWorker/sections/list.do");

		return result;
	}

	// Create -----------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int tutorialId) {

		ModelAndView result;
		Section section = sectionService.create();
		section.setTutorial(tutorialService.findOne(tutorialId));

		result = this.createEditModelAndView(section);

		return result;
	}

	// Edit -----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int sectionId) {

		ModelAndView result;
		Section section;
		HandyWorker logged;
		
		section = sectionService.findOne(sectionId);
		logged = hwService.findByPrincipal();
		
		if(section.getTutorial().getHandyWorker().equals(logged)){
			result = createEditModelAndView(section);
		}else{
			result = new ModelAndView("error/access");
		}
		

		return result;
	}

	// Save -----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Section section, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(section);
		} else{
			try {
				sectionService.save(section);
				result = new ModelAndView("redirect:/tutorial/show.do?tutorialId=" + section.getTutorial().getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(section,"section.commit.error");
			}
		}
		
		return result;
	}

	// Delete -----------------------------------------------------------------

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int sectionId) {
		ModelAndView result;
		Section section;
		HandyWorker logged;
		
		section = sectionService.findOne(sectionId);
		logged = hwService.findByPrincipal();
		
		if(section.getTutorial().getHandyWorker().equals(logged)){
			try {
				this.sectionService.delete(section);
				result = new ModelAndView("redirect:/tutorial/show.do?tutorialId=" + section.getTutorial().getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(section,"section.commit.error");
			}
		}else{
			result = new ModelAndView("error/access");
		}
		
		return result;
	}
	
	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(Section section) {
		ModelAndView result;

		result = this.createEditModelAndView(section, null);

		return result;
	}

	private ModelAndView createEditModelAndView(final Section section,final String message) {

		ModelAndView result;

		result = new ModelAndView("section/edit");

		result.addObject("section", section);
		result.addObject("message", message);

		return result;
	}
}
