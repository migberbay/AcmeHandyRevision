package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CategoryRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Category;
import domain.FixUpTask;


@Service
@Transactional
public class CategoryService {

	//Managed Repository -----
	@Autowired
	private CategoryRepository categoryRepository;
	
	//Supporting Services -----
	
	@Autowired
	private FixUpTaskService taskService;
	
	//Simple CRUD methods -----
	
	public Category create(){
		Category res = new Category();
		
		return res;
	}
	
	public Collection<Category> findAll(){
		return categoryRepository.findAll();
	}
	
	public Category findOne(int Id){
		return categoryRepository.findOne(Id);
	}
	
	public Category save(Category a){
		UserAccount userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("ADMIN");
		Assert.isTrue(userAccount.getAuthorities().contains(au));
		
		return categoryRepository.save(a);
	}
	
	public void delete(Category a){
		UserAccount userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("ADMIN");
		Assert.isTrue(userAccount.getAuthorities().contains(au));
		//final Category primeraCategoria = this.getCategoryByName("category");
		System.out.println(a.getParentCategory());
		for(FixUpTask f:categoryRepository.listTaskByCategory(a.getId())){ //Hacerlo mejor con una query
			if(a.getParentCategory()!=null){
				f.setCategory(a.getParentCategory());
			}else{
				f.setCategory(categoryRepository.getCategoryByName("CATEGORY"));
			}
			taskService.saveAdmin(f);
		}

		categoryRepository.delete(a);
	}
	
	//Other business methods -----
	
	public Category getCategoryByName(String cName){
		Category res;
		res = categoryRepository.getCategoryByName(cName);
		return res;
	}
	
	public Collection<FixUpTask> listTaskByCategory(int id){
		return categoryRepository.listTaskByCategory(id);
	}
	
	public Collection<Category> getChildCategory(Category c){
		return categoryRepository.getChildCategory(c);
	}
	
}