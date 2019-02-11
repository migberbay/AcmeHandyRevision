package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.HandyWorker;

@Repository
public interface HandyWorkerRepository extends JpaRepository<HandyWorker, Integer>{

	// no es necesario viene por defecto esta como referencia
	@Query("select a from HandyWorker a where a.id = ?1") 
	HandyWorker findOne(Integer Id);
	
	@Query("select hw from HandyWorker hw where hw.userAccount.id = ?1") 
	HandyWorker findByUserAccountId(Integer Id);
	
	@Query("select hw from HandyWorker hw join hw.userAccount ac where ac.id = ?1")
	HandyWorker findByPrincipal(int id);

	@Query("select f.handyWorker from Finder f where f.id=?1")
	HandyWorker findByFinderId(int finderId);
	
	@Query("select hw from HandyWorker hw where (select count(a1)*1.0/ (select count(subhw1) from HandyWorker subhw1) from Application a1 where a1.status='ACCEPTED')*1.1 <= (select count(a3)*1.0 from Application a3 where a3.status='ACCEPTED' and hw.id = a3.handyWorker.id)")
	Collection<HandyWorker> getHandyWorkersWMoreApplicationsThanAvg();
	
	@Query("select hw from Complaint com join com.fixUpTask fix join fix.applications app join app.handyWorker hw where app.status = 'ACCEPTED' group by hw order by count(com) desc")
	Collection<HandyWorker> topThreeInComplaints();
}
