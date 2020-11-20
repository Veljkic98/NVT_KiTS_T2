package tim2.CulturalHeritage.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tim2.CulturalHeritage.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    
}
