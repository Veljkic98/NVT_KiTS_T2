package tim2.CulturalHeritage.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import tim2.CulturalHeritage.model.Admin;



@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    Page<Admin> findAll(Pageable pageable);
}
