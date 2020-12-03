package tim2.CulturalHeritage.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tim2.CulturalHeritage.model.Admin;

public interface AdminService {

    public Page<Admin> findAll(Pageable pageable);

    public Admin findById(Long id);

    public Admin add(Admin admin);

    public Admin update(Admin admin);

    public void deleteById(Long id);
}
