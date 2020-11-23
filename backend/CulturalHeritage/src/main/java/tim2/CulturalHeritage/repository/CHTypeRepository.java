package tim2.CulturalHeritage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tim2.CulturalHeritage.model.CHType;

@Repository
public interface CHTypeRepository extends JpaRepository<CHType, Long> {

}
