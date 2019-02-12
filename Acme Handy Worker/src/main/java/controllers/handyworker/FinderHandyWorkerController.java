package controllers.handyworker;

import controllers.AbstractController;
import domain.Finder;
import domain.FixUpTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import services.WarrantyService;
import services.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashSet;

@Controller
@RequestMapping("handyWorker/finder/")
public class FinderHandyWorkerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private FinderService finderService;

	@Autowired
	private FixUpTaskService fixUpTaskService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private WarrantyService warrantyService;

	// filter: change filter parameters and lists fix-up tasks -------------------------------------

	@RequestMapping(value="/filter", method= RequestMethod.GET)
	public ModelAndView filter() {
		ModelAndView result;
		result = createEditModelAndView(finderService.findByPrincipal());
		System.out.println("Finder Results:" + finderService.findByPrincipal().getFixUpTasks());
		return result;
	}

	@RequestMapping(value="/filter", method= RequestMethod.POST, params = "filter")
	public ModelAndView filter(@Valid Finder finder, final BindingResult binding) {
		ModelAndView result;
		if(binding.hasErrors()){
			result = createEditModelAndView(finder);
		} else {
			try {
				Finder updatedFinder = finderService.save(finder);
				result = createEditModelAndView(updatedFinder);
			} catch (final Throwable oops) {
				oops.printStackTrace();
				result = createEditModelAndView(finder,
						"finder.commit.error");
			}
		}
		return result;
	}

	//Helper methods---------------------------------------------------
	
	protected ModelAndView createEditModelAndView(Finder finder){
		ModelAndView res;
		res = createEditModelAndView(finder, null);
		return res;
	}
	protected ModelAndView createEditModelAndView(Finder finder, String messageCode){
		ModelAndView res;
		Collection<FixUpTask> fixUpTasks = new HashSet<FixUpTask>();
		String cachedMessageCode = null;

		res = new ModelAndView("finder/edit");

		if(finderService.findOne(finder.getId()).getMoment() == null
				|| finderService.isVoid(finder)
				|| finderService.isExpired(finder)){
			fixUpTasks.addAll(fixUpTaskService.findAll());
		}else{
			fixUpTasks.addAll(finderService.findOne(finder.getId()).getFixUpTasks());
			cachedMessageCode = "finder.cachedMessage";
		}
		res.addObject("cachedMessage", cachedMessageCode);
		res.addObject("finder",finder);
		res.addObject("categories", categoryService.findAll());
		res.addObject("warranties", warrantyService.findAll());
		res.addObject("fixUpTasks", fixUpTasks);
		res.addObject("message", messageCode);

		return res;
	}
}
