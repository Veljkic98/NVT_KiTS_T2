package tim2.CulturalHeritage.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import tim2.CulturalHeritage.dto.requestDTO.AuthUserLoginDTO;
import tim2.CulturalHeritage.dto.responseDTO.AuthUserLoginResponseDTO;
import tim2.CulturalHeritage.helper.AuthenticatedUserMapper;
import tim2.CulturalHeritage.model.AuthenticatedUser;
import tim2.CulturalHeritage.security.TokenUtils;
import tim2.CulturalHeritage.service.AuthenticatedUserServiceImpl;
import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private AuthenticatedUserServiceImpl userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;


    private AuthenticatedUserMapper userMapper;

    // Prvi endpoint koji pogadja korisnik kada se loguje.
    // Tada zna samo svoje korisnicko ime i lozinku i to prosledjuje na backend.
    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthUserLoginDTO authenticationRequest,
                                                                    HttpServletResponse response) {

        Authentication authentication = authenticationManager.
                authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));

        // Ubaci korisnika u trenutni security kontekst
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Kreiraj token za tog korisnika
        AuthenticatedUser user = (AuthenticatedUser) authentication.getPrincipal();
        String jwt = tokenUtils.generateToken(user.getEmail()); // prijavljujemo se na sistem sa email adresom
        int expiresIn = tokenUtils.getExpiredIn();

        // Vrati token kao odgovor na uspesnu autentifikaciju
        return ResponseEntity.ok(new AuthUserLoginResponseDTO(jwt, expiresIn));
    }

    // Endpoint za registraciju novog korisnika
//    @PostMapping("/sign-up")
//    public ResponseEntity<?> addUser(@RequestBody UserDTO userRequest) throws Exception {
//
//        User existUser = this.userService.findByEmail(userRequest.getEmail());
//        if (existUser != null) {
//            throw new Exception("Username already exists");
//        }
//
//        try {
//            existUser = userService.create(userMapper.toEntity(userRequest));
//        } catch (Exception e) {
//            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
//        }
//        return new ResponseEntity<>(userMapper.toDto(existUser), HttpStatus.CREATED);
//    }

    // U slucaju isteka vazenja JWT tokena, endpoint koji se poziva da se token osvezi
//    @PostMapping(value = "/refresh")
//    public ResponseEntity<AuthUserLoginResponseDTO> refreshAuthenticationToken(HttpServletRequest request) {
//
//        String token = tokenUtils.getToken(request);
//        String username = this.tokenUtils.getUsernameFromToken(token);
//        AuthenticatedUser user = (AuthenticatedUser) this.userDetailsService.loadUserByUsername(username);
//
//        if (this.tokenUtils.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
//            String refreshedToken = tokenUtils.refreshToken(token);
//            int expiresIn = tokenUtils.getExpiredIn();
//
//            return ResponseEntity.ok(new UserTokenStateDTO(refreshedToken, expiresIn));
//        } else {
//            AuthUserLoginResponseDTO userTokenState = new AuthUserLoginResponseDTO();
//            return ResponseEntity.badRequest().body(userTokenState);
//        }
//    }

//    @RequestMapping(value = "/change-password", method = RequestMethod.POST)
//    @PreAuthorize("hasRole('ROLE_USER')")
//    public ResponseEntity<?> changePassword(@RequestBody PasswordChanger passwordChanger) {
//        userDetailsService.changePassword(passwordChanger.oldPassword, passwordChanger.newPassword);
//
//        Map<String, String> result = new HashMap<>();
//        result.put("result", "success");
//        return ResponseEntity.accepted().body(result);
//    }
//
//    static class PasswordChanger {
//        public String oldPassword;
//        public String newPassword;
//    }

    public AuthenticationController() {
        userMapper = new AuthenticatedUserMapper();
    }
}
