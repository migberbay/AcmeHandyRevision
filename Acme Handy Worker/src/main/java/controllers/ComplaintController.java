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
import services.CustomerService;
import services.FixUpTaskService;
import services.HandyWorkerService;
import services.RefereeService;
import services.ReportService;
import services.WorkPlanPhaseService;
import controllers.AbstractController;
import domain.Application;
import domain.Complaint;
import domain.Customer;
import domain.FixUpTask;
import domain.HandyWorker;
import domain.Report;
import domain.WorkPlanPhase;

@Controller
@RequestMapping("complaint/")
public class ComplaintController extends AbstractController{

public ComplaintController(){
	super();
}
	
	@Autowired
	ComplaintService complaintService;
	
	@Autowired
	ReportService reportService;
	
	@Autowired
	RefereeService refereeService;
	
	@Autowired
	CustomerService customerService;
	
	// Showing ----------------------------------------------------------------
		@RequestMapping(value="/show", method = RequestMethod.GET)
		public ModelAndView show(@RequestParam final int complaintId){
			ModelAndView result;

			Complaint complaint = complaintService.findOne(complaintId);
			Customer customer;
			result = new ModelAndView("complaint/show");

			Collection<Report> reports = reportService.getReportsByComplaint(complaintId);
			if(refereeService.findByUserAccountId(LoginService.getPrincipal().getId()) == null){
				reports = reportService.getFinalReportsByComplaint(complaintId);
			}
			if(customerService.findByUserAccountId(LoginService.getPrincipal().getId()) != null){
				customer = customerService.findByUserAccountId(LoginService.getPrincipal().getId());
				if(complaint.getCustomer().equals(customer)) result.addObject("customer", customer);
			}


			result.addObject("complaint", complaint);
			result.addObject("reports",reports);
			result.addObject("referee", refereeService.findByUserAccountId(LoginService.getPrincipal().getId()));
			return result; 
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
