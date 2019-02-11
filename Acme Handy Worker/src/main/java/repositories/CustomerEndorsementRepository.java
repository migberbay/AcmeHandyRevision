package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.CustomerEndorsement;

@Repository
public interface CustomerEndorsementRepository extends JpaRepository<CustomerEndorsement, Integer>{

	// no es necesario viene por defecto esta como referencia
	@Query("select a from CustomerEndorsement a where a.id = ?1") 
	CustomerEndorsement findOne(Integer Id);
}
