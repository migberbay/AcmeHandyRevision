package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.MiscellaneousRecord;

@Repository
public interface MiscellaneousRecordRepository extends JpaRepository<MiscellaneousRecord, Integer>{

	// no es necesario viene por defecto esta como referencia
	@Query("select a from MiscellaneousRecord a where a.id = ?1") 
	MiscellaneousRecord findOne(Integer Id);
}
