package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Word;

@Repository
public interface WordRepository extends JpaRepository<Word, Integer>{

	// no es necesario viene por defecto esta como referencia
	@Query("select a from Word a where a.id = ?1") 
	Word findOne(Integer Id);
	
	@Query("select w from Word w where w.type = 'SPAM'")
	Collection<Word> findSpamWords();
	
	@Query("select w from Word w where w.type = 'POSITIVE'")
	Collection<Word> findPositiveWords();
	
	@Query("select w from Word w where w.type = 'NEGATIVE'")
	Collection<Word> findNegativeWords();
}
