package tim2.CulturalHeritage.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import tim2.CulturalHeritage.dto.AdminDTO;
import tim2.CulturalHeritage.model.Admin;
import tim2.CulturalHeritage.repository.AdminRepository;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public List<AdminDTO> findAll(int page, int size) {
        Pageable firstPageWithTwoElements = PageRequest.of(page, size);
        Page<Admin> admins =  adminRepository.findAll(firstPageWithTwoElements);
        List<AdminDTO> results = new ArrayList<>();

        for (Admin admin: admins){
            results.add(new AdminDTO(admin.getId(), admin.getFirstName(), admin.getLastName(), admin.getEmail()));
        }
        System.out.println(results.size());
        return results;
    }

    @Override
    public Admin findById(Long id) {
        return adminRepository.findById(id).orElse(null);
    }

    @Override
    public Admin add(Admin admin) {
        return adminRepository.save(admin);
    }

    @Override
    public Admin update(Admin admin) {
        return adminRepository.save(admin);
    }

    @Override
    public void deleteById(Long id) {
        adminRepository.deleteById(id);

    }
}
