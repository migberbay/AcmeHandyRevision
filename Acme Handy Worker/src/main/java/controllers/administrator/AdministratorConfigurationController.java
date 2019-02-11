package controllers.administrator;

import controllers.AbstractController;
import domain.Configuration;
import domain.CreditCardMake;
import domain.WelcomeMessage;
import domain.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import services.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/administrator/configuration")
public class AdministratorConfigurationController extends AbstractController {

	//Services

	@Autowired
	private ConfigurationService configurationService;

	@Autowired
	private WordService wordService;

	@Autowired
	private CreditCardMakeService creditCardMakeService;

	@Autowired
	private ActorService actorService;

	@Autowired
	private WelcomeMessageService welcomeMessageService;


	// Constructors -----------------------------------------------------------

	public AdministratorConfigurationController() {
		super();
	}

	//Edit-------------------------------------------------------------
	//TODO
	@RequestMapping(value="/edit", method=RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView res = new ModelAndView();
		return res;
	}

	//Ancillary methods---------------------------------------------------
	protected ModelAndView createEditModelAndView(Configuration config){
		ModelAndView res;
		res = createEditModelAndView(config, null);
		return res;
	}
	protected ModelAndView createEditModelAndView(Configuration configuration, String messageCode){
		ModelAndView res;
		String configurationSubmitURI, welcomeMessageSubmitURI, wordSubmitURI, creditCardMakeSubmitURI;
		Collection<WelcomeMessage> welcomeMessages;

		Collection<Word> spamWords, positiveWords, negativeWords;

		configurationSubmitURI = "administrator/configuration/edit.do";
		welcomeMessageSubmitURI = "administrator/configuration/welcomeMessage/save.do";
		wordSubmitURI = "administrator/configuration/word/save.do";
		creditCardMakeSubmitURI = "administrator/configuration/creditCardMake/save.do";


		 welcomeMessages = welcomeMessageService.findAll();

		spamWords = wordService.findSpamWords();
		positiveWords = wordService.findPositiveWords();
		negativeWords = wordService.findNegativeWords();

		Collection<CreditCardMake> creditCardMakes = creditCardMakeService.findAll();

		res = new ModelAndView("administrator/configuration");

		res.addObject("configuration", configuration);
		res.addObject("message", messageCode);

		return res;
	}
}