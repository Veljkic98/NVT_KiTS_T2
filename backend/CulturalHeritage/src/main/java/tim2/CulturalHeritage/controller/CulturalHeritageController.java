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
import org.springframework.web.multipart.MultipartFile;

import tim2.CulturalHeritage.dto.requestDTO.CulturalHeritageRequestDTO;
import tim2.CulturalHeritage.dto.responseDTO.CulturalHeritageResponseDTO;
import tim2.CulturalHeritage.helper.CulturalHeritageMapper;
import tim2.CulturalHeritage.model.CHSubtype;
import tim2.CulturalHeritage.model.CulturalHeritage;
import tim2.CulturalHeritage.service.CHSubtypeService;
import tim2.CulturalHeritage.service.CulturalHeritageService;

@RestController
@RequestMapping("/api/cultural-heritages")
public class CulturalHeritageController {

    @Autowired
    private CulturalHeritageService culturalHeritageService;

    @Autowired
    private CHSubtypeService subtypeService;

    private static CulturalHeritageMapper chMapper = new CulturalHeritageMapper();

    @GetMapping
    public ResponseEntity<List<CulturalHeritageResponseDTO>> findAll() {

        return new ResponseEntity<>(chMapper.toDtoList(culturalHeritageService.findAll()), HttpStatus.OK);
    }

    @RequestMapping(value = "/by-page", method = RequestMethod.GET)
    public ResponseEntity<Page<CulturalHeritageResponseDTO>> getAllCulturalHeritages(Pageable pageable) {

        Page<CulturalHeritage> page = culturalHeritageService.findAll(pageable);
        List<CulturalHeritageResponseDTO> DTOs = chMapper.toDtoList(page.toList());
        Page<CulturalHeritageResponseDTO> pageResponse = new PageImpl<>(DTOs, page.getPageable(),
                page.getTotalElements());

        return new ResponseEntity<>(pageResponse, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CulturalHeritageResponseDTO> findById(@PathVariable Long id) {

        CulturalHeritage ch = culturalHeritageService.findById(id);
        if (ch == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(chMapper.toDto(ch), HttpStatus.OK);

    }

    // @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<CulturalHeritageResponseDTO> add(@RequestPart("file") MultipartFile file,
            @RequestPart("culturalHeritageRequestDTO") CulturalHeritageRequestDTO culturalHeritageRequestDTO) {

        CulturalHeritage ch = chMapper.toEntity(culturalHeritageRequestDTO);
        culturalHeritageService.add(ch, file);

        CulturalHeritageResponseDTO response = new CulturalHeritageResponseDTO();
        response = chMapper.toDto(ch);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CulturalHeritageResponseDTO> update(
            @RequestBody CulturalHeritageRequestDTO culturalHeritageRequestDTO) {

        try {

            CulturalHeritage ch = chMapper.toEntity(culturalHeritageRequestDTO);
            CHSubtype sub = subtypeService.findById(culturalHeritageRequestDTO.getChsubtypeID());
            ch.setChsubtype(sub);
            culturalHeritageService.update(ch);
            return new ResponseEntity<>(chMapper.toDto(ch), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("GRESKA " + e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        try {
            culturalHeritageService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
