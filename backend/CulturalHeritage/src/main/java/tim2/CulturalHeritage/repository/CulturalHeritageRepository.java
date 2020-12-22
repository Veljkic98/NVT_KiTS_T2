package tim2.CulturalHeritage.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tim2.CulturalHeritage.model.CulturalHeritage;

@Repository
public interface CulturalHeritageRepository extends JpaRepository<CulturalHeritage, Long> {

    Page<CulturalHeritage> findByChsubtypeName();

    Page<CulturalHeritage> findByLocation_City();

    Page<CulturalHeritage> findByLocation_Country();



}