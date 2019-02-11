package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.CreditCardMake;

@Repository
public interface CreditCardMakeRepository extends JpaRepository<CreditCardMake, Integer>{

	// no es necesario viene por defecto esta como referencia
	@Query("select a from CreditCardMake a where a.id = ?1") 
	CreditCardMake findOne(Integer Id);
}
