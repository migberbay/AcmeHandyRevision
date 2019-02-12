package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import security.LoginService;
import services.ActorService;
import services.AdministratorService;

import controllers.AbstractController;
import domain.Actor;

@Controller
@RequestMapping("/actor/admin")
public class ActorAdministratorController extends AbstractController {

	// Services ----------------------------------------------------------------f

	@Autowired
	private ActorService actorService;

	@Autowired
	private AdministratorService administratorService;

	// Constructors ------------------------------------------------------------

	public ActorAdministratorController() {
		super();
	}

	// Listing -----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;

		Collection<Actor> actors = administratorService.findSuspicious();

		result = new ModelAndView("actor/list");
		result.addObject("actors", actors);
		result.addObject("requestURI", "actor/admin/list.do");

		return result;
	}

	// Banear actor---------------------------------------------------------

	@RequestMapping(value = "/banActor", method = RequestMethod.GET)
	public ModelAndView banActor(@RequestParam int actorId) {
		ModelAndView res;

		if (!LoginService.hasRole("ADMIN")) {
			res = new ModelAndView("error/access");
		}else{
		Actor actor = actorService.findOne(actorId);
		if (!actor.getIsBanned()) {
			administratorService.ban(actorService.findOne(actorId));
		}
		res = new ModelAndView("redirect:list.do");
		}
		return res;
	}

	// Desbanear actor---------------------------------------------------------

	@RequestMapping(value = "/unBanActor", method = RequestMethod.GET)
	public ModelAndView unBanActor(@RequestParam int actorId) {
		ModelAndView res;

		if (!LoginService.hasRole("ADMIN")) {
			res = new ModelAndView("error/access");
		}else{
		Actor actor = actorService.findOne(actorId);
		if (actor.getIsBanned()) {
			administratorService.unBan(actorService.findOne(actorId));
		}
		res = new ModelAndView("redirect:list.do");
		}
		return res;
	}

	protected ModelAndView createEditModelAndView(Actor actor) {
		ModelAndView result;

		result = this.createEditModelAndView(actor, null);

		return result;
	}

	private ModelAndView createEditModelAndView(final Actor actor,
			final String message) {

		ModelAndView result;

		result = new ModelAndView("actor/list");

		result.addObject("actor", actor);
		result.addObject("message", message);

		return result;
	}
}