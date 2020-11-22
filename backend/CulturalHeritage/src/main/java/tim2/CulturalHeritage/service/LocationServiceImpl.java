package tim2.CulturalHeritage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim2.CulturalHeritage.model.Location;
import tim2.CulturalHeritage.repository.LocationRepository;

@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    private LocationRepository locationRepository;

    @Override
    public List<Location> findAll() {
        return locationRepository.findAll();
    }

    @Override
    public Location findById(Long id) {
        return locationRepository.findById(id).orElse(null);
    }

    @Override
    public Location add(Location location) {
        return locationRepository.save(location);
    }

    @Override
    public Location update(Location location) {
        return locationRepository.save(location);
    }

    @Override
    public void deleteById(Long id) {
        locationRepository.deleteById(id);

    }

}
