package tim2.CulturalHeritage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import tim2.CulturalHeritage.dto.requestDTO.CHTypeRequestDTO;
import tim2.CulturalHeritage.dto.responseDTO.CHTypeResponseDTO;
import tim2.CulturalHeritage.helper.ApiErrors;
import tim2.CulturalHeritage.helper.CHTypeMapper;
import tim2.CulturalHeritage.model.CHType;
import tim2.CulturalHeritage.service.CHTypeService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/ch-types")
public class CHTypeController {

    @Autowired
    private CHTypeService chTypeService;

    private CHTypeMapper mapper = new CHTypeMapper();

    @RequestMapping(value = "/by-page", method = RequestMethod.GET)
    public ResponseEntity<Page<CHTypeResponseDTO>> getAllCulturalHeritages(Pageable pageable) {
        Page<CHType> page = chTypeService.findAll(pageable);
        List<CHTypeResponseDTO> DTOs = mapper.toDtoList(page.toList());
        Page<CHTypeResponseDTO> pageResponse = new PageImpl<>(DTOs, page.getPageable(), page.getTotalElements());

        return new ResponseEntity<>(pageResponse, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CHTypeResponseDTO> findById(@PathVariable Long id) {

        try {
            CHType type = chTypeService.findById(id);

            if (type == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(mapper.toDto(type), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<CHTypeResponseDTO> add(@Valid @RequestBody CHTypeRequestDTO typeDTO, Errors errors) {

        if (errors.hasErrors())
            return new ResponseEntity(new ApiErrors(errors.getAllErrors()), HttpStatus.BAD_REQUEST);

        try {
            CHType type = mapper.toEntity(typeDTO);
            chTypeService.add(type);

            return new ResponseEntity<>(mapper.toDto(type), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<CHTypeResponseDTO> update(@PathVariable Long id, @Valid @RequestBody CHTypeRequestDTO reqDTO,
            Errors errors) {
        if (errors.hasErrors()) {
            return new ResponseEntity(new ApiErrors(errors.getAllErrors()), HttpStatus.BAD_REQUEST);
        }
        try {
            CHType type = mapper.toEntity(reqDTO);
            type.setId(id);
            chTypeService.update(type);

            return new ResponseEntity<>(mapper.toDto(type), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        try {
            chTypeService.deleteById(id);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
