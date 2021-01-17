package tim2.CulturalHeritage.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import tim2.CulturalHeritage.model.*;
import tim2.CulturalHeritage.repository.AdminRepository;
import tim2.CulturalHeritage.repository.AuthenticatedUserRepository;

@Service
public class AuthenticatedUserServiceImpl implements UserDetailsService, AuthenticatedUserService {

    @Autowired
    private AuthenticatedUserRepository authenticatedUserRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private EmailService emailService;

    @Autowired
    private AuthorityService authorityService;

    private final String linkBaseURL = "http://localhost:4200/verify/";

    @Override
    public Person loadUserByUsername(String email) throws UsernameNotFoundException {

        // ako se ne radi nasledjivanje, paziti gde sve treba da se proveri email
        AuthenticatedUser user = authenticatedUserRepository.findByEmail(email);
        Admin admin = adminRepository.findByEmail(email);

        if (admin != null) {
            return admin;
        } else if (user != null) {
            return user;
        } else {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", email));
        }
    }

    @Override
    public Page<AuthenticatedUser> findAll(Pageable pageable) {

        return authenticatedUserRepository.findAll(pageable);
    }

    @Override
    public AuthenticatedUser findById(Long id) {

        return authenticatedUserRepository.findById(id).orElse(null);
    }

    @Override
    public AuthenticatedUser add(AuthenticatedUser authenticatedUser) {

        authenticatedUser.setPassword(passwordEncoder.encode(authenticatedUser.getPassword()));
        List<Authority> auth = authorityService.findByName("ROLE_USER");
        authenticatedUser.setAuthorities(auth);
        AuthenticatedUser user = authenticatedUserRepository.save(authenticatedUser);

        try {
            String link = linkBaseURL + user.getId();
            emailService.sendVerificationLink(link, user.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public AuthenticatedUser update(AuthenticatedUser authenticatedUser) {

        return authenticatedUserRepository.save(authenticatedUser);
    }

    @Override
    public void deleteById(Long id) {

        authenticatedUserRepository.deleteById(id);
    }

    @Override
    public void setVerified(AuthenticatedUser user) {

        user.setApproved(true);
        authenticatedUserRepository.save(user);
    }

    public void changePassword(String oldPassword, String newPassword) {

        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        String username = ((AuthenticatedUser) currentUser.getPrincipal()).getEmail();

        if (authenticationManager != null)
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, oldPassword));
        else
            return;
        AuthenticatedUser user = (AuthenticatedUser) loadUserByUsername(username);

        user.setPassword(passwordEncoder.encode(newPassword));
        authenticatedUserRepository.save(user);
    }

    @Override
    public List<AuthenticatedUser> findAllSubscribedToCH(Long chID) {

        List<AuthenticatedUser> usersAll = authenticatedUserRepository.findAll();
        List<AuthenticatedUser> subscribedUsers = new ArrayList<>();

        for (AuthenticatedUser user : usersAll) {
            for (CulturalHeritage ch : user.getCulturalHeritages()) {
                if (ch.getId() == chID && !subscribedUsers.contains(user))
                    subscribedUsers.add(user);
            }
        }

        return subscribedUsers;
    }

}
