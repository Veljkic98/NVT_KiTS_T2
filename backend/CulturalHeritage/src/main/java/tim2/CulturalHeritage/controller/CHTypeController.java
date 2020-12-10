package tim2.CulturalHeritage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import tim2.CulturalHeritage.dto.requestDTO.CHTypeRequestDTO;
import tim2.CulturalHeritage.dto.responseDTO.CHTypeResponseDTO;
import tim2.CulturalHeritage.helper.CHTypeMapper;
import tim2.CulturalHeritage.model.CHType;
import tim2.CulturalHeritage.service.CHTypeService;

@RestController
@RequestMapping("/api/ch-types")
public class CHTypeController {

    @Autowired
    private CHTypeService chTypeService;

    private CHTypeMapper mapper = new CHTypeMapper();

    @GetMapping
    public ResponseEntity<List<CHTypeResponseDTO>> findAll() {

        return new ResponseEntity<>(mapper.toDtoList(chTypeService.findAll()), HttpStatus.OK);
    }

    @RequestMapping(value="/by-page", method= RequestMethod.GET)
    public ResponseEntity<Page<CHTypeResponseDTO>> getAllCulturalHeritages(Pageable pageable){
        Page<CHType> page = chTypeService.findAll(pageable);
        List<CHTypeResponseDTO> DTOs = mapper.toDtoList(page.toList());
        Page<CHTypeResponseDTO> pageResponse =  new PageImpl<>(DTOs,page.getPageable(),page.getTotalElements());

        return new ResponseEntity<>(pageResponse, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CHTypeResponseDTO> findById(@PathVariable Long id) {

        try {
            CHType type = chTypeService.findById(id);

            if(type == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(mapper.toDto(type),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<CHTypeResponseDTO> add(@RequestBody CHTypeRequestDTO typeDTO) {
        CHType type = mapper.toEntity(typeDTO);
        chTypeService.add(type);

        return new ResponseEntity<>(mapper.toDto(type), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping
    public ResponseEntity<CHTypeResponseDTO> update(@RequestBody CHTypeResponseDTO reqDTO) {

        try {
            CHType type = mapper.toEntity(reqDTO);
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
