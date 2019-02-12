package controllers;

import domain.Complaint;
import domain.Note;
import domain.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.ComplaintService;
import services.NoteService;
import services.ReportService;

import javax.validation.Valid;
import java.util.Collection;

@Controller
@RequestMapping("report/")
public class ReportController extends AbstractController{

	// Services ---------------------------------------------------------------

	@Autowired
	private ComplaintService complaintService;

	@Autowired
	private ReportService reportService;

	@Autowired
	private NoteService noteService;

	@RequestMapping(value="/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int reportId){
		ModelAndView result;

		Report report = reportService.findOne(reportId);

		result = new ModelAndView("report/show");
		result.addObject("report", report);

		return result;
	}

	@RequestMapping(value="/note/create", method = RequestMethod.GET)
	public ModelAndView createNote(@RequestParam final int reportId){
		ModelAndView result;

		Note note = noteService.create();
		note.setReport(reportService.findOne(reportId));

		result = createEditModelAndViewNote(note);


		return result;
	}

	@RequestMapping(value="/note/edit", method = RequestMethod.GET)
	public ModelAndView editNote(@RequestParam final int noteId){
		ModelAndView result;

		Note note = noteService.findOne(noteId);

		result = createEditModelAndViewNote(note);

		return result;
	}

	@RequestMapping(value="/note/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView editNote(@Valid final Note note, BindingResult binding){
		ModelAndView result;

		if(binding.hasErrors()){
			System.out.println(binding.getAllErrors());
			result = createEditModelAndViewNote(note);
		}else{
			try{
				this.noteService.save(note);
				result = new ModelAndView("redirect:../show.do?reportId="+note.getReport().getId());
			}catch(Throwable oops){
				result = createEditModelAndViewNote(note,"report.commit.error");
				oops.printStackTrace();
			}
			return result;
		}

		result = createEditModelAndViewNote(note);

		return result;
	}

	protected ModelAndView createEditModelAndViewNote(Note note){
		ModelAndView res;
		res = createEditModelAndViewNote(note, null);

		return res;
	}

	protected ModelAndView createEditModelAndViewNote(Note note, String messageCode){
		ModelAndView res;
		res = new ModelAndView("note/edit");
		res.addObject("note", note);
		Note savedNote = null;
		if(note.getId() != 0){
			savedNote = noteService.findOne(note.getId());
		}
		res.addObject("savedNote", savedNote);
		res.addObject("message", messageCode);

		return res;
	}


}
