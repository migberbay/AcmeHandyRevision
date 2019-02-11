package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class WelcomeMessage extends DomainEntity {

	// Attributes -------------------------------------------------------------

	private String lenguajeCode;
	private String text;

	// Constructors -----------------------------------------------------------

	public WelcomeMessage() {
		super();
	}

	// Getters and Setters ---------------------------------------------------

	@NotBlank
	@Column(unique = true)
	public String getLenguajeCode() {
		return lenguajeCode;
	}

	public void setLenguajeCode(String lenguajeCode) {
		this.lenguajeCode = lenguajeCode;
	}

	@NotBlank
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
