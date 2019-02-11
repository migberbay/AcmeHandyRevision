package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.PersonalRecord;

@Repository
public interface PersonalRecordRepository extends JpaRepository<PersonalRecord, Integer>{

	// no es necesario viene por defecto esta como referencia
	//	@Query("select a from PersonalRecord a where a.id = ?1") 
	//	PersonalRecord findOne(Integer Id);
}
