package tim2.CulturalHeritage.service;

import java.util.List;

import tim2.CulturalHeritage.model.Admin;

public interface AdminService {
  
  public List<Admin> findAll();

  public Admin findById(Long id);

  public Admin add(Admin admin);

  public Admin update(Admin admin);

  public void deleteById(Long id);
}
