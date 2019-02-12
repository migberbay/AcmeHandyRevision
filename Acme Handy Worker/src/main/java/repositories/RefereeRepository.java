package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Referee;

@Repository
public interface RefereeRepository extends JpaRepository<Referee, Integer>{

	// no es necesario viene por defecto esta como referencia
	@Query("select a from Referee a where a.id = ?1") 
	Referee findOne(Integer Id);
	
	@Query("select r from Referee r where r.userAccount.id = ?1") 
	Referee findByUserAccountId(Integer Id);
	
}
