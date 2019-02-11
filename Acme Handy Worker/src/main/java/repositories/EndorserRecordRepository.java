package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.EndorserRecord;

@Repository
public interface EndorserRecordRepository extends JpaRepository<EndorserRecord, Integer>{

	// no es necesario viene por defecto esta como referencia
	@Query("select a from EndorserRecord a where a.id = ?1") 
	EndorserRecord findOne(Integer Id);
}
