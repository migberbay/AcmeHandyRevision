package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Category;
import domain.FixUpTask;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>{
	
	@Query("select f from FixUpTask f where f.category.id = ?1")
	Collection<FixUpTask> listTaskByCategory(int id);
	
	@Query("select c from Category c where c.name = ?1")
	Category getCategoryByName(String cName);
	
	@Query("select c from Category c where c.parentCategory = ?1")
	Collection<Category> getChildCategory(Category c);
}
