package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import services.FixUpTaskService;

@Controller
@RequestMapping("/thymeleaf/fixuptask")
public class FixUpTaskThymeleafController extends AbstractController{

public FixUpTaskThymeleafController(){
	super();
}

	@Autowired
	FixUpTaskService fixUpTaskService;

	@RequestMapping(value = "/thymeleafdemo", method = RequestMethod.GET)
	public String thymeleafdemo() {
		//System.out.println("FixUpTaskThymeleafController: thymeleafdemo() is called");
		return "thymeleafdemo";
	}

}
