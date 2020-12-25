package tim2.CulturalHeritage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tim2.CulturalHeritage.model.CHSubtype;

@Repository
public interface CHSubtypeRepository extends JpaRepository<CHSubtype, Long> {

    CHSubtype findByName(String name);
}
