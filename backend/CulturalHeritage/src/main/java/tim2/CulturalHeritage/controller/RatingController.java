package tim2.CulturalHeritage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
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
import tim2.CulturalHeritage.helper.ApiErrors;
import tim2.CulturalHeritage.helper.RatingMapper;
import tim2.CulturalHeritage.model.AuthenticatedUser;
import tim2.CulturalHeritage.model.CulturalHeritage;
import tim2.CulturalHeritage.model.Rating;
import tim2.CulturalHeritage.service.CulturalHeritageService;
import tim2.CulturalHeritage.service.RatingService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @Autowired
    private CulturalHeritageService culturalHeritageService;

    private RatingMapper ratingMapper = new RatingMapper();

    @GetMapping
    public ResponseEntity<List<Rating>> findAll() {

        return new ResponseEntity<>(ratingService.findAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Void> findById(@PathVariable Long id) {

        try {
            ratingService.findById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    public ResponseEntity<?> add(@Valid @RequestBody RatingRequestDTO ratingRequest, Errors errors) {
        if (errors.hasErrors()) {
            return new ResponseEntity(new ApiErrors(errors.getAllErrors()), HttpStatus.BAD_REQUEST);
        }
        AuthenticatedUser user = (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {
            Rating rating = ratingService.add(ratingMapper.toEntity(ratingRequest));
            rating.setAuthenticatedUser(user);
            CulturalHeritage ch = culturalHeritageService.findById(ratingRequest.getCulturalHeritageId());
            rating.setCulturalHeritage(ch);
            ratingService.add(rating);

            return new ResponseEntity<>(ratingMapper.toDto(rating), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,@Valid @RequestBody RatingRequestDTO ratingRequest, Errors errors) {
        if (errors.hasErrors()) {
            return new ResponseEntity(new ApiErrors(errors.getAllErrors()), HttpStatus.BAD_REQUEST);
        }
        AuthenticatedUser user = (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            Rating rating = ratingService.findById(id);
            if (rating.getAuthenticatedUser().getId() != user.getId()) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            rating.setGrade(ratingRequest.getGrade());
            ratingService.update(rating);

            return new ResponseEntity<>(ratingMapper.toDto(rating), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        AuthenticatedUser user = (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            Rating rating = ratingService.findById(id);
            if (rating.getAuthenticatedUser().getId() != user.getId()) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            ratingService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
