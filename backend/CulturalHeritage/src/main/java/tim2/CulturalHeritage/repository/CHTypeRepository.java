package tim2.CulturalHeritage.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tim2.CulturalHeritage.model.CHType;

public interface CHTypeRepository extends JpaRepository<CHType, Long> {
    
}
