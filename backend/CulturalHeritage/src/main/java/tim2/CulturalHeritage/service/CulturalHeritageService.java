package tim2.CulturalHeritage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim2.CulturalHeritage.model.CulturalHeritage;
import tim2.CulturalHeritage.repository.CulturalHeritageRepository;

@Service
public class CulturalHeritageService {
    
    @Autowired
    private CulturalHeritageRepository culturalHeritageRepository;

    public CulturalHeritage findOne(Long id) {
		return culturalHeritageRepository.findById(id).orElseGet(null);
	}
	
	public List<CulturalHeritage> findAll() {
		return culturalHeritageRepository.findAll();
	}
	
	public CulturalHeritage save(CulturalHeritage Admin) {
		return culturalHeritageRepository.save(Admin);
	}
	
	public void remove(Long id) {
		culturalHeritageRepository.deleteById(id);
    }

}
