package tim2.CulturalHeritage.service;

import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import tim2.CulturalHeritage.model.Location;
import tim2.CulturalHeritage.repository.LocationRepository;

@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    private LocationRepository locationRepository;

    @Override
    public Page<Location> findAll(Pageable pageable) {
        return locationRepository.findAll(pageable);
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
        if(null == locationRepository.findById(location.getId()).orElse(null)){
            throw new EntityNotFoundException();
        }
        return locationRepository.save(location);
    }

    @Override
    public void deleteById(Long id) {
        locationRepository.deleteById(id);

    }

}
