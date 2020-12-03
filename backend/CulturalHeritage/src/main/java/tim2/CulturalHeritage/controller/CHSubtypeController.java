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

import tim2.CulturalHeritage.dto.requestDTO.CHSubtypeRequestDTO;
import tim2.CulturalHeritage.dto.responseDTO.CHSubtypeResponseDTO;
import tim2.CulturalHeritage.helper.CHSubtypeMapper;
import tim2.CulturalHeritage.model.CHSubtype;
import tim2.CulturalHeritage.service.CHSubtypeService;

@RestController
@RequestMapping("/api/ch-subtypes")
public class CHSubtypeController {

    @Autowired
    private CHSubtypeService chSubtypeService;
    private CHSubtypeMapper mapper = new CHSubtypeMapper();

    @GetMapping
    public ResponseEntity<List<CHSubtypeResponseDTO>> findAll() {

        return new ResponseEntity<>(mapper.toDtoList(chSubtypeService.findAll()), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CHSubtypeResponseDTO> findById(@PathVariable Long id) {

        try {
            CHSubtype subtype = chSubtypeService.findById(id);

            if(subtype == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }


            return new ResponseEntity<>(mapper.toDto(subtype), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<CHSubtypeResponseDTO> add(@RequestBody CHSubtypeRequestDTO chSubtype) {
        CHSubtype subtype = mapper.toEntity(chSubtype);
        chSubtypeService.add(subtype);


        return new ResponseEntity<>(mapper.toDto(subtype), HttpStatus.CREATED);
    }


    @PutMapping
    public ResponseEntity<CHSubtypeResponseDTO> update(@RequestBody CHSubtypeResponseDTO chSubtype) {

        try {
            CHSubtype subtype = mapper.toEntity(chSubtype);
            chSubtypeService.update(subtype);

            return new ResponseEntity<>(mapper.toDto(subtype), HttpStatus.OK);
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
