package tim2.CulturalHeritage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim2.CulturalHeritage.model.Location;
import tim2.CulturalHeritage.repository.LocationRepository;

@Service
public class LocationService {
    
    @Autowired
    private LocationRepository locationRepository;

    public Location findOne(Long id) {
		return locationRepository.findById(id).orElseGet(null);
	}
	
	public List<Location> findAll() {
		return locationRepository.findAll();
	}
	
	public Location save(Location Admin) {
		return locationRepository.save(Admin);
	}
	
	public void remove(Long id) {
		locationRepository.deleteById(id);
    }

}
