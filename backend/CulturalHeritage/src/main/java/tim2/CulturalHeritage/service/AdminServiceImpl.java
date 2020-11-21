package tim2.CulturalHeritage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim2.CulturalHeritage.model.Admin;
import tim2.CulturalHeritage.repository.AdminRepository;

@Service
public class AdminServiceImpl implements AdminService {

  @Autowired
  private AdminRepository adminRepository;

  @Override
  public List<Admin> findAll() {
    return adminRepository.findAll();
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
