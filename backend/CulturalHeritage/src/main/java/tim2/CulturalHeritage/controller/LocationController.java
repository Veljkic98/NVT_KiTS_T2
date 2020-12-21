package tim2.CulturalHeritage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import tim2.CulturalHeritage.dto.requestDTO.LocationRequestDTO;
import tim2.CulturalHeritage.dto.responseDTO.LocationResponseDTO;
import tim2.CulturalHeritage.helper.LocationMapper;
import tim2.CulturalHeritage.model.Location;
import tim2.CulturalHeritage.service.LocationService;
import tim2.CulturalHeritage.helper.ApiErrors;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

    @Autowired
    private LocationService locationService;

    private static LocationMapper locationMapper = new LocationMapper();

    @RequestMapping(value = "/by-page", method = RequestMethod.GET)
    public ResponseEntity<Page<LocationResponseDTO>> findAll(Pageable pageable) {

        Page<Location> resultPage = locationService.findAll(pageable);
        List<LocationResponseDTO> locationsDTO = locationMapper.toDtoList(resultPage.toList());
        Page<LocationResponseDTO> pageLocationsDTO = new PageImpl<>(locationsDTO, resultPage.getPageable(),
                resultPage.getTotalElements());

        return new ResponseEntity<>(pageLocationsDTO, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<LocationResponseDTO> findById(@PathVariable Long id) {

        try {
            Location location = locationService.findById(id);
            return new ResponseEntity<>(locationMapper.toDto(location), HttpStatus.OK);
        }        
        catch (NullPointerException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } 
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<LocationResponseDTO> add(@Valid @RequestBody LocationRequestDTO locationRequestDTO, Errors errors) {

        if (errors.hasErrors()) {
            return new ResponseEntity(new ApiErrors(errors.getAllErrors()), HttpStatus.BAD_REQUEST);
        }
        try {
            Location location = locationService.add(locationMapper.toEntity(locationRequestDTO));
            LocationResponseDTO locationResponseDTO = locationMapper.toDto(location);
            return new ResponseEntity<>(locationResponseDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<LocationResponseDTO> update(@Valid @RequestBody LocationRequestDTO locationRequest,
        @PathVariable Long id,
        Errors errors ) {
        if (errors.hasErrors()) {
            return new ResponseEntity(new ApiErrors(errors.getAllErrors()), HttpStatus.BAD_REQUEST);
        }
        try {
            Location location = locationMapper.toEntity(locationRequest);
            location.setId(id);
            location = locationService.update(location);
            LocationResponseDTO locationResponseDTO = locationMapper.toDto(location);
            return new ResponseEntity<>(locationResponseDTO, HttpStatus.OK);
        } 
        catch(EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } 
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        System.out.print(id);
        try {
            locationService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        // if there's a CH in the database which has a reference to a given location
        catch(DataIntegrityViolationException e){
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        //if id == null
        catch(IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        //if a location with a given id doesn't exist in the database
        catch(EmptyResultDataAccessException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
