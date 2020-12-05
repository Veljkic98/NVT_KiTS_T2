package tim2.CulturalHeritage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import tim2.CulturalHeritage.model.AuthenticatedUser;
import tim2.CulturalHeritage.repository.AuthenticatedUserRepository;

@Service
public class AuthenticatedUserServiceImpl implements AuthenticatedUserService {

    @Autowired
    private AuthenticatedUserRepository authenticatedUserRepository;

    @Autowired
    private EmailService emailService;

    private final String linkBase = "http://localhost:8080/api/authenticated-users/verify/";

    @Override
    public Page<AuthenticatedUser> findAll(Pageable pageable) { return authenticatedUserRepository.findAll(pageable); }

    @Override
    public AuthenticatedUser findById(Long id) {
        return authenticatedUserRepository.findById(id).orElse(null);
    }

    @Override
    public AuthenticatedUser add(AuthenticatedUser authenticatedUser) {

       AuthenticatedUser user = authenticatedUserRepository.save(authenticatedUser);
       try {
           String link = linkBase + user.getId();
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

}
