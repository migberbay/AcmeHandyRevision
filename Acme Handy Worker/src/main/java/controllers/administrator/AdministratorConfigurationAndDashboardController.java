package controllers.administrator;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ActorService;
import services.ApplicationService;
import services.ComplaintService;
import services.ConfigurationService;
import services.CreditCardMakeService;
import services.CustomerEndorsementService;
import services.CustomerService;
import services.FixUpTaskService;
import services.HandyWorkerEndorsementService;
import services.HandyWorkerService;
import services.NoteService;
import services.WordService;
import controllers.AbstractController;
import domain.Configuration;
import domain.CreditCardMake;
import domain.Word;

@Controller
@RequestMapping("/admin/admin")
public class AdministratorConfigurationAndDashboardController extends AbstractController {
	
	//Services
	@Autowired
	private HandyWorkerService handyWorkerService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private NoteService noteService;
	
	@Autowired
	private ComplaintService complaintService;
	
	@Autowired
	private ApplicationService applicationService;
	
	@Autowired
	private FixUpTaskService fixUpTaskService;
	
	@Autowired
	private ConfigurationService configurationService;
	
	@Autowired
	private CreditCardMakeService creditCardMakeService;
	
	@Autowired
	private WordService	wordService;
	
	@Autowired
	private ActorService actorService;

	@Autowired
	private CustomerEndorsementService customerEndorsementService;
	
	@Autowired
	private HandyWorkerEndorsementService handyWorkerEndorsementService;
	// Constructors -----------------------------------------------------------

	public AdministratorConfigurationAndDashboardController() {
		super();
	}
	
	//Edit-------------------------------------------------------------
	@RequestMapping(value="/configuration", method=RequestMethod.GET)
	public ModelAndView edit(){
		ModelAndView res;
		//como solo debe existir una se puede hacer esto.
		Configuration c = (Configuration) configurationService.findAll().toArray()[0];
		
		res = this.createEditModelAndView(c);
		return res;
	}
	
	//Save-------------------------------------------------------------	
	@RequestMapping(value="/configuration", method=RequestMethod.POST, params="save")
	public ModelAndView save(@Valid Configuration config, BindingResult binding){
		ModelAndView res;
		
		
		if(binding.hasErrors()){
			res = createEditModelAndView(config);
		}else{
			try {
				configurationService.save(config);
				res = new ModelAndView("redirect:/welcome/index.do");
			} catch (Throwable e) {
				res = createEditModelAndView(config, "admin.commit.error");
				
				//TODO: añadir el commit error al message.properties de admin.
			}
		}
		return res;
	}
	
	//Add and remove methods ------------------------------------------
	
	// word------------------------------------------------------------
	@RequestMapping(value="/addSpamWord", method=RequestMethod.POST, params="save")
	public ModelAndView addSpam(@Valid Word word, BindingResult binding){
		ModelAndView res;
		
		Configuration config = (Configuration) configurationService.findAll().toArray()[0];		
		
		if(binding.hasErrors()){
			res = createEditModelAndView(config);
		}else{
			try {
				Word saved = wordService.save(word);
				
				List<Word> aux = config.getspamWords();
				aux.add(saved);
				config.setSpamWords(aux);
				Configuration savedc = configurationService.save(config);
				
				res = createEditModelAndView(savedc);
			} catch (Throwable e) {
				res = createEditModelAndView(config, "admin.commit.error");
				System.out.println("he cogido esto para ti: "+e);
			}
		}
		return res;
	}
	
	@RequestMapping(value="/deleteSpam", method=RequestMethod.GET)
	public ModelAndView removeSpam(@RequestParam int wordId){
		ModelAndView res;
		if (!LoginService.hasRole("ADMIN")) {
			res = new ModelAndView("error/access");
		}else{
		
		Configuration config = (Configuration) configurationService.findAll().toArray()[0];
			try {
				List<Word> aux = config.getspamWords();
				aux.remove(wordService.findOne(wordId));
				config.setSpamWords(aux);
				Configuration saved = configurationService.save(config);
				
				wordService.delete(wordService.findOne(wordId));
				res = createEditModelAndView(saved);
			} catch (Throwable e) {
				res = createEditModelAndView(config, "admin.commit.error");
				
			}
		}
		return res;
	}
	
	
	// make------------------------------------------------------------
	@RequestMapping(value="/addCreditCard", method=RequestMethod.POST, params="save")
	public ModelAndView addMake(@Valid CreditCardMake make, BindingResult binding){
		ModelAndView res;
		
		Configuration config = (Configuration) configurationService.findAll().toArray()[0];
		
		if(binding.hasErrors()){
			res = createEditModelAndView(config);
		}else{
			try {
				
				CreditCardMake saved = creditCardMakeService.save(make);
				
				List<CreditCardMake> aux = config.getCreditCardMakes();
				aux.add(saved);
				config.setCreditCardMakes(aux);
				Configuration savedc = configurationService.save(config);
				
				res = createEditModelAndView(savedc);
				
			} catch (Throwable e) {
				res = createEditModelAndView(config, "admin.commit.error");
				
			}
		}
		return res;
	}
	
	@RequestMapping(value="/deleteMake", method=RequestMethod.GET)
	public ModelAndView removeMake(@RequestParam int makeId){
		ModelAndView res;
		if (!LoginService.hasRole("ADMIN")) {
			res = new ModelAndView("error/access");
		}else{
		Configuration config = (Configuration) configurationService.findAll().toArray()[0];
			try {
				
				List<CreditCardMake> aux = config.getCreditCardMakes();
				aux.remove(creditCardMakeService.findOne(makeId));
				config.setCreditCardMakes(aux);
				Configuration savedc = configurationService.save(config);
				
				res = createEditModelAndView(savedc);
			} catch (Throwable e) {
				res = createEditModelAndView(config, "admin.commit.error");
			}
		}
		return res;
	}
	//DASHBOARD--------------------------------------------------------
	@RequestMapping(value="/dashboard", method=RequestMethod.GET)
	public ModelAndView dashboard(){
		ModelAndView res;
		
		res = new ModelAndView("admin/dashboard");
		res.addObject("customersScore", customerEndorsementService.getScoreCustomerEndorsement());
		res.addObject("handyworkersScore", handyWorkerEndorsementService.getScoreHandyWorkerEndorsement());
		
		res.addObject("avgFixUpPerUser", Math.round(fixUpTaskService.getAvgTasksPerCustomer() * 100d) / 100d);//Double
		res.addObject("minFixUpPerUser", fixUpTaskService.getMinTasksPerCustomer());
		res.addObject("maxFixUpPerUser", fixUpTaskService.getMaxTasksPerCustomer());
		res.addObject("stdvFixUpPerUser", Math.round(fixUpTaskService.getStdevTasksPerCustomer() * 100d) / 100d);//Double
		
		res.addObject("avgApplicationsPerFixUp", Math.round(applicationService.getAverageApplicationsPerFixUpTask() * 100d) / 100d );//Double
		res.addObject("minApplicationsPerFixUp", applicationService.getMinimumApplicationsPerFixUpTask());
		res.addObject("maxApplicationsPerFixUp", applicationService.getMaximumApplicationsPerFixUpTask());
		res.addObject("stdvApplicationsPerFixUp", Math.round(applicationService.getStdevApplicationsPerFixUpTask() * 100d) / 100d );//Double
		
		res.addObject("avgMaxPricePerFixUp", Math.round(fixUpTaskService.getAvgMaxPriceTasks() * 100d) / 100d );//Double
		res.addObject("minMaxPricePerFixUp", fixUpTaskService.getMinimumMaxPriceTasks());
		res.addObject("maxMaxPricePerFixUp", fixUpTaskService.getMaximumMaxPriceTasks());
		res.addObject("stdvMaxPricePerFixUp", Math.round(fixUpTaskService.getStdevMaxPriceTasks() * 100d) / 100d );//Double
		
		res.addObject("avgPriceOfferedApplication", Math.round(applicationService.getAveragePriceApplication() * 100d) / 100d );//Double
		res.addObject("minPriceOfferedApplication", applicationService.getMinimumPriceApplications());
		res.addObject("maxPriceOfferedApplication", applicationService.getMaximumPriceApplications());
		res.addObject("stdvPriceOfferedApplication", Math.round(applicationService.getStdevPriceApplications() * 100d) / 100d );//Double
		
		res.addObject("avgComplaintsPerFixUp", Math.round(complaintService.getAvgComplaintsPerTask() * 100d) / 100d );//Double
		res.addObject("minComplaintsPerFixUp", complaintService.getMinComplaintsPerTask());
		res.addObject("maxComplaintsPerFixUp", complaintService.getMaxComplaintsPerTask());
		res.addObject("stdvComplaintsPerFixUp", Math.round(complaintService.getStdevComplaintsPerTask() * 100d) / 100d );//Double
		
		res.addObject("avgNotesPerReport", Math.round(noteService.getAvgNotesPerReport() * 100d) / 100d );
		res.addObject("minNotesPerReport", noteService.getMinNotesPerReport());
		res.addObject("maxNotesPerReport", noteService.getMaxNotesPerReport());
		res.addObject("stdvNotesPerReport", Math.round(noteService.getStdevNotesPerReport() * 100d) / 100d );
		
		res.addObject("ratioPendingApplications", Math.round(applicationService.getRatioPendingApplications() * 100d) / 100d );
		res.addObject("ratioAcceptedApplications", Math.round(applicationService.getRatioAcceptedApplications() * 100d) / 100d );
		res.addObject("ratioRejectedApplications", Math.round(applicationService.getRatioRejectedApplications() * 100d) / 100d );
		res.addObject("ratioOvertimeApplications", Math.round(applicationService.getRatioPendingApplicationsTime() * 100d) / 100d );
		res.addObject("ratioFixUpComplaint", Math.round(fixUpTaskService.getRatioTasksWComplaints() * 100d) / 100d );
		res.addObject("topThreeCustomerComplaints",customerService.TopThreeInComplaints());
		res.addObject("topThreeHandyWorkersComplaints", handyWorkerService.TopThreeInComplaints());
		
		res.addObject("customerPublishers10", customerService.getCustomersWMoreTasksThanAvg());
		res.addObject("handyWorkersPublishers10", handyWorkerService.getHandyWorkersWMoreApplicationsThanAvg());
		
		return res;
	}
	
	// SCORE
	
	//DASHBOARD--------------------------------------------------------
	@RequestMapping(value="/score", method=RequestMethod.GET)
	public ModelAndView score(){
		ModelAndView res;
		
		res = new ModelAndView("admin/score");
		res.addObject("customersScore", customerEndorsementService.getScoreCustomerEndorsement());
		res.addObject("handyworkersScore", handyWorkerEndorsementService.getScoreHandyWorkerEndorsement());
		return res;
	}
	
	
	
	//Helper methods---------------------------------------------------
	protected ModelAndView createEditModelAndView(Configuration config){
		ModelAndView res;
		res = createEditModelAndView(config, null);
		return res;
	}
	protected ModelAndView createEditModelAndView(Configuration config, String messageCode){
		ModelAndView res;
		//TODO:add word and creditcardmake to the view.
		Word word = wordService.create();
		word.setType("SPAM");
		
		CreditCardMake make = creditCardMakeService.create();
		
		res = new ModelAndView("admin/configuration");
		res.addObject("configuration", config);
		res.addObject("creditCardMake", make);
		res.addObject("spamWord", word);
		res.addObject("errorMessage", messageCode);
		
		return res;
	}
	
}