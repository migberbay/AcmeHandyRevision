package repositories;

import domain.FixUpTask;
import domain.HandyWorker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import domain.Finder;

import java.util.Collection;
import java.util.Date;

@Repository
public interface FinderRepository extends JpaRepository<Finder, Integer>{

	@Query("select fix from FixUpTask fix where " +
			"((:keyword is null or :keyword like '' ) or " +
				"(fix.ticker like %:keyword% " +
				"or fix.description like %:keyword% " +
				"or fix.address like %:keyword%))" +
			"and (:minPrice is null or fix.maxPrice >= :minPrice) " +
			"and (:maxPrice is null or fix.maxPrice <= :maxPrice) " +
			"and (:startDate is null or :startDate <= fix.startMoment) " +
			"and (:endDate is null or fix.endMoment <= :endDate) " +
			"and (:categoryId is null or fix.category.id = :categoryId) " +
			"and (:warrantyId is null or fix.warranty.id = :warrantyId)")
	Collection<FixUpTask> filterFixUpTasks(@Param("keyword") String keyword,
										   @Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice,
										   @Param("startDate") Date startDate, @Param("endDate") Date endDate,
										   @Param("categoryId") Integer categoryId,
										   @Param("warrantyId") Integer warrantyId);

	@Query("select f from Finder f where f.handyWorker.id = :handyWorkerId")
	Finder findByHandyWorker(@Param("handyWorkerId") Integer handyWorkerId);
}
