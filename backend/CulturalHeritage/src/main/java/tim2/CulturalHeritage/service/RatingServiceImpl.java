package tim2.CulturalHeritage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import tim2.CulturalHeritage.model.Rating;
import tim2.CulturalHeritage.repository.RatingRepository;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Override
    public Page<Rating> findAll(Pageable pageable) {
        return ratingRepository.findAll(pageable);
    }

    @Override
    public Rating findById(Long id) {
        return ratingRepository.findById(id).orElse(null);
    }

    @Override
    public Rating add(Rating rating) {
        return ratingRepository.save(rating);
    }

    @Override
    public Rating update(Rating rating) {
        return ratingRepository.save(rating);
    }

    @Override
    public void deleteById(Long id) {
        ratingRepository.deleteById(id);
    }

}
