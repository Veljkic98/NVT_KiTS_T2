package tim2.CulturalHeritage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tim2.CulturalHeritage.dto.responseDTO.AdminResponseDTO;
import tim2.CulturalHeritage.model.Admin;
import tim2.CulturalHeritage.service.AdminService;

@RestController
@RequestMapping("/api/admins")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping(params = { "page", "size" })
    public ResponseEntity<List<AdminResponseDTO>> findAll(@RequestParam("page") int page,
                                                          @RequestParam("size") int size) {

        return new ResponseEntity<>(adminService.findAll(Integer.parseInt(String.valueOf(page)),
                Integer.parseInt(String.valueOf(size))), HttpStatus.OK);
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
