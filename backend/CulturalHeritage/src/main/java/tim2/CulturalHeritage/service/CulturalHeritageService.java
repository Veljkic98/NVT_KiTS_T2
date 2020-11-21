package tim2.CulturalHeritage.service;

import java.util.List;

import tim2.CulturalHeritage.model.CulturalHeritage;


public interface CulturalHeritageService {
    
  public List<CulturalHeritage> findAll();

  public CulturalHeritage findById(Long id);

  public CulturalHeritage add(CulturalHeritage culturalHeritage);

  public CulturalHeritage update(CulturalHeritage culturalHeritage);

  public void deleteById(Long id);
}
