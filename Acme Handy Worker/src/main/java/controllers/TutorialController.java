/*
 * CustomerController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.SectionService;
import services.TutorialService;
import domain.Section;
import domain.Tutorial;

@Controller
@RequestMapping("tutorial/")
public class TutorialController extends AbstractController {
	
	// Services ---------------------------------------------------------------
	
	@Autowired
	private TutorialService tutorialService;
	
	@Autowired
	private SectionService sectionService;
	
	// Constructors -----------------------------------------------------------

	public TutorialController() {
		super();
	}

	// Listing ----------------------------------------------------------------		

	@RequestMapping(value="/list", method=RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Tutorial> tutorials;
		
		tutorials = tutorialService.findAll();

		result = new ModelAndView("tutorial/list");
		result.addObject("tutorials",tutorials);
		result.addObject("requestURI","tutorials/list.do");

		return result;
	}
	
	// Show ------------------------------------------------------------------

	@RequestMapping(value="/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int tutorialId){
		ModelAndView res;
		Tutorial tut;
		Collection<Section> sections;
		
		sections = sectionService.sectionsByTutorial(tutorialId);

		tut = tutorialService.findOne(tutorialId);
		
		res = new ModelAndView("tutorial/show");
		res.addObject("tutorial", tut);
		res.addObject("sections", sections);
		res.addObject("requestURI", "tutorial/show.do");
		
		return res; 
	}
	
}
