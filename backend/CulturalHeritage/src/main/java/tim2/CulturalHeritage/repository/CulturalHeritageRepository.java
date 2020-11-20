package tim2.CulturalHeritage.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tim2.CulturalHeritage.model.CulturalHeritage;

public interface CulturalHeritageRepository extends JpaRepository<CulturalHeritage, Long> {
    
}
