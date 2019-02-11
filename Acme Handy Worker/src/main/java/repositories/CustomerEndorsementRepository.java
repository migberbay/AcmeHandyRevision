package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.CustomerEndorsement;
import domain.HandyWorkerEndorsement;

@Repository
public interface CustomerEndorsementRepository extends JpaRepository<CustomerEndorsement, Integer>{

	// no es necesario viene por defecto esta como referencia
	@Query("select a from CustomerEndorsement a where a.id = ?1") 
	CustomerEndorsement findOne(Integer Id);
	
	@Query("select a from CustomerEndorsement a where a.handyWorker.id = ?1")
	Collection<CustomerEndorsement> customerEndorsementsByHandyWorker(int handyWorkerId);
}
