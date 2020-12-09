package tim2.CulturalHeritage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tim2.CulturalHeritage.model.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    Authority findByName(String name);
}
