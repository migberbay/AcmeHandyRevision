package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Box;

@Repository
public interface BoxRepository extends JpaRepository<Box, Integer>{

	// no es necesario viene por defecto esta como referencia
	@Query("select a from Box a where a.id = ?1") 
	Box findOne(Integer Id);
	
	//Bussines methods-----
	
	@Query("select b from Box b where b.actor.id = ?1")
	Collection<Box> findByActorId(Integer actorId);
	
	@Query("select b from Box b where b.actor.id = ?1 and b.name like ?2")
	Box findByActorIdAndName(int actorId, String boxName);
}
