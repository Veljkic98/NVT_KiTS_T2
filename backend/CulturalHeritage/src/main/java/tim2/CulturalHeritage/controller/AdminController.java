package tim2.CulturalHeritage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import tim2.CulturalHeritage.dto.requestDTO.AdminRequestDTO;
import tim2.CulturalHeritage.dto.responseDTO.AdminResponseDTO;
import tim2.CulturalHeritage.helper.AdminMapper;
import tim2.CulturalHeritage.helper.ApiErrors;
import tim2.CulturalHeritage.model.Admin;
import tim2.CulturalHeritage.service.AdminService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/admins")
public class AdminController {

    @Autowired
    private AdminService adminService;

    private AdminMapper adminMapper = new AdminMapper();

    @RequestMapping(value="/by-page", method= RequestMethod.GET)
    public ResponseEntity<Page<AdminResponseDTO>> findAll(Pageable pageable) {

        Page<Admin> resultPage = adminService.findAll(pageable);
        List<AdminResponseDTO> adminsDTO = adminMapper.toDtoList(resultPage.toList());
        Page<AdminResponseDTO> pageAdminsDTO = new PageImpl<>(adminsDTO, resultPage.getPageable(), resultPage.getTotalElements());

        return new ResponseEntity<>(pageAdminsDTO, HttpStatus.OK);
    }


    @GetMapping(path = "/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {

        try {
            Admin admin = adminService.findById(id);
            return new ResponseEntity<>(adminMapper.toDto(admin), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> add(@Valid @RequestBody AdminRequestDTO adminRequest, Errors errors) {
        if (errors.hasErrors()) {
            return new ResponseEntity(new ApiErrors(errors.getAllErrors()), HttpStatus.BAD_REQUEST);
        }

        try {
            Admin admin = adminService.add(adminMapper.toEntity(adminRequest));

            return new ResponseEntity<>(adminMapper.toDto(admin), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody AdminRequestDTO adminRequest, Errors errors) {

        if (errors.hasErrors()) {
            return new ResponseEntity(new ApiErrors(errors.getAllErrors()), HttpStatus.BAD_REQUEST);
        }
        try {
            Admin admin = adminMapper.toEntity(adminRequest);
            admin.setId(id);
            adminService.update(admin);

            return new ResponseEntity<>(adminMapper.toDto(admin), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        try {
            adminService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
