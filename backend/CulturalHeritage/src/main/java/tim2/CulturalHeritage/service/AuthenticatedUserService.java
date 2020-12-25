package tim2.CulturalHeritage.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import tim2.CulturalHeritage.model.AuthenticatedUser;

public interface AuthenticatedUserService {

    public Page<AuthenticatedUser> findAll(Pageable pageable);

    public List<AuthenticatedUser> findAllSubscribedToCH(Long chID);

    public AuthenticatedUser findById(Long id);

    public AuthenticatedUser add(AuthenticatedUser authenticatedUser);

    public AuthenticatedUser update(AuthenticatedUser authenticatedUser);

    public void deleteById(Long id);

    void setVerified(AuthenticatedUser user);
}