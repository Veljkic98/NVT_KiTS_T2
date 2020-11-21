package tim2.CulturalHeritage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim2.CulturalHeritage.model.AuthenticatedUser;
import tim2.CulturalHeritage.repository.AuthenticatedUserRepository;

@Service
public class AuthenticatedUserServiceImpl implements AuthenticatedUserService {

  @Autowired
  private AuthenticatedUserRepository authenticatedUserRepository;

  @Override
  public List<AuthenticatedUser> findAll() {
    return authenticatedUserRepository.findAll();
  }

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
