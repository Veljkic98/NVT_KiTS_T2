package tim2.CulturalHeritage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim2.CulturalHeritage.model.Rating;
import tim2.CulturalHeritage.repository.RatingRepository;

@Service
public class RatingService {
    
    @Autowired
    private RatingRepository ratingRepository;

    public Rating findOne(Long id) {
		return ratingRepository.findById(id).orElseGet(null);
	}
	
	public List<Rating> findAll() {
		return ratingRepository.findAll();
	}
	
	public Rating save(Rating Admin) {
		return ratingRepository.save(Admin);
	}
	
	public void remove(Long id) {
		ratingRepository.deleteById(id);
    }

}
