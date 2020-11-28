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

import tim2.CulturalHeritage.model.CHSubtype;
import tim2.CulturalHeritage.service.CHSubtypeService;

@RestController
@RequestMapping("/api/ch-subtypes")
public class CHSubtypeController {

    @Autowired
    private CHSubtypeService chSubtypeService;

    @GetMapping
    public ResponseEntity<List<CHSubtype>> findAll() {

        return new ResponseEntity<>(chSubtypeService.findAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Void> findById(@PathVariable Long id) {

        try {
            chSubtypeService.findById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<CHSubtype> add(@RequestBody CHSubtype chSubtype) {

        chSubtypeService.add(chSubtype);

        return new ResponseEntity<>(chSubtype, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<CHSubtype> update(@RequestBody CHSubtype chSubtype) {

        try {
            chSubtypeService.update(chSubtype);
            return new ResponseEntity<>(chSubtype, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        try {
            chSubtypeService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
