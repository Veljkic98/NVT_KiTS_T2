package tim2.CulturalHeritage.service;

import java.util.List;

import tim2.CulturalHeritage.model.Location;

public interface LocationService {

    public List<Location> findAll();

    public Location findById(Long id);

    public Location add(Location location);

    public Location update(Location location);

    public void deleteById(Long id);

}
