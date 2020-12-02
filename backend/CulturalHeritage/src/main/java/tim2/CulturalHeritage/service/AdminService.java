package tim2.CulturalHeritage.service;

import java.util.List;

import tim2.CulturalHeritage.dto.responseDTO.AdminResponseDTO;
import tim2.CulturalHeritage.model.Admin;

public interface AdminService {

    public List<AdminResponseDTO> findAll(int page, int size);

    public Admin findById(Long id);

    public Admin add(Admin admin);

    public Admin update(Admin admin);

    public void deleteById(Long id);
}
