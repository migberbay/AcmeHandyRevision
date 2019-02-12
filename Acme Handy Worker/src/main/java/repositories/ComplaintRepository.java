package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Complaint;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Integer>{

	// no es necesario viene por defecto esta como referencia
	@Query("select a from Actor a where a.id = '?1'") 
	Complaint findOne(Integer Id);
	
	// B-RF 36.1
	@Query("select c from Complaint c where c not in (select distinct r.complaint from Report r)")
	Collection<Complaint> getComplaintsWithNoReports();
	
	// B-RF 36.2
	@Query("select r.complaint from Report r where r.referee.id = ?1")
	Collection<Complaint> getComplaintsReferee(int refereeId);
	
	// B-RF 37.3
	@Query("select distinct fx.complaints from Application a join a.fixUpTask fx where a.handyWorker.id = ?1")
	Collection<Complaint> getComplaintsHandyWorker(int handyWorkerId);

	@Query("select distinct com from Complaint com join com.fixUpTask fix join fix.customer cus where cus.id = ?1")
	Collection<Complaint> getComplaintsCustomer(int customerId);
	
	@Query("select c from Complaint c where c.fixUpTask.id = ?1")
	Collection<Complaint> getComplaintsFixUpTask(int fixUpTaskId);
	
	@Query("select count(c)*1.0/(select count(f) from FixUpTask f) from Complaint c")
	Double getAvgComplaintsPerTask();
	
	@Query("select distinct count(c) from Complaint c join c.fixUpTask f where (select count(subc1) from Complaint subc1 where subc1.fixUpTask.id = f.id) <= all (select count(subc2) from Complaint subc2 group by subc2.fixUpTask) group by c.fixUpTask")
	Integer getMinComplaintsPerTask();
	
	@Query("select distinct count(c) from Complaint c join c.fixUpTask f where (select count(subc1) from Complaint subc1 where subc1.fixUpTask.id = f.id) >= all (select count(subc2) from Complaint subc2 group by subc2.fixUpTask) group by c.fixUpTask")
	Integer getMaxComplaintsPerTask();
	
	@Query("select sqrt(sum(f.complaints.size*f.complaints.size)) /(count(f)-(avg(f.complaints.size)*avg(f.complaints.size))) from FixUpTask f")
	Double getStdevComplaintsPerTask();
}

