package controllers.handyworker;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.FixUpTaskService;
import services.HandyWorkerService;
import services.WorkPlanPhaseService;
import controllers.AbstractController;
import domain.FixUpTask;
import domain.WorkPlanPhase;

@Controller
@RequestMapping("workPlanPhase/handyWorker/")
public class WorkPlanPhaseHandyWorkerController extends AbstractController{

public WorkPlanPhaseHandyWorkerController(){
	super();
}

	@Autowired
	WorkPlanPhaseService workPlanPhaseService;
	
	@Autowired
	HandyWorkerService handyWorkerService;
	
	@Autowired
	FixUpTaskService fixUpTaskService;

	// Create -----------------------------------------------------------------

		@RequestMapping(value = "/create", method = RequestMethod.GET)
		public ModelAndView create(@RequestParam final int fixUpTaskId) {
			ModelAndView result;
			WorkPlanPhase workPlanPhase;
			FixUpTask fixUpTask;
			workPlanPhase = this.workPlanPhaseService.create();
			fixUpTask = fixUpTaskService.findOne(fixUpTaskId);
			workPlanPhase.setFixUpTask(fixUpTask);
			
			result = this.createEditModelAndView(workPlanPhase);

			return result;
		}

		// Edit -----------------------------------------------------------------

		@RequestMapping(value = "/edit", method = RequestMethod.GET)
		public ModelAndView edit(@RequestParam final int workPlanPhaseId) {
			ModelAndView result;
			WorkPlanPhase workPlanPhase;

			workPlanPhase = this.workPlanPhaseService.findOne(workPlanPhaseId);
			result = this.createEditModelAndView(workPlanPhase);

			return result;
		}
		
	//Delete edit----------------------------------------------------------------------------------------------------------------------------------------
		
		@RequestMapping(value="/edit", method = RequestMethod.POST, params = "delete")
		public ModelAndView delete(WorkPlanPhase workPlanPhase, BindingResult binding){
			
			ModelAndView res;
			
			try{
				this.workPlanPhaseService.delete(workPlanPhase);
				res= new ModelAndView("redirect:fixUpTask/show.do?fixUpTaskId=" + workPlanPhase.getFixUpTask().getId());
			} catch (Throwable oops) {
				res = createEditModelAndView(workPlanPhase,"workplan.commit.error");
			}
			return res;
		}
		
	//Delete list ----------------------------------------------------------------------------------------------------------------------------------------
	
		@RequestMapping(value="/delete", method = RequestMethod.GET)
		public ModelAndView delete(@RequestParam final int workPlanPhaseId){
			
			ModelAndView res;
			WorkPlanPhase workPlanPhase;
			workPlanPhase = workPlanPhaseService.findOne(workPlanPhaseId);
			
			try{
				this.workPlanPhaseService.delete(workPlanPhase);
				res= new ModelAndView("redirect:/fixUpTask/show.do?fixUpTaskId=" + workPlanPhase.getFixUpTask().getId());
			} catch (Throwable oops) {
				res = createEditModelAndView(workPlanPhase,"workplan.commit.error");
			}
			return res;
		}
		
	//Save---------------------------------------------------------------	
		
		@RequestMapping(value="/edit", method=RequestMethod.POST, params="save")
		public ModelAndView save(@Valid WorkPlanPhase workPlanPhase, BindingResult binding){
			ModelAndView res;
			if(binding.hasErrors()){
				System.out.println("Fallos en: \n" + binding.getAllErrors());
				res = this.createEditModelAndView(workPlanPhase);
			}else{
				try {
					this.workPlanPhaseService.save(workPlanPhase);
					res = new ModelAndView("redirect:/fixUpTask/show.do?fixUpTaskId=" + workPlanPhase.getFixUpTask().getId());
				} catch (Throwable oops) {
					System.out.println(oops.getCause());
					res = this.createEditModelAndView(workPlanPhase, "workplan.commit.error");
				}
			}
			return res;
		}
		
	//Ancillary Methods---------
		
		protected ModelAndView createEditModelAndView(WorkPlanPhase workPlanPhase){
			ModelAndView res;
			res= this.createEditModelAndView(workPlanPhase,null);
			return res;
		}
		
		protected ModelAndView createEditModelAndView(WorkPlanPhase workPlanPhase, String messageCode){
			ModelAndView res;
			res= new ModelAndView("workPlanPhase/edit");
			res.addObject("workPlanPhase", workPlanPhase);
			res.addObject("message", messageCode);
			
			return res;
		}

}
