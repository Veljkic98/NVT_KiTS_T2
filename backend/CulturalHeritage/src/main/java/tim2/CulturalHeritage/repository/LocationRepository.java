package tim2.CulturalHeritage.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tim2.CulturalHeritage.model.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {

}
