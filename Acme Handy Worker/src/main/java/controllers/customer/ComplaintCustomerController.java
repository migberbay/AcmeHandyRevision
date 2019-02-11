package controllers.customer;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;

import domain.Complaint;
import domain.Customer;
import domain.Referee;
import domain.Report;

import security.LoginService;
import services.ComplaintService;
import services.CustomerService;
import services.RefereeService;
import services.ReportService;

@Controller
@RequestMapping("/complaint/customer")
public class ComplaintCustomerController extends AbstractController {

	// Services ----------------------------------------------------------------

	@Autowired
	private ComplaintService complaintService;

	@Autowired
	private RefereeService refereeService;
	
	@Autowired
	private ReportService reportService;
	
	@Autowired
	private CustomerService customerService;

	// Constructors ------------------------------------------------------------

	public ComplaintCustomerController() {
		super();
	}

	// Listing -----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;

		Collection<Complaint> complaints = complaintService.findAll();

		result = new ModelAndView("complaint/list");
		result.addObject("complaints", complaints);
		result.addObject("requestURI", "complaint/customer/list.do");

		return result;
	}

	// Show --------------------------------------------------------------------

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int complaintId) {

		ModelAndView result;

		Complaint complaint = complaintService.findOne(complaintId);
		Customer customer;
		Referee referee;
		result = new ModelAndView("complaint/show");

		if(refereeService.findByUserAccountId(LoginService.getPrincipal().getId()) != null){
			referee = refereeService.findByUserAccountId(LoginService.getPrincipal().getId());
			result.addObject("referee", referee);

		}
		if(customerService.findByUserAccountId(LoginService.getPrincipal().getId()) != null){
			customer = customerService.findByUserAccountId(LoginService.getPrincipal().getId());
			if(complaint.getCustomer().equals(customer)) result.addObject("customer", customer);
		}

		Collection<Report> reports = reportService.getReportsByComplaint(complaintId);

		result.addObject("complaint", complaint);
		result.addObject("reports",reports);
		result.addObject("requestURI", "complaint/customer/show.do");

		return result;
	}

	// Create -----------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView result;
		Complaint complaint;

		complaint = complaintService.create();

		result = this.createEditModelAndView(complaint);

		return result;
	}

	// Edit -----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int complaintId) {

		ModelAndView result;

		Complaint complaint = complaintService.findOne(complaintId);

		result = createEditModelAndView(complaint);

		return result;
	}

	// Save -----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Complaint complaint,
			final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			System.out.println(binding.getFieldErrors());
			result = this.createEditModelAndView(complaint);
		} else
			try {
				complaintService.save(complaint);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(complaint,
						"complaint.commit.error");
			}
		return result;
	}

	// Delete -----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Complaint complaint,
			final BindingResult binding) {
		ModelAndView result;

		try {
			this.complaintService.delete(complaint);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(complaint,
					"complaint.commit.error");
		}

		return result;
	}

	// Cancel -----------------------------------------------------------------

	@RequestMapping(value = "/cancel", method = RequestMethod.GET)
	public ModelAndView cancel(@RequestParam final int complaintId) {
		ModelAndView result;

		Complaint complaint = this.complaintService.findOne(complaintId);

		try {
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(complaint,
					"complaint.commit.error");
		}
		return result;
	}

	protected ModelAndView createEditModelAndView(Complaint complaint) {
		ModelAndView result;

		result = this.createEditModelAndView(complaint, null);

		return result;
	}

	private ModelAndView createEditModelAndView(final Complaint complaint,
			final String message) {

		ModelAndView result;

		result = new ModelAndView("complaint/edit");

		Collection<String> attachments = complaint.getAttachments();

		result.addObject("complaint", complaint);
		result.addObject("attachments", attachments);
		result.addObject("message", message);

		return result;
	}

}
