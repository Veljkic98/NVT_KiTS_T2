package tim2.CulturalHeritage.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import tim2.CulturalHeritage.model.Rating;

public interface RatingService {

    public Page<Rating> findAll(Pageable pageable);

    public Rating findById(Long id);

    public Rating add(Rating rating);

    public Rating update(Rating rating);

    public void deleteById(Long id);
}
