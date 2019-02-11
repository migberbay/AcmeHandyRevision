package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Note;

@Repository
public interface NoteRepository extends JpaRepository<Note, Integer>{

	// no es necesario viene por defecto esta como referencia
	@Query("select a from Note a where a.id = ?1") 
	Note findOne(Integer Id);
	
	@Query("select count(n)*1.0/(select count(r) from Report r) from Note n")
	Double getAvgNotesPerReport();
	
	@Query("select distinct count(n) from Note n join n.report r where (select count(subn1) from Note subn1 where subn1.report.id = r.id) <= all (select count(subn2) from Note subn2 group by subn2.report) group by n.report")
	Integer getMinNotesPerReport();
	
	@Query("select distinct count(n) from Note n join n.report r where (select count(subn1) from Note subn1 where subn1.report.id = r.id) >= all (select count(subn2) from Note subn2 group by subn2.report) group by n.report")
	Integer getMaxNotesPerReport();
	
	@Query("select sqrt(sum(r.notes.size*r.notes.size)) /(count(r)-(avg(r.notes.size)*avg(r.notes.size))) from Report r")
	Double getStdevNotesPerReport();
}
