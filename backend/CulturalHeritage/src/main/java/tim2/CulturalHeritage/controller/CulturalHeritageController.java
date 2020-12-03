package tim2.CulturalHeritage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tim2.CulturalHeritage.dto.responseDTO.CulturalHeritageResponseDTO;
import tim2.CulturalHeritage.helper.CulturalHeritageMapper;
import tim2.CulturalHeritage.model.CulturalHeritage;
import tim2.CulturalHeritage.service.CulturalHeritageService;

@RestController
@RequestMapping("/api/cultural-heritages")
public class CulturalHeritageController {

    @Autowired
    private CulturalHeritageService culturalHeritageService;

    private CulturalHeritageMapper mapper;

    @GetMapping
    public ResponseEntity<List<CulturalHeritageResponseDTO>> findAll() {

        return new ResponseEntity<>(mapper.toDtoList(culturalHeritageService.findAll()), HttpStatus.OK);
    }

    @RequestMapping(value="/by-page", method= RequestMethod.GET)
    public ResponseEntity<Page<CulturalHeritageResponseDTO>> getAllCulturalHeritages(Pageable pageable){
        Page<CulturalHeritage> page = culturalHeritageService.findAll(pageable);
        List<CulturalHeritageResponseDTO> DTOs = mapper.toDtoList(page.toList());
        Page<CulturalHeritageResponseDTO> pageResponse =  new PageImpl<>(DTOs,page.getPageable(),page.getTotalElements());

        return new ResponseEntity<>(pageResponse, HttpStatus.OK);
    }



    @GetMapping(path = "/{id}")
    public ResponseEntity<CulturalHeritageResponseDTO> findById(@PathVariable Long id) {


        CulturalHeritage ch = culturalHeritageService.findById(id);
        if(ch == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(mapper.toDto(ch), HttpStatus.OK);

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
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
