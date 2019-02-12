package controllers.administrator;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CategoryService;
import controllers.AbstractController;
import domain.Category;
import domain.FixUpTask;

@Controller
@RequestMapping("/category/admin")
public class CategoryAdministratorController extends AbstractController {

	@Autowired
	private CategoryService categoryService;

	// Show --------------------------------------------------------------------

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int categoryId) {

		ModelAndView result;

		Category category = categoryService.findOne(categoryId);
		Collection<Category> cats = categoryService.getChildCategory(category);
		Collection<FixUpTask> fixUpTasks = categoryService.listTaskByCategory(categoryId);

		result = new ModelAndView("category/show");
		result.addObject("fixUpTasks", fixUpTasks);
		result.addObject("cats", cats);
		result.addObject("category", category);
		result.addObject("requestURI", "category/admin/show.do");

		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		Collection<Category> categories = new ArrayList<>();
		categories = this.categoryService.findAll();

		result = new ModelAndView("category/list");
		result.addObject("categories", categories);
		result.addObject("requestURI", "category/admin/list.do");
		return result;
	}

	// Create -----------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		
		Category category = categoryService.create();

		result = this.createEditModelAndView(category);
		return result;
	}

	// Edit -----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int categoryId) {
		ModelAndView result;

		Category category = categoryService.findOne(categoryId);

		result = this.createEditModelAndView(category);
		return result;
	}

	// Save -----------------------------------------------------------------

	@RequestMapping(value = "/edit", params = "save", method = RequestMethod.POST)
	public ModelAndView edit(@Valid final Category category,
			final BindingResult bindingResult) {
		ModelAndView result;

		if (bindingResult.hasErrors())
			result = this.createEditModelAndView(category);
		else
			try {
				this.categoryService.save(category);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(category,
						"category.commit.error");
			}
		return result;
	}

	// Delete -----------------------------------------------------------------

	@RequestMapping(value = "/edit", params = "delete", method = RequestMethod.POST)
	public ModelAndView delete(final Category category,
			final BindingResult bindingResult) {
		ModelAndView result;

		try {
			this.categoryService.delete(category);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(category,
					"category.commit.error");
		}
		return result;
	}

	private ModelAndView createEditModelAndView(final Category category) {
		ModelAndView result;

		result = this.createEditModelAndView(category, null);
		return result;
	}

	private ModelAndView createEditModelAndView(final Category category,
			final String message) {
		ModelAndView result;

		int id = category.getId();

		Collection<Category> categories = this.categoryService.findAll();
		Collection<FixUpTask> fixUpTasks = categoryService.listTaskByCategory(id);

		result = new ModelAndView("category/edit");
		result.addObject("category", category);
		result.addObject("message", message);
		result.addObject("categories", categories);
		result.addObject("fixUpTasks", fixUpTasks);

		return result;
	}

}
