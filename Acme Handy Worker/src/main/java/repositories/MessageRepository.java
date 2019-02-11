package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer>{

	// no es necesario viene por defecto esta como referencia
	@Query("select a from Message a where a.id = ?1") 
	Message findOne(Integer Id);
}
