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

import services.ActorService;
import services.ApplicationService;
import services.ComplaintService;
import services.ConfigurationService;
import services.CreditCardMakeService;
import services.CustomerService;
import services.FixUpTaskService;
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
				res = new ModelAndView("redirect:configuration.do");
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
		return res;
	}
	//DASHBOARD--------------------------------------------------------
	@RequestMapping(value="/dashboard", method=RequestMethod.GET)
	public ModelAndView dashboard(){
		ModelAndView res;
		
		res = new ModelAndView("admin/dashboard");
		res.addObject("avgFixUpPerUser", fixUpTaskService.getAvgTasksPerCustomer());
		res.addObject("minFixUpPerUser", fixUpTaskService.getMinTasksPerCustomer());
		res.addObject("maxFixUpPerUser", fixUpTaskService.getMaxTasksPerCustomer());
		res.addObject("stdvFixUpPerUser", fixUpTaskService.getStdevTasksPerCustomer());
		
		res.addObject("avgApplicationsPerFixUp", applicationService.getAverageApplicationsPerFixUpTask());
		res.addObject("minApplicationsPerFixUp", applicationService.getMinimumApplicationsPerFixUpTask());
		res.addObject("maxApplicationsPerFixUp", applicationService.getMaximumApplicationsPerFixUpTask());
		res.addObject("stdvApplicationsPerFixUp", applicationService.getStdevApplicationsPerFixUpTask());
		
		res.addObject("avgMaxPricePerFixUp", fixUpTaskService.getAvgMaxPriceTasks());
		res.addObject("minMaxPricePerFixUp", fixUpTaskService.getMinimumMaxPriceTasks());
		res.addObject("maxMaxPricePerFixUp", fixUpTaskService.getMaximumMaxPriceTasks());
		res.addObject("stdvMaxPricePerFixUp", fixUpTaskService.getStdevMaxPriceTasks());
		
		res.addObject("avgPriceOfferedApplication", applicationService.getAveragePriceApplication());
		res.addObject("minPriceOfferedApplication", applicationService.getMinimumPriceApplications());
		res.addObject("maxPriceOfferedApplication", applicationService.getMaximumPriceApplications());
		res.addObject("stdvPriceOfferedApplication", applicationService.getStdevPriceApplications());
		
		res.addObject("avgComplaintsPerFixUp", complaintService.getAvgComplaintsPerTask());
		res.addObject("minComplaintsPerFixUp", complaintService.getMinComplaintsPerTask());
		res.addObject("maxComplaintsPerFixUp", complaintService.getMaxComplaintsPerTask());
		res.addObject("stdvComplaintsPerFixUp", complaintService.getStdevComplaintsPerTask());
		
		res.addObject("avgNotesPerReport", noteService.getAvgNotesPerReport());
		res.addObject("minNotesPerReport", noteService.getMinNotesPerReport());
		res.addObject("maxNotesPerReport", noteService.getMaxNotesPerReport());
		res.addObject("stdvNotesPerReport", noteService.getStdevNotesPerReport());
		
		res.addObject("ratioPendingApplications", applicationService.getRatioPendingApplications());
		res.addObject("ratioAcceptedApplications", applicationService.getRatioAcceptedApplications());
		res.addObject("ratioRejectedApplications", applicationService.getRatioRejectedApplications());
		res.addObject("ratioOvertimeApplications", applicationService.getRatioPendingApplicationsTime());
		res.addObject("ratioFixUpComplaint", fixUpTaskService.getRatioTasksWComplaints());
		res.addObject("topThreeCustomerComplaints",customerService.TopThreeInComplaints());
		res.addObject("topThreeHandyWorkersComplaints", handyWorkerService.TopThreeInComplaints());
		
		res.addObject("customerPublishers10", customerService.getCustomersWMoreTasksThanAvg());
		res.addObject("handyWorkersPublishers10", handyWorkerService.getHandyWorkersWMoreApplicationsThanAvg());
		
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