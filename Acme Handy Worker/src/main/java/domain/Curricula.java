package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Curricula extends DomainEntity {

	// Attributes -------------------------------------------------------------

	private String ticker;

	// Constructors -----------------------------------------------------------

	public Curricula() {
		super();
	}

	// Getters & Setters -------------------------------------------------------

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp ="^([0-2]{1}[0-9]{1})([0]{1}[1-9]{1}|[1]{1}[0-2]{1})([0]{1}[1-9]{1}|[1,2]{1}[0-9]{1}|[3]{1}[0-1]{1})[-][A-Z0-9]{6}$")
	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}


	// Relationships ----------------------------------------------------------

	private PersonalRecord personalRecord;
	private Collection<EducationRecord> educationRecords;
	private Collection<MiscellaneousRecord> miscellaneousRecords;
	private Collection<ProfessionalRecord> professionalRecords;
	private Collection<EndorserRecord> endorserRecords;
	private HandyWorker handyWorker;

	
	@Valid
	@OneToOne(optional = false)
	public PersonalRecord getPersonalRecord() {
		return this.personalRecord;
	}

	public void setPersonalRecord(final PersonalRecord personalRecord) {
		this.personalRecord = personalRecord;
	}
	
	@Valid
	@ElementCollection
	@OneToMany(cascade = CascadeType.ALL)
	public Collection<EducationRecord> getEducationRecords() {
		return this.educationRecords;
	}

	public void setEducationRecords(
			final Collection<EducationRecord> educationRecords) {
		this.educationRecords = educationRecords;
	}

	@Valid
	@ElementCollection
	@OneToMany(cascade = CascadeType.ALL)
	public Collection<MiscellaneousRecord> getMiscellaneousRecords() {
		return this.miscellaneousRecords;
	}

	public void setMiscellaneousRecords(
			final Collection<MiscellaneousRecord> miscellaneousRecords) {
		this.miscellaneousRecords = miscellaneousRecords;
	}

	@Valid
	@ElementCollection
	@OneToMany(cascade = CascadeType.ALL)
	public Collection<ProfessionalRecord> getProfessionalRecords() {
		return this.professionalRecords;
	}

	public void setProfessionalRecords(
			final Collection<ProfessionalRecord> professionalRecords) {
		this.professionalRecords = professionalRecords;
	}

	@Valid
	@ElementCollection
	@OneToMany(cascade = CascadeType.ALL)
	public Collection<EndorserRecord> getEndorserRecords() {
		return this.endorserRecords;
	}

	public void setEndorserRecords(
			final Collection<EndorserRecord> endorserRecords) {
		this.endorserRecords = endorserRecords;
	}

	@Valid
	@OneToOne(optional = false)
	public HandyWorker getHandyWorker() {
		return this.handyWorker;
	}

	public void setHandyWorker(final HandyWorker handyWorker) {
		this.handyWorker = handyWorker;
	}

}
