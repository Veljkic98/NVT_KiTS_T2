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

import tim2.CulturalHeritage.dto.requestDTO.AuthUserRequestDTO;
import tim2.CulturalHeritage.dto.responseDTO.AuthUserResponseDTO;
import tim2.CulturalHeritage.helper.ApiErrors;
import tim2.CulturalHeritage.helper.AuthenticatedUserMapper;
import tim2.CulturalHeritage.model.AuthenticatedUser;
import tim2.CulturalHeritage.service.AuthenticatedUserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/authenticated-users")
public class AuthenticatedUserController {

    @Autowired
    private AuthenticatedUserService authenticatedUserService;

    private AuthenticatedUserMapper userMapper = new AuthenticatedUserMapper();

    @GetMapping(path = "/by-page")
    public ResponseEntity<Page<AuthUserResponseDTO>> findAll(Pageable pageable) {

        Page<AuthenticatedUser> resultPage = authenticatedUserService.findAll(pageable);
        List<AuthUserResponseDTO> usersDTO = userMapper.toDtoList(resultPage.toList());
        Page<AuthUserResponseDTO> pageUserDTO = new PageImpl<>(usersDTO, resultPage.getPageable(), resultPage.getTotalElements());

        return new ResponseEntity<>(pageUserDTO, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<AuthUserResponseDTO> findById(@PathVariable Long id) {

        try {
            AuthenticatedUser user = authenticatedUserService.findById(id);

            return new ResponseEntity<>(userMapper.toDto(user), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/verify/{id}")
    public void verify(@PathVariable long id) {
        AuthenticatedUser user = authenticatedUserService.findById(id);
        if (user != null) {
            authenticatedUserService.setVerified(user);
        }
    }


    @PostMapping
    public ResponseEntity<?> add(@Valid @RequestBody AuthUserRequestDTO authenticatedUser, Errors errors) {
        if (errors.hasErrors()) {
            return new ResponseEntity(new ApiErrors(errors.getAllErrors()), HttpStatus.BAD_REQUEST);
        }
        try {
            AuthenticatedUser user = userMapper.toEntity(authenticatedUser);
            authenticatedUserService.add(user);

            return new ResponseEntity<>(userMapper.toDto(user), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiErrors("User with given email already exists"), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody AuthUserRequestDTO authenticatedUser) {

        try {
            AuthenticatedUser user = userMapper.toEntity(authenticatedUser);
            user.setId(id);
            authenticatedUserService.update(user);

            return new ResponseEntity<>(userMapper.toDto(user), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value="/{id}", method= RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        try {
            authenticatedUserService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
