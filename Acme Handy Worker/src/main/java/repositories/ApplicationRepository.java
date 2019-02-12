package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Application;

@Repository
public interface ApplicationRepository extends
		JpaRepository<Application, Integer> {

	@Query("select a from Application a where a.id = ?1")
	Application findOne(Integer Id);

	@Query("select a from Application a where a.handyWorker.id = ?1")
	Collection<Application> applicationByHandyWorker(Integer Id);
	
	@Query("select a from Application a where a.fixUpTask.customer.id = ?1")
	Collection<Application> applicationByCustomer(Integer Id);
	
	@Query("select a from FixUpTask f join f.applications a where a.status='ACCEPTED' and a.handyWorker.id = ?1 and f.id = ?2")
	Collection<Application> findApplicationsAccepted(int handyWorkerId, int taskId);

	// C/2 The average, the minimum, the maximum and the standard deviation of
	// the number of applications per fix-up tasks.

	@Query("select count(a)*1.0/(select count(f) from FixUpTask f) from Application a")
	Double getAverageApplicationsPerFixUpTask();

	@Query("select distinct count(a) from Application a join a.fixUpTask f where (select count(suba1) from Application suba1 where suba1.fixUpTask.id = f.id) <= all (select count(suba2) from Application suba2 group by suba2.fixUpTask) group by a.fixUpTask")
	Integer getMinimumApplicationsPerFixUpTask();

	@Query("select distinct count(a) from Application a join a.fixUpTask f where (select count(suba1) from Application suba1 where suba1.fixUpTask.id = f.id) >= all (select count(suba2) from Application suba2 group by suba2.fixUpTask) group by a.fixUpTask")
	Integer getMaximumApplicationsPerFixUpTask();

	@Query("select sqrt(sum(f.applications.size*f.applications.size))/(count(f)-(avg(f.applications.size)*avg(f.applications.size))) from FixUpTask f")
	Double getStdevApplicationsPerFixUpTask();

	// C/4 The average, the minimum, the maximum and the standard deviation of
	// the price offered in the applications.
	@Query("select avg(a.price) from Application a")
	Double getAveragePriceApplication();

	@Query("select max (a.price) from Application a")
	Integer getMaximumPriceApplications();

	@Query("select min (a.price) from Application a")
	Integer getMinimumPriceApplications();

	@Query("select sqrt(sum(a.price*a.price)) /(count(a)-(avg(a.price)*avg(a.price))) from Application a")
	Double getStdevPriceApplications();

	// C/5 The ratio of pending applications.
	@Query("select (select count(app2) from Application app2 where app2.status='PENDING')/count(app1)*1.0 from Application app1")
	Double getRatioPendingApplications();

	// C/6 The ratio of accepted applications.
	@Query("select (select count(app2) from Application app2 where app2.status='ACCEPTED')/count(app1)*1.0 from Application app1")
	Double getRatioAcceptedApplications();

	// C/7 The ratio of rejected applications.
	@Query("select (select count(app2) from Application app2 where app2.status='REJECTED')/count(app1)*1.0 from Application app1")
	Double getRatioRejectedApplications();

	// C/8 The ratio of pending applications that cannot change its status
	// because their time period´s elapsed.
	@Query("select ((count(a)*1.0)/(select count(a1)*1.0 from Application a1)) from Application a where a.fixUpTask.endMoment < CURRENT_DATE and a.status='PENDING'")
	Double getRatioPendingApplicationsTime();
}
