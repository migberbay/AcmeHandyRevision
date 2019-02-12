package controllers.referee;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
@RequestMapping("/complaint/referee")
public class ComplaintRefereeController extends AbstractController {

	// Services ----------------------------------------------------------------

	@Autowired
	private ComplaintService complaintService;

	@Autowired
	private RefereeService refereeService;

	@Autowired
	private ReportService reportService;

	// Constructors ------------------------------------------------------------

	public ComplaintRefereeController() {
		super();
	}

	// Listing B-RF 36.1 -------------------------------------------------------

	@RequestMapping(value = "/listNoReport", method = RequestMethod.GET)
	public ModelAndView listComplaintsWithNoReports() {

		ModelAndView result;

		Referee referee = refereeService.findByUserAccountId(LoginService
				.getPrincipal().getId());

		Collection<Complaint> complaints = complaintService
				.getComplaintsWithNoReports();

		result = new ModelAndView("complaint/list");
		result.addObject("complaints", complaints);
		result.addObject("referee", referee);
		result.addObject("requestURI", "complaint/referee/listNoReport.do");

		return result;
	}

	// Listing B-RF 36.2 -------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listComplaintsReferee() {

		ModelAndView result;

		Referee referee = refereeService.findByUserAccountId(LoginService
				.getPrincipal().getId());
		int refereeId = referee.getId();

		Collection<Complaint> complaints = complaintService
				.getComplaintsReferee(refereeId);

		result = new ModelAndView("complaint/list");
		result.addObject("complaints", complaints);
		result.addObject("referee", referee);
		result.addObject("requestURI", "complaint/referee/list.do");

		return result;
	}

	// Show --------------------------------------------------------------------

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int complaintId) {

		ModelAndView result;
		Complaint complaint = complaintService.findOne(complaintId);
		Collection<Report> reports = reportService.getReportsByComplaint(complaintId);
		Referee logged = refereeService.findByUserAccountId(LoginService.getPrincipal().getId());
		boolean access = false;
		
		for(Report r:reportService.getReportsByReferee(logged.getId())){
			if(r.getComplaint().equals(complaint)){
				access = true;
				break;
			}
		}
		
		if(access){
			result = new ModelAndView("complaint/show");
			result.addObject("complaint", complaint);
			result.addObject("reports", reports);
			result.addObject("referee", logged);
			result.addObject("requestURI", "complaint/referee/show.do");
		}else{
			result = new ModelAndView("error/access");
		}
		
		return result;
	}
}
