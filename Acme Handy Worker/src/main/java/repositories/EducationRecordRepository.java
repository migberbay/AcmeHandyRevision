package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.EducationRecord;

@Repository
public interface EducationRecordRepository extends JpaRepository<EducationRecord, Integer>{

	// no es necesario viene por defecto esta como referencia
	@Query("select a from EducationRecord a where a.id = ?1") 
	EducationRecord findOne(Integer Id);
}
