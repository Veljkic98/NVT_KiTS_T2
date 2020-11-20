package tim2.CulturalHeritage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim2.CulturalHeritage.model.CHSubtype;
import tim2.CulturalHeritage.repository.CHSubtypeRepository;

@Service
public class CHSubtypeService {
    
    @Autowired
    private CHSubtypeRepository chSubtypeRepository;

    public CHSubtype findOne(Long id) {
		return chSubtypeRepository.findById(id).orElseGet(null);
	}
	
	public List<CHSubtype> findAll() {
		return chSubtypeRepository.findAll();
	}
	
	public CHSubtype save(CHSubtype Admin) {
		return chSubtypeRepository.save(Admin);
	}
	
	public void remove(Long id) {
		chSubtypeRepository.deleteById(id);
    }

}
