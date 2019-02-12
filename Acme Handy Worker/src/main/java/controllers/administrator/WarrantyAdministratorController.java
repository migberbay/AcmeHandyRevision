package controllers.administrator;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.WarrantyService;
import controllers.AbstractController;
import domain.Warranty;

@Controller
@RequestMapping("warranty/admin/")
public class WarrantyAdministratorController extends AbstractController{

public WarrantyAdministratorController(){
	super();
}

	@Autowired
	WarrantyService warrantyService;	

		//Listing ---------------------------------------------------------------
	
		@RequestMapping(value="/list", method = RequestMethod.GET)
		public ModelAndView list(){
			
			ModelAndView res;
			Collection<Warranty> warranties;
			warranties = warrantyService.findAll();
			
			res = new ModelAndView("warranty/list");
			res.addObject("warranties", warranties);
			res.addObject("requestURI", "warranty/admin/list.do");
			return res; 
		}
		
		// Show ------------------------------------------------------------------

		@RequestMapping(value="/show", method = RequestMethod.GET)
		public ModelAndView show(@RequestParam final int warrantyId){
			ModelAndView res;
			Warranty warranty;

			warranty = warrantyService.findOne(warrantyId);
			
			res = new ModelAndView("warranty/show");
			res.addObject("warranty", warranty);
			res.addObject("requestURI", "warranty/admin/show.do");
			
			return res; 
		}

		// Create -----------------------------------------------------------------

		@RequestMapping(value = "/create", method = RequestMethod.GET)
		public ModelAndView create() {
			ModelAndView result;
			Warranty warranty;
			warranty = this.warrantyService.create();
			
			result = this.createEditModelAndView(warranty);

			return result;
		}

		// Edit -----------------------------------------------------------------

		@RequestMapping(value = "/edit", method = RequestMethod.GET)
		public ModelAndView edit(@RequestParam final int warrantyId) {
			ModelAndView result;
			Warranty warranty;

			warranty = this.warrantyService.findOne(warrantyId);
			result = this.createEditModelAndView(warranty);

			return result;
		}
		
	//Delete edit----------------------------------------------------------------------------------------------------------------------------------------
		
		@RequestMapping(value="/edit", method = RequestMethod.POST, params = "delete")
		public ModelAndView delete(Warranty warranty, BindingResult binding){
			
			ModelAndView res;
			
			try{
				this.warrantyService.delete(warranty);
				res= new ModelAndView("redirect:list.do");
			} catch (Throwable oops) {
				res = createEditModelAndView(warranty,"warranty.commit.error");
			}
			return res;
		}
		
	//Delete list ----------------------------------------------------------------------------------------------------------------------------------------
	
		@RequestMapping(value="/delete", method = RequestMethod.GET)
		public ModelAndView delete(@RequestParam final int warrantyId){
			
			ModelAndView res;
			Warranty warranty;
			warranty = warrantyService.findOne(warrantyId);
			
			try{
				this.warrantyService.delete(warranty);
				res= new ModelAndView("redirect:list.do");
			} catch (Throwable oops) {
				res = createEditModelAndView(warranty,"warranty.commit.error");
			}
			return res;
		}
		
	//Save---------------------------------------------------------------	
		
		@RequestMapping(value="/edit", method=RequestMethod.POST, params="save")
		public ModelAndView save(@Valid Warranty warranty, BindingResult binding){
			ModelAndView res;
			if(binding.hasErrors()){
				res = this.createEditModelAndView(warranty);
			}else{
				try {
					this.warrantyService.save(warranty);
					res = new ModelAndView("redirect:list.do");
				} catch (Throwable oops) {
					System.out.println(oops.getCause());
					res = this.createEditModelAndView(warranty, "warranty.commit.error");
				}
			}
			return res;
		}
		
	//Ancillary Methods---------
		
		protected ModelAndView createEditModelAndView(Warranty warranty){
			ModelAndView res;
			res= this.createEditModelAndView(warranty, null);
			return res;
		}
		
		protected ModelAndView createEditModelAndView(Warranty warranty, String messageCode){
			ModelAndView result;

			result = new ModelAndView("warranty/edit");

			result.addObject("warranty", warranty);
			result.addObject("message", messageCode);

			return result;
			
		}

}
