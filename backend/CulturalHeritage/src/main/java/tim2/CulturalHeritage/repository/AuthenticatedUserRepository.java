package tim2.CulturalHeritage.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tim2.CulturalHeritage.model.AuthenticatedUser;

public interface AuthenticatedUserRepository extends JpaRepository<AuthenticatedUser, Long> {
    
}
