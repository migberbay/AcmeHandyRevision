package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.FixUpTask;

@Repository
public interface FixUpTaskRepository extends JpaRepository<FixUpTask, Integer>{
	
	//C-RF 11.1
	@Query("select a.fixUpTask from Application a where a.handyWorker.id=?1")
	Collection<FixUpTask> getFixUpTasksHandyWorker(int handyWorkerId);
	
	@Query("select f from FixUpTask f where f.customer.id=?1")
	Collection<FixUpTask> getFixUpTasksCustomer(int customerId);

	@Query("select fx from FixUpTask fx join fx.applications a where a.status='ACCEPTED'")
	Collection<FixUpTask> getTasksAccepted();

	@Query("select (select count(distinct c.fixUpTask)*1.0 from Complaint c) /count(f)*1.0 from FixUpTask f")
	Double getRatioTasksWComplaints();
	
	// C/1
	@Query("select count(f)*1.0/(select count(c) from Customer c) from FixUpTask f")
	Double getAvgTasksPerCustomer();
	
	@Query("select distinct count(f) from FixUpTask f join f.customer c where (select count(subf1) from FixUpTask subf1 where subf1.customer.id = c.id) <= all (select count(subf2) from FixUpTask subf2 group by subf2.customer) group by f.customer")
	Integer getMinTasksPerCustomer();
	
	@Query("select distinct count(f) from FixUpTask f join f.customer c where (select count(subf1) from FixUpTask subf1 where subf1.customer.id = c.id) >= all (select count(subf2) from FixUpTask subf2 group by subf2.customer) group by f.customer")
	Integer getMaxTasksPerCustomer();
	
	@Query("select sqrt(sum(c.fixUpTasks.size*c.fixUpTasks.size)) /(count(c)-(avg(c.fixUpTasks.size)*avg(c.fixUpTasks.size))) from Customer c")
	Double getStdevTasksPerCustomer();
	
	@Query("select avg (f.maxPrice) from FixUpTask f")
	Double getAvgMaxPriceTasks();
	
	@Query("select max (f.maxPrice) from FixUpTask f")
	Integer getMaximumMaxPriceTasks();
	
	@Query("select min (f.maxPrice) from FixUpTask f")
	Integer getMinimumMaxPriceTasks();
	
	@Query("select sqrt(sum(f.maxPrice*f.maxPrice)) /(count(f)-(avg(f.maxPrice)*avg(f.maxPrice))) from FixUpTask f")
	Double getStdevMaxPriceTasks();
}
