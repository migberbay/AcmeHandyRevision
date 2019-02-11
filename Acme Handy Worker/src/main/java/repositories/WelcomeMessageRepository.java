package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.WelcomeMessage;

@Repository
public interface WelcomeMessageRepository extends JpaRepository<WelcomeMessage, Integer>{

	// no es necesario viene por defecto esta como referencia
	@Query("select a from WelcomeMessage a where a.id = ?1") 
	WelcomeMessage findOne(Integer Id);
}
