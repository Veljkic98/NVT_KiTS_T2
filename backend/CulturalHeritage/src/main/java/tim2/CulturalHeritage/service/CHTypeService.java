package tim2.CulturalHeritage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim2.CulturalHeritage.model.CHType;
import tim2.CulturalHeritage.repository.CHTypeRepository;

@Service
public class CHTypeService {
    
    @Autowired
    private CHTypeRepository chTypeRepository;

    public CHType findOne(Long id) {
		return chTypeRepository.findById(id).orElseGet(null);
	}
	
	public List<CHType> findAll() {
		return chTypeRepository.findAll();
	}
	
	public CHType save(CHType Admin) {
		return chTypeRepository.save(Admin);
	}
	
	public void remove(Long id) {
		chTypeRepository.deleteById(id);
    }

}
