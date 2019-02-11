package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.HandyWorkerEndorsement;

@Repository
public interface HandyWorkerEndorsementRepository extends JpaRepository<HandyWorkerEndorsement, Integer>{

	// no es necesario viene por defecto esta como referencia
	@Query("select a from HandyWorkerEndorsement a where a.id = ?1") 
	HandyWorkerEndorsement findOne(Integer Id);
}
