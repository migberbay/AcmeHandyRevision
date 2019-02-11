package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;
import domain.Administrator;

@Repository
public interface AdministratorRepository extends
		JpaRepository<Administrator, Integer> {

	@Query("select a from Administrator a where a.id = ?1")
	Administrator findOne(Integer Id);
	
	@Query("select a from Actor a where a.isSuspicious = true")
	Collection<Actor> findSuspicious();

//	// The maximun of the number of complaints per fix-up task.
//	@Query("select max(f.complaints) from FixUpTask f")
//	Integer getMaxComplaintPerFixUpTask();
//
//	// The minimun of the number of complaints per fix-up task.
//	@Query("select min(f.complaints) from FixUpTask f")
//	Integer getMinComplaintPerFixUpTask();
//
//	// The average of the number of complaints per fix-up task.
//	// @Query("select count(c)*1.0/(select count(f) from FixUpTask f) from Comlaint c")
//	@Query("select avg(f.complaints) from FixUpTask f")
//	Double getAverageComplaintsPerFixUpTask();

}
