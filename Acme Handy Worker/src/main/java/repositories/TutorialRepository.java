package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Tutorial;

@Repository
public interface TutorialRepository extends JpaRepository<Tutorial, Integer>{

	@Query("select a from Tutorial a where a.handyWorker.id = ?1") 
	Collection<Tutorial> tutorialsByHandyWorker(int Id);
}
