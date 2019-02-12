package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Section;

@Repository
public interface SectionRepository extends JpaRepository<Section, Integer>{

	@Query("select a from Section a where a.tutorial.id = ?1") 
	Collection<Section> sectionsByTutorial(int Id);
}
