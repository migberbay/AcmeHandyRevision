package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class CreditCardMake extends DomainEntity {

	// The credit card brand must match any of the credit card makes

	// Attributes -------------------------------------------------------------

	private String make;

	// Constructors -----------------------------------------------------------

	public CreditCardMake() {
		super();
	}

	// Getters and Setters ----------------------------------------------------

	@NotNull
	@Column(unique = true)
	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}
}
