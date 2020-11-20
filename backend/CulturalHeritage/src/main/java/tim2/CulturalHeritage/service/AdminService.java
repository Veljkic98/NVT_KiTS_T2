package tim2.CulturalHeritage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim2.CulturalHeritage.model.Admin;
import tim2.CulturalHeritage.repository.AdminRepository;

@Service
public class AdminService {
    
    @Autowired
    private AdminRepository adminRepository;

    public Admin findOne(Long id) {
		return adminRepository.findById(id).orElseGet(null);
	}
	
	public List<Admin> findAll() {
		return adminRepository.findAll();
	}
	
	public Admin save(Admin Admin) {
		return adminRepository.save(Admin);
	}
	
	public void remove(Long id) {
		adminRepository.deleteById(id);
    }

}
