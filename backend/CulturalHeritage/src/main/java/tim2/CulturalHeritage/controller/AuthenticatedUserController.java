package tim2.CulturalHeritage.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tim2.CulturalHeritage.dto.UserResponseDTO;
import tim2.CulturalHeritage.helper.UserMapper;
import tim2.CulturalHeritage.model.AuthenticatedUser;
import tim2.CulturalHeritage.service.AuthenticatedUserService;

@RestController
@RequestMapping("/api/authenticated-users")
public class AuthenticatedUserController {

    @Autowired
    private AuthenticatedUserService authenticatedUserService;

    private UserMapper userMapper = new UserMapper();

    @GetMapping(params = { "page", "size" })
    public ResponseEntity<Page<UserResponseDTO>> findAll(@RequestParam("page") int page,
                                              @RequestParam("size") int size) {

        Pageable pageObj = PageRequest.of(page, size);
        Page<AuthenticatedUser> resultPage = authenticatedUserService.findAll(pageObj);
        List<UserResponseDTO> usersDTO = userMapper.toDtoList(resultPage.toList());
        Page<UserResponseDTO> pageUserDTO = new PageImpl<>(usersDTO, resultPage.getPageable(), resultPage.getTotalElements());

        return new ResponseEntity<>(pageUserDTO, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Void> findById(@PathVariable Long id) {

        try {
            authenticatedUserService.findById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<AuthenticatedUser> add(@RequestBody AuthenticatedUser authenticatedUser) {

        authenticatedUserService.add(authenticatedUser);

        return new ResponseEntity<>(authenticatedUser, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<AuthenticatedUser> update(@RequestBody AuthenticatedUser authenticatedUser) {

        try {
            authenticatedUserService.update(authenticatedUser);
            return new ResponseEntity<>(authenticatedUser, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        try {
            authenticatedUserService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
