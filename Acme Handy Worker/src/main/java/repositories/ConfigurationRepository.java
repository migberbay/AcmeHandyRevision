package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Configuration;

@Repository
public interface ConfigurationRepository extends JpaRepository<Configuration, Integer>{

	// no es necesario viene por defecto esta como referencia
	@Query("select a from Configuration a where a.id = ?1") 
	Configuration findOne(Integer Id);
}
