package tim2.CulturalHeritage.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;
import tim2.CulturalHeritage.model.CulturalHeritage;

@Repository
public interface CulturalHeritageRepository extends JpaRepository<CulturalHeritage, Long> {
    @Transactional
    Page<CulturalHeritage> findByNameContainsAllIgnoreCase(String name, Pageable page);

    @Transactional
    Page<CulturalHeritage> findByChsubtypeNameContainsAllIgnoreCase(String chSubtypeName, Pageable page);

    @Transactional
    Page<CulturalHeritage> findByLocationCityAllIgnoreCase(String city, Pageable page);

    @Transactional
    Page<CulturalHeritage> findByLocationCountryAllIgnoreCase(String country, Pageable page);


}