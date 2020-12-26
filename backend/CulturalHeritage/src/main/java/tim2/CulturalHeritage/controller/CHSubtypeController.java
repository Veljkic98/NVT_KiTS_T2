package tim2.CulturalHeritage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

import tim2.CulturalHeritage.dto.requestDTO.CHSubtypeRequestDTO;
import tim2.CulturalHeritage.dto.responseDTO.CHSubtypeResponseDTO;
import tim2.CulturalHeritage.helper.ApiErrors;
import tim2.CulturalHeritage.helper.CHSubtypeMapper;
import tim2.CulturalHeritage.model.CHSubtype;
import tim2.CulturalHeritage.service.CHSubtypeService;

import javax.validation.Valid;

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
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<CHSubtypeResponseDTO> add(@Valid @RequestBody CHSubtypeRequestDTO chSubtype, Errors errors) {

        if (errors.hasErrors()) {
            return new ResponseEntity(new ApiErrors(errors.getAllErrors()), HttpStatus.BAD_REQUEST);
        }
        try {
            CHSubtype subtype = mapper.toEntity(chSubtype);
            chSubtypeService.add(subtype);

            return new ResponseEntity<>(mapper.toDto(subtype), HttpStatus.CREATED);
        } catch(DataIntegrityViolationException e) {
            return new ResponseEntity(new ApiErrors("Subtype name must be unique for it's type"), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<CHSubtypeResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody CHSubtypeRequestDTO chSubtype,
            Errors errors) {
        if (errors.hasErrors()) {
            return new ResponseEntity(new ApiErrors(errors.getAllErrors()), HttpStatus.BAD_REQUEST);
        }
        try {
            CHSubtype subtype = mapper.toEntity(chSubtype);
            subtype.setId(id);
            chSubtypeService.update(subtype);

            return new ResponseEntity<>(mapper.toDto(subtype), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
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
