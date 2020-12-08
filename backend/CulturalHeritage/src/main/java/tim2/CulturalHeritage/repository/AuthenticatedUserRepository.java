package tim2.CulturalHeritage.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tim2.CulturalHeritage.model.AuthenticatedUser;

@Repository
public interface AuthenticatedUserRepository extends JpaRepository<AuthenticatedUser, Long> {

    Page<AuthenticatedUser> findAll(Pageable pageable);

    AuthenticatedUser findByEmail(String email);
}
