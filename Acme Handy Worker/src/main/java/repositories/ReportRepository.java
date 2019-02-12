package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer>{

	// no es necesario viene por defecto esta como referencia
	@Query("select a from Report a where a.id = ?1") 
	Report findOne(Integer Id);
	
	@Query("select r from Report r where r.complaint.id = ?1")
	Collection<Report> getReportsByComplaint(int complaintId);
	
	@Query("select r from Report r where r.referee.id = ?1")
	Collection<Report> getReportsByReferee(int refereeId);
	
	@Query("select r from Report r where r.complaint.id = ?1 and r.isDraft = false")
	Collection<Report> getFinalReportsByComplaint(int complaintId);
}
