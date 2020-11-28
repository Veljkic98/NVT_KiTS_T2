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

import tim2.CulturalHeritage.model.CulturalHeritage;
import tim2.CulturalHeritage.service.CulturalHeritageService;

@RestController
@RequestMapping("/api/cultural-heritages")
public class CulturalHeritageController {

    @Autowired
    private CulturalHeritageService culturalHeritageService;

    @GetMapping
    public ResponseEntity<List<CulturalHeritage>> findAll() {

        return new ResponseEntity<>(culturalHeritageService.findAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Void> findById(@PathVariable Long id) {

        try {
            culturalHeritageService.findById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<CulturalHeritage> add(@RequestBody CulturalHeritage culturalHeritage) {

        culturalHeritageService.add(culturalHeritage);

        return new ResponseEntity<>(culturalHeritage, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<CulturalHeritage> update(@RequestBody CulturalHeritage culturalHeritage) {

        try {
            culturalHeritageService.update(culturalHeritage);
            return new ResponseEntity<>(culturalHeritage, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        try {
            culturalHeritageService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
