package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.SocialProfile;

@Repository
public interface SocialProfileRepository extends JpaRepository<SocialProfile, Integer>{

	// no es necesario viene por defecto esta como referencia
	@Query("select a from SocialProfile a where a.id = ?1") 
	SocialProfile findOne(Integer Id);
}
