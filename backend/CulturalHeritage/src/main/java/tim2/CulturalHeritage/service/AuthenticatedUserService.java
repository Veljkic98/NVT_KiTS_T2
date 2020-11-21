package tim2.CulturalHeritage.service;

import java.util.List;

import tim2.CulturalHeritage.model.AuthenticatedUser;

public interface AuthenticatedUserService {

    public List<AuthenticatedUser> findAll();

    public AuthenticatedUser findById(Long id);

    public AuthenticatedUser add(AuthenticatedUser authenticatedUser);

    public AuthenticatedUser update(AuthenticatedUser authenticatedUser);

    public void deleteById(Long id);
}