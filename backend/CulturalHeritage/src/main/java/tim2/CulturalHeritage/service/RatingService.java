package tim2.CulturalHeritage.service;

import java.util.List;

import tim2.CulturalHeritage.model.Rating;


public interface RatingService {

	public List<Rating> findAll();

  public Rating findById(Long id);

  public Rating add(Rating rating);

  public Rating update(Rating rating);

  public void deleteById(Long id);
}
