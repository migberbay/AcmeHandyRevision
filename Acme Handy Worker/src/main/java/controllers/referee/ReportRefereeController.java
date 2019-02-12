package controllers.referee;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ComplaintService;
import services.RefereeService;
import services.ReportService;
import controllers.AbstractController;
import domain.Complaint;
import domain.Referee;
import domain.Report;

@Controller
@RequestMapping("/report/referee")
public class ReportRefereeController extends AbstractController {

	// Services ----------------------------------------------------------------

	@Autowired
	private ComplaintService complaintService;
	
	@Autowired
	private ReportService reportService;

	@Autowired
	private RefereeService refereeService;

	// Constructors ------------------------------------------------------------

	public ReportRefereeController() {
		super();
	}

	// Create --------------------------------------------------------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int complaintId) {
		ModelAndView result;
		Report report;
		Complaint complaint;
		report = this.reportService.create();
		complaint = complaintService.findOne(complaintId);
		report.setComplaint(complaint);
		
		result = this.createEditModelAndView(report);

		return result;
	}

	// Edit ---------------------------------------------------------------------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int reportId) {
		ModelAndView result;
		Report report;
		Referee logged;
		
		report = this.reportService.findOne(reportId);
		logged = refereeService.findByUserAccountId(LoginService.getPrincipal().getId());
		
		if(report.getReferee().equals(logged)){
			result = this.createEditModelAndView(report);
		}else{
			result = new ModelAndView("error/access");
		}	

		return result;
	}
	
	//Delete edit -------------------------------------------------------------------------------------------------------------------
	
	@RequestMapping(value="/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Report report, BindingResult binding){
		
		ModelAndView res;
		
		try{
			this.reportService.delete(report);
			res= new ModelAndView("redirect:complaint/show.do?complaintId=" + report.getComplaint().getId());
		} catch (Throwable oops) {
			res = createEditModelAndView(report,"report.commit.error");
		}
		return res;
	}
	
	//Delete list ----------------------------------------------------------------------------------------------------------------
	
	@RequestMapping(value="/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int reportId){
		
		ModelAndView res;
		Report report;
		Referee logged;
		
		report = this.reportService.findOne(reportId);
		logged = refereeService.findByUserAccountId(LoginService.getPrincipal().getId());
		
		if(report.getReferee().equals(logged)){
			try{
				this.reportService.delete(report);
				res= new ModelAndView("redirect:/complaint/show.do?complaintId=" + report.getComplaint().getId());
			} catch (Throwable oops) {
				res = createEditModelAndView(report,"report.commit.error");
			}
		}else{
			res = new ModelAndView("error/access");
		}
		return res;
	}
	

	//Save ----------------------------------------------------------------------------------------------------------------------
	
	@RequestMapping(value="/edit", method=RequestMethod.POST, params="save")
	public ModelAndView save(@Valid Report report, BindingResult binding){
		ModelAndView res;
		if(binding.hasErrors()){
			System.out.println("Fallos en: \n" + binding.getAllErrors());
			res = this.createEditModelAndView(report);
		}else{
			try {
				this.reportService.save(report);
				res = new ModelAndView("redirect:/complaint/show.do?complaintId=" + report.getComplaint().getId());
			} catch (Throwable oops) {
				System.out.println(oops.getCause());
				res = this.createEditModelAndView(report, "report.commit.error");
			}
		}
		return res;
	}
	
	//Ancillary Methods ---------------------------------------------------------------------------------------------------------
	
	protected ModelAndView createEditModelAndView(Report report){
		ModelAndView res;
		res= this.createEditModelAndView(report,null);
		return res;
	}
			
	protected ModelAndView createEditModelAndView(Report report, String messageCode){
		ModelAndView res;
		res= new ModelAndView("report/edit");
		res.addObject("report", report);
		res.addObject("message", messageCode);
				
		return res;
	}
}
