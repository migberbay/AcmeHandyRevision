package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Word extends DomainEntity {

	// Attributes -------------------------------------------------------------

	private String content;
	private String type;

	// Constructors -----------------------------------------------------------

	public Word() {
		super();
	}

	// Getters and Setters ---------------------------------------------------

	@NotBlank
	@Column(unique = true)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@NotBlank
	@Pattern(regexp="^SPAM|POSITIVE|NEGATIVE$")
	public String getType() {
		return type;
	}

	

	public void setType(String type) {
		this.type = type;
	}
}
