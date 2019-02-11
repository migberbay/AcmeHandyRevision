package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Note extends DomainEntity {

	// Only the referee, customer and handyworker involved in the complaint can
	// make notes on the corresponding report

	// Depending on the kind of actor who created the note, the corresponding
	// comment would be mandatory and the rest would be optional

	// Attributes -------------------------------------------------------------

	private Date moment;
	private String refereeComment;
	private String customerComment;
	private String handyWorkerComment;

	// Constructors -----------------------------------------------------------

	public Note() {
		super();
	}

	// Getters and Setters ---------------------------------------------------

	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	public String getRefereeComment() {
		return refereeComment;
	}

	public void setRefereeComment(String refereeComment) {
		this.refereeComment = refereeComment;
	}

	public String getCustomerComment() {
		return customerComment;
	}

	public void setCustomerComment(String customerComment) {
		this.customerComment = customerComment;
	}

	public String getHandyWorkerComment() {
		return handyWorkerComment;
	}

	public void setHandyWorkerComment(String handyWorkerComment) {
		this.handyWorkerComment = handyWorkerComment;
	}

	// Relationships ----------------------------------------------------------

	private Report report;


	@Valid
	@ManyToOne(optional = false)
	public Report getReport() {
		return report;
	}

	public void setReport(Report report) {
		this.report = report;
	}

}
