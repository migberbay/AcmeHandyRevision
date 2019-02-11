package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Box extends DomainEntity {

	// Default system boxes can't be deleted or modified

	// Attributes -----------------------------------------------------------

	private String name;
	private Boolean systemBox;

	// Constructors -----------------------------------------------------------

	public Box() {
		super();
	}

	// Getters and Setters ---------------------------------------------------

	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public Boolean getSystemBox() {
		return systemBox;
	}

	public void setSystemBox(Boolean systemBox) {
		this.systemBox = systemBox;
	}

	// Relationships ----------------------------------------------------------

	private Actor actor;
	private Collection<Integer> messages;

	@Valid
	@ElementCollection
	public Collection<Integer> getMessages() {
		return messages;
	}

	public void setMessages(Collection<Integer> messages) {
		this.messages = messages;
	}

	@Valid
	@ManyToOne(optional = false)
	public Actor getActor() {
		return actor;
	}

	public void setActor(Actor actor) {
		this.actor = actor;
	}

}
