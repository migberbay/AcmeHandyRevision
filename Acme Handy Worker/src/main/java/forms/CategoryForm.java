
package forms;

import java.util.Collection;

import javax.persistence.ElementCollection;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;

import domain.Category;

public class CategoryForm {

	private int						id;
	private int						version;
	private String					name;
	private Collection<Category>	hijos;
	private Category				categoryFather;


	@ElementCollection
	public Collection<Category> getHijos() {
		return this.hijos;
	}

	public void setHijos(final Collection<Category> hijos) {
		this.hijos = hijos;
	}

	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public int getVersion() {
		return this.version;
	}

	public void setVersion(final int version) {
		this.version = version;
	}

	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@Valid
	public Category getCategoryFather() {
		return this.categoryFather;
	}

	public void setCategoryFather(final Category categoryFather) {
		this.categoryFather = categoryFather;
	}

}
