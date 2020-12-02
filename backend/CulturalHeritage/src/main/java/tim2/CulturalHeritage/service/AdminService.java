package tim2.CulturalHeritage.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tim2.CulturalHeritage.dto.responseDTO.AdminResponseDTO;
import tim2.CulturalHeritage.model.Admin;
import tim2.CulturalHeritage.model.AuthenticatedUser;

public interface AdminService {

    public Page<Admin> findAll(Pageable pageable);

    public Admin findById(Long id);

    public Admin add(Admin admin);

    public Admin update(Admin admin);

    public void deleteById(Long id);
}
