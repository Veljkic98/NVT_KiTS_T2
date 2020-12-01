package tim2.CulturalHeritage.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import tim2.CulturalHeritage.dto.UserResponseDTO;
import tim2.CulturalHeritage.model.AuthenticatedUser;
import tim2.CulturalHeritage.repository.AuthenticatedUserRepository;

@Service
public class AuthenticatedUserServiceImpl implements AuthenticatedUserService {

    @Autowired
    private AuthenticatedUserRepository authenticatedUserRepository;

    @Override
    public Page<AuthenticatedUser> findAll(Pageable pageable) { return authenticatedUserRepository.findAll(pageable); }

    @Override
    public AuthenticatedUser findById(Long id) {
        return authenticatedUserRepository.findById(id).orElse(null);
    }

    @Override
    public AuthenticatedUser add(AuthenticatedUser authenticatedUser) {
        return authenticatedUserRepository.save(authenticatedUser);
    }

    @Override
    public AuthenticatedUser update(AuthenticatedUser authenticatedUser) {
        return authenticatedUserRepository.save(authenticatedUser);
    }

    @Override
    public void deleteById(Long id) {
        authenticatedUserRepository.deleteById(id);
    }

}
