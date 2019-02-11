package controllers.customer;

import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ApplicationService;
import services.CategoryService;
import services.CustomerService;
import services.FixUpTaskService;
import services.WarrantyService;
import controllers.AbstractController;
import domain.Application;
import domain.Category;
import domain.Customer;
import domain.FixUpTask;
import domain.Warranty;
import domain.WorkPlanPhase;

@Controller
@RequestMapping("fixUpTask/customer/")
public class FixUpTaskCustomerController extends AbstractController{

public FixUpTaskCustomerController(){
	super();
}

	@Autowired
	FixUpTaskService fixUpTaskService;
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	WarrantyService warrantyService;
	
	@Autowired
	ApplicationService applicationService;

	//Listing---
		@RequestMapping(value="/list", method = RequestMethod.GET)
		public ModelAndView list(){
			
			ModelAndView res;
			Collection<FixUpTask> fixUpTasks;
			Customer c;
			c = customerService.findByUserAccountId(LoginService.getPrincipal().getId());
			fixUpTasks = fixUpTaskService.getFixUpTasksCustomer();
			
			res = new ModelAndView("fixUpTask/list");
			res.addObject("fixUpTasks", fixUpTasks);
			res.addObject("customer",c);
			res.addObject("requestURI", "fixUpTask/customer/list.do");
			return res; 
		}

		// Create -----------------------------------------------------------------

		@RequestMapping(value = "/create", method = RequestMethod.GET)
		public ModelAndView create() {
			ModelAndView result;
			FixUpTask fixUpTask;
			fixUpTask = this.fixUpTaskService.create();
			
			Customer logged = customerService.findByUserAccountId(LoginService.getPrincipal().getId());

			if(fixUpTask.getCustomer().equals(logged)){
				result = this.createEditModelAndView(fixUpTask);
			}else{
				result = new ModelAndView("error/access");
			}

			return result;
		}

		// Edit -----------------------------------------------------------------

		@RequestMapping(value = "/edit", method = RequestMethod.GET)
		public ModelAndView edit(@RequestParam final int fixUpTaskId) {
			ModelAndView result;
			FixUpTask fixUpTask;

			fixUpTask = this.fixUpTaskService.findOne(fixUpTaskId);
			
			Customer logged = customerService.findByUserAccountId(LoginService.getPrincipal().getId());

			if(fixUpTask.getCustomer().equals(logged)){
				result = this.createEditModelAndView(fixUpTask);
			}else{
				result = new ModelAndView("error/access");
			}

			return result;
		}
		
	//Delete edit----------------------------------------------------------------------------------------------------------------------------------------
		
		@RequestMapping(value="/edit", method = RequestMethod.POST, params = "delete")
		public ModelAndView delete(FixUpTask fixUpTask, BindingResult binding){
			
			ModelAndView res;
			
			try{
				this.fixUpTaskService.delete(fixUpTask);
				res= new ModelAndView("redirect:list.do");
			} catch (Throwable oops) {
				res = createEditModelAndView(fixUpTask,"task.commit.error");
			}
			return res;
		}
		
	//Delete list ----------------------------------------------------------------------------------------------------------------------------------------
	
		@RequestMapping(value="/delete", method = RequestMethod.GET)
		public ModelAndView delete(@RequestParam final int fixUpTaskId){
			
			ModelAndView res;
			FixUpTask fx;
			fx = fixUpTaskService.findOne(fixUpTaskId);
			
			try{
				this.fixUpTaskService.delete(fx);
				res= new ModelAndView("redirect:list.do");
			} catch (Throwable oops) {
				res = createEditModelAndView(fx,"task.commit.error");
			}
			return res;
		}
		
	//Save---------------------------------------------------------------	
		
		@RequestMapping(value="/edit", method=RequestMethod.POST, params="save")
		public ModelAndView save(@Valid FixUpTask fixUpTask, BindingResult binding){
			ModelAndView res;
			if(binding.hasErrors()){
				System.out.println("Fallos en: \n" + binding.getAllErrors());
				res = this.createEditModelAndView(fixUpTask);
			}else{
				try {
					this.fixUpTaskService.save(fixUpTask);
					res = new ModelAndView("redirect:list.do");
				} catch (Throwable oops) {
					System.out.println(oops.getCause());
					res = this.createEditModelAndView(fixUpTask, "task.commit.error");
				}
			}
			return res;
		}
		
	//Ancillary Methods---------
		
		protected ModelAndView createEditModelAndView(FixUpTask fixUpTask){
			ModelAndView res;
			res= this.createEditModelAndView(fixUpTask,null);
			return res;
		}
		
		protected ModelAndView createEditModelAndView(FixUpTask fixUpTask, String messageCode){
			ModelAndView res;
			Collection<Category> categories = categoryService.findAll();
			Collection<Warranty> warranties = warrantyService.findWarrantiesWNoTask();
			categories.remove(categoryService.getCategoryByName("CATEGORY"));
			Boolean sMoment = false, eMoment=false;
			res= new ModelAndView("fixUpTask/edit");
			if(fixUpTask.getId() !=0 && fixUpTask.getStartMoment().before(new Date())) sMoment=true;
			if(fixUpTask.getId() !=0 && fixUpTask.getEndMoment().before(new Date())) eMoment=true;

			res.addObject("fixUpTask", fixUpTask);
			res.addObject("warranties",warranties);
			res.addObject("now",new Date());
			res.addObject("sMoment",sMoment);
			res.addObject("eMoment",eMoment);
			res.addObject("categories",categories);
			res.addObject("message", messageCode);
			
			return res;
		}

}
