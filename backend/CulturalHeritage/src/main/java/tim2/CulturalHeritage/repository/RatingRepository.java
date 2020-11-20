package tim2.CulturalHeritage.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tim2.CulturalHeritage.model.Rating;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    
}
