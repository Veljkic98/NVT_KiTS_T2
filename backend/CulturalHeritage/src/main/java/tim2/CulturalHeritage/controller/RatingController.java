package tim2.CulturalHeritage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tim2.CulturalHeritage.dto.requestDTO.RatingRequestDTO;
import tim2.CulturalHeritage.helper.RatingMappers.RatingRequestMapper;
import tim2.CulturalHeritage.helper.RatingMappers.RatingResponseMapper;
import tim2.CulturalHeritage.model.Location;
import tim2.CulturalHeritage.model.Rating;
import tim2.CulturalHeritage.service.RatingService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    private RatingRequestMapper ratingRequestMapper = new RatingRequestMapper();

    private RatingResponseMapper ratingResponseMapper = new RatingResponseMapper();

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

    @PostMapping
    public ResponseEntity<?> add(@RequestBody RatingRequestDTO ratingRequest) {

        //ovde bi trebalo preuzeti id ulogovanog korisnika i setovati u novom rating objektu
        try {
            Rating rating = ratingService.add(ratingRequestMapper.toEntity(ratingRequest));

            return new ResponseEntity<>(ratingResponseMapper.toDto(rating), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody RatingRequestDTO ratingRequest) {

        try {
            //ovde bi trebalo preuzeti id ulogovanog korisnika i setovati u novom rating objektu
            Rating rating = ratingRequestMapper.toEntity(ratingRequest);
            rating.setId(id);
            ratingService.update(rating);

            return new ResponseEntity<>(ratingResponseMapper.toDto(rating), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        try {
            ratingService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
