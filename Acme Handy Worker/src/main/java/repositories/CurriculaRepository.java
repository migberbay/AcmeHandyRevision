package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Complaint;
import domain.Curricula;

@Repository
public interface CurriculaRepository extends JpaRepository<Curricula, Integer>{

//	@Query("select a from Curricula a where a.id = ?1") 
//	Curricula findOne(Integer Id);
	
	@Query("select c from Curricula c where c.personalRecord.id = ?1")
	Curricula findByPersonalRecordId(Integer pr);
	
	@Query("select c from Curricula c join c.educationRecords er where (select er1 from EducationRecord er1 where er1.id=?1) in er")
	Curricula findByEducationRecordId(Integer er);
	
	@Query("select c from Curricula c join c.professionalRecords pr where (select pr1 from ProfessionalRecord pr1 where pr1.id=?1) in pr")
	Curricula findByProfessionalRecordId(Integer er);
	
	@Query("select c from Curricula c join c.endorserRecords er where (select er1 from EndorserRecord er1 where er1.id=?1) in er")
	Curricula findByEndorserRecordId(Integer er);
	
	@Query("select c from Curricula c join c.miscellaneousRecords er where (select er1 from MiscellaneousRecord er1 where er1.id=?1) in er")
	Curricula findByMiscellaneousRecordId(Integer er);
}
