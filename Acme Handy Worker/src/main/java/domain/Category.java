package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Category extends DomainEntity {

	// If parentCategory is null, it means it belongs to the virtual root
	// category called "CATEGORY"

	// Attributes -------------------------------------------------------------

	private String name;

	// Constructors -----------------------------------------------------------

	public Category() {
		super();
	}

	// Getters and Setters ---------------------------------------------------

	@NotBlank
	@Column(unique = true)
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	// Relationships ----------------------------------------------------------

	private Category parentCategory;

	@Valid
	@ManyToOne(optional = true)
	public Category getParentCategory() {
		return this.parentCategory;
	}

	public void setParentCategory(final Category parentCategory) {
		this.parentCategory = parentCategory;
	}
	
	/*private Collection<Category>	categories;


	@OneToMany(cascade = {
		CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH
	})
	public Collection<Category> getCategories() {
		return this.categories;
	}

	public void setCategories(final Collection<Category> categories) {
		this.categories = categories;
	}
	
	@Override
	public String toString() {
		return this.name;
	}*/

}
