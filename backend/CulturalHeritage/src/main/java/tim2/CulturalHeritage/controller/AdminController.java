package tim2.CulturalHeritage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tim2.CulturalHeritage.dto.responseDTO.AdminResponseDTO;
import tim2.CulturalHeritage.dto.responseDTO.AuthUserResponseDTO;
import tim2.CulturalHeritage.helper.AdminResponseMapper;
import tim2.CulturalHeritage.helper.AuthUserResponseMapper;
import tim2.CulturalHeritage.model.Admin;
import tim2.CulturalHeritage.model.AuthenticatedUser;
import tim2.CulturalHeritage.service.AdminService;

@RestController
@RequestMapping("/api/admins")
public class AdminController {

    @Autowired
    private AdminService adminService;

    private AdminResponseMapper adminMapper = new AdminResponseMapper();

    @RequestMapping(value="/by-page", method= RequestMethod.GET)
    public ResponseEntity<Page<AdminResponseDTO>> findAll(Pageable pageable) {

        Page<Admin> resultPage = adminService.findAll(pageable);
        List<AdminResponseDTO> adminsDTO = adminMapper.toDtoList(resultPage.toList());
        Page<AdminResponseDTO> pageAdminsDTO = new PageImpl<>(adminsDTO, resultPage.getPageable(), resultPage.getTotalElements());

        return new ResponseEntity<>(pageAdminsDTO, HttpStatus.OK);
    }


    @GetMapping(path = "/{id}")
    public ResponseEntity<Void> findById(@PathVariable Long id) {

        try {
            adminService.findById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Admin> add(@RequestBody Admin admin) {

        adminService.add(admin);

        return new ResponseEntity<>(admin, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Admin> update(@RequestBody Admin admin) {

        try {
            adminService.update(admin);
            return new ResponseEntity<>(admin, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        try {
            adminService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
