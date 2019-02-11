package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.ProfessionalRecord;

@Repository
public interface ProfessionalRecordRepository extends JpaRepository<ProfessionalRecord, Integer>{

	// no es necesario viene por defecto esta como referencia
	@Query("select a from ProfessionalRecord a where a.id = ?1") 
	ProfessionalRecord findOne(Integer Id);
}
