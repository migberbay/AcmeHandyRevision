package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import security.LoginService;
import services.ApplicationService;
import services.ComplaintService;
import services.FixUpTaskService;
import services.HandyWorkerService;
import services.WorkPlanPhaseService;
import controllers.AbstractController;
import domain.Application;
import domain.Complaint;
import domain.Customer;
import domain.FixUpTask;
import domain.HandyWorker;
import domain.WorkPlanPhase;

@Controller
@RequestMapping("fixUpTask/")
public class FixUpTaskController extends AbstractController{

public FixUpTaskController(){
	super();
}

	@Autowired
	FixUpTaskService fixUpTaskService;
	
	@Autowired
	ComplaintService complaintService;
	
	@Autowired
	WorkPlanPhaseService workPlanPhaseService;
	
	@Autowired
	ApplicationService applicationService;
	
	@Autowired
	HandyWorkerService handyWorkerService;
	
	//Listing---
	@RequestMapping(value="/list", method = RequestMethod.GET)
	public ModelAndView list(){
		
		ModelAndView res;
		Collection<FixUpTask> fixUpTasks;
		fixUpTasks = fixUpTaskService.findAll();
		
		res = new ModelAndView("fixUpTask/list");
		res.addObject("fixUpTasks", fixUpTasks);
		res.addObject("requestURI", "fixUpTask/list.do");
		return res; 
	}
	
	// Showing ----------------------------------------------------------------
		@RequestMapping(value="/show", method = RequestMethod.GET)
		public ModelAndView show(@RequestParam final int fixUpTaskId){
			// Comprobar que la task no tiene ninguna aplicación aceptada del hw logeado
			ModelAndView res;
			FixUpTask fixUpTask;
			Collection<Complaint> complaints;
			Collection<WorkPlanPhase> workPlanPhases;
			Collection<Application> applications;
			Boolean app = false, haw=false;
			Authority n = new Authority();
			n.setAuthority("HANDYWORKER");
			HandyWorker hw = handyWorkerService.findByPrincipal();
			System.out.println(hw);
			complaints = complaintService.getComplaintsFixUpTask(fixUpTaskId);
			fixUpTask = fixUpTaskService.findOne(fixUpTaskId);
			workPlanPhases = workPlanPhaseService.findByFixUpTaskId(fixUpTaskId);
			if(LoginService.getPrincipal().getAuthorities().contains(n)){
				app = true;
				applications = applicationService.findApplicationsAccepted(hw.getId(), fixUpTaskId);
				if(applications.isEmpty()) app=false; // Si no hay aplicaciones aceptadas, quiere decir que no se podrán añadir fases hasta que haya una  por el hw
				else{
					for(WorkPlanPhase w: workPlanPhases){
						if(hw != null && w.getHandyWorker() == hw) haw=true;
						if(w.getHandyWorker() != hw){
							app=false;
							break;
						}
					}
				}
			}

			String fullName = fixUpTask.getCustomer().getName()+" " + fixUpTask.getCustomer().getMiddleName() + " "+ fixUpTask.getCustomer().getSurname();
			
			res = new ModelAndView("fixUpTask/show");
			res.addObject("fixUpTask", fixUpTask);
			res.addObject("complaints",complaints);
			res.addObject("workPlanPhases",workPlanPhases);
			res.addObject("fullName",fullName);
			res.addObject("app",app);
			res.addObject("hw",haw);
			res.addObject("requestURI", "fixUpTask/show.do");
			return res; 
		}

	
	//Ancillary Methods---------
		
		protected ModelAndView createEditModelAndView(FixUpTask fixUpTask){
			ModelAndView res;
			res= this.createEditModelAndView(fixUpTask,null);
			return res;
		}
		
		protected ModelAndView createEditModelAndView(FixUpTask fixUpTask, String messageCode){
			ModelAndView res;
			res= new ModelAndView("fixUpTask/edit");
			res.addObject("fixUpTask", fixUpTask);
			res.addObject("message", messageCode);
			
			return res;
		}

}
