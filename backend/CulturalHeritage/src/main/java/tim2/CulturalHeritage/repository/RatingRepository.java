package tim2.CulturalHeritage.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tim2.CulturalHeritage.model.Rating;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

    public Page<Rating> findAll(Pageable pageable);

}
