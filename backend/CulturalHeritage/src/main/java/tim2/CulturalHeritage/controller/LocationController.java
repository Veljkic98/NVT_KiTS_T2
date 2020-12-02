package tim2.CulturalHeritage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tim2.CulturalHeritage.dto.requestDTO.LocationRequestDTO;
import tim2.CulturalHeritage.dto.responseDTO.LocationResponseDTO;
import tim2.CulturalHeritage.helper.LocationMappers.LocationRequestMapper;
import tim2.CulturalHeritage.helper.LocationMappers.LocationResponseMapper;
import tim2.CulturalHeritage.model.Location;
import tim2.CulturalHeritage.service.LocationService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

    @Autowired
    private LocationService locationService;

    private LocationRequestMapper locationRequestMapper = new LocationRequestMapper();

    private LocationResponseMapper locationResponseMapper = new LocationResponseMapper();

    @RequestMapping(value="/by-page", method= RequestMethod.GET)
    public ResponseEntity<Page<LocationResponseDTO>>  findAll(Pageable pageable) {

        Page<Location> resultPage = locationService.findAll(pageable);
        List<LocationResponseDTO> locationsDTO = locationResponseMapper.toDtoList(resultPage.toList());
        Page<LocationResponseDTO> pageLocationsDTO = new PageImpl<>(locationsDTO, resultPage.getPageable(), resultPage.getTotalElements());

        return new ResponseEntity<>(pageLocationsDTO, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {

        try {
            Location location = locationService.findById(id);
            return new ResponseEntity<>(locationResponseMapper.toDto(location), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> add(@Valid @RequestBody LocationRequestDTO locationRequest) {

        try {
            Location location = locationService.add(locationRequestMapper.toEntity(locationRequest));

            return new ResponseEntity<>(locationResponseMapper.toDto(location), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody LocationRequestDTO locationRequest) {

        try {
            Location location = locationRequestMapper.toEntity(locationRequest);
            location.setId(id);
            locationService.update(location);

            return new ResponseEntity<>(locationResponseMapper.toDto(location), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        System.out.print(id);
        try {
            locationService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
