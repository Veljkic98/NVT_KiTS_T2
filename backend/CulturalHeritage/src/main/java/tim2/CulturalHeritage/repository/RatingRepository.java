package tim2.CulturalHeritage.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tim2.CulturalHeritage.model.News;
import tim2.CulturalHeritage.model.Rating;

import javax.transaction.Transactional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

    public Page<Rating> findAll(Pageable pageable);

    @Transactional
    @Query("select r from Rating r where r.culturalHeritage.id = :chID and r.authenticatedUser.id = :userID")
    public Rating getUserRating(Long chID, Long userID);

}
