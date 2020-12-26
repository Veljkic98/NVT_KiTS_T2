package tim2.CulturalHeritage.controller;

import java.security.AccessControlException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tim2.CulturalHeritage.dto.requestDTO.RatingRequestDTO;
import tim2.CulturalHeritage.dto.responseDTO.RatingResponseDTO;
import tim2.CulturalHeritage.helper.ApiErrors;
import tim2.CulturalHeritage.helper.RatingMapper;
import tim2.CulturalHeritage.model.Rating;
import tim2.CulturalHeritage.service.RatingService;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;
    private RatingMapper ratingMapper = new RatingMapper();

    @GetMapping(value = "/by-page")
    public ResponseEntity<Page<RatingResponseDTO>> findAll(Pageable pageable) {

        Page<Rating> resultPage = ratingService.findAll(pageable);
        List<RatingResponseDTO> ratingsDTO = ratingMapper.toDtoList(resultPage.toList());
        Page<RatingResponseDTO> ratingsDTOPage = new PageImpl<>(ratingsDTO, resultPage.getPageable(),
                resultPage.getTotalElements());

        return new ResponseEntity<>(ratingsDTOPage, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<RatingResponseDTO> findById(@PathVariable Long id) {
        try{
            Rating rating = ratingService.findById(id);
            RatingResponseDTO response = ratingMapper.toDto(rating);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (NullPointerException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } 
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<RatingResponseDTO> add(@Valid @RequestBody RatingRequestDTO ratingRequestDTO, Errors errors) {

        if (errors.hasErrors())
            return new ResponseEntity(new ApiErrors(errors.getAllErrors()), HttpStatus.BAD_REQUEST);

        try{
            Rating rating = ratingMapper.toEntity(ratingRequestDTO);
            rating = ratingService.add(rating);
            RatingResponseDTO response = ratingMapper.toDto(rating);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody RatingRequestDTO ratingRequestDTO,
            Errors errors) {

        if (errors.hasErrors())
            return new ResponseEntity(new ApiErrors(errors.getAllErrors()), HttpStatus.BAD_REQUEST);

        try {
            Rating updatedRating = ratingMapper.toEntity(ratingRequestDTO);
            updatedRating.setId(id);
            updatedRating = ratingService.update(updatedRating);
            RatingResponseDTO response = ratingMapper.toDto(updatedRating);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch(AccessControlException e){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        catch(EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } 
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        try {
            ratingService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch(AccessControlException e){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } 
        catch(EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } 
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
