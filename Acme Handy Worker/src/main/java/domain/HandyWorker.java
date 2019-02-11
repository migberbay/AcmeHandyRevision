package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class HandyWorker extends Actor {

	// Attributes -------------------------------------------------------------

	private String make;

	// Constructors -----------------------------------------------------------

	public HandyWorker() {
		super();
	}

	// Getters and Setters ---------------------------------------------------

	@NotBlank
	public String getMake() {
		return this.make;
	}

	public void setMake(final String make) {
		this.make = make;
	}

}
