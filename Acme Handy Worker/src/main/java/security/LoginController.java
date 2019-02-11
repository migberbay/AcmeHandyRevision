/*
 * LoginController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package security;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;

import controllers.AbstractController;

@Controller
@RequestMapping("/security")
public class LoginController extends AbstractController {

	// Supporting services ----------------------------------------------------

	@Autowired
	LoginService	service;
	
	@Autowired 
	ActorService actorService;
	
	@Autowired 
	UserAccountService accountService;


	// Constructors -----------------------------------------------------------

	public LoginController() {
		super();
	}

	// Login ------------------------------------------------------------------

	@RequestMapping("/login")
	public ModelAndView login(@Valid final Credentials credentials, final BindingResult bindingResult, @RequestParam(required = false) final boolean showError) {
		Assert.notNull(credentials);
		Assert.notNull(bindingResult);
//		boolean banned = false;

		System.out.println(credentials.getUsername()+" "+credentials.getPassword());
		ModelAndView result;
//		String username = credentials.getUsername();
//		for (UserAccount ua : accountService.findAll()) {
//			if(ua.getUsername().equals(username)){
//				if(actorService.getByUserAccountId(ua).getIsBanned()){
//					banned = true;
//				}
//			}
//		}
		result = new ModelAndView("security/login");
		result.addObject("credentials", credentials);
		result.addObject("showError", showError);
//		if(banned){
//			credentials.setJ_username("YOU ARE HELLA BANNED BROTHER");
//			result = new ModelAndView("redirect:login.do?showError=true");
//		}

		return result;
	}

	// LoginFailure -----------------------------------------------------------

	@RequestMapping("/loginFailure")
	public ModelAndView failure() {
		ModelAndView result;

		result = new ModelAndView("redirect:login.do?showError=true");

		return result;
	}


}
