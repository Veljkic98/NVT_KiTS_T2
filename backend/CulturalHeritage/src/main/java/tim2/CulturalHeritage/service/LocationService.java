package tim2.CulturalHeritage.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tim2.CulturalHeritage.model.Location;

public interface LocationService {

    public Page<Location> findAll(Pageable pageable);

    public Location findById(Long id);

    public Location add(Location location);

    public Location update(Location location);

    public void deleteById(Long id);

}
