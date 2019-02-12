package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Warranty;

@Repository
public interface WarrantyRepository extends JpaRepository<Warranty, Integer>{
	
	@Query("select distinct w from Warranty w where w.isDraft=true and w not in (select f.warranty from FixUpTask f)")
	Collection<Warranty> findWarrantiesWNoTask();

}
