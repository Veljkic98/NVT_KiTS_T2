package tim2.CulturalHeritage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim2.CulturalHeritage.model.AuthenticatedUser;
import tim2.CulturalHeritage.repository.AuthenticatedUserRepository;

@Service
public class AuthenticatedUserService {
    
    @Autowired
    private AuthenticatedUserRepository authenticatedUserRepository;

    public AuthenticatedUser findOne(Long id) {
		return authenticatedUserRepository.findById(id).orElseGet(null);
	}
	
	public List<AuthenticatedUser> findAll() {
		return authenticatedUserRepository.findAll();
	}
	
	public AuthenticatedUser save(AuthenticatedUser Admin) {
		return authenticatedUserRepository.save(Admin);
	}
	
	public void remove(Long id) {
		authenticatedUserRepository.deleteById(id);
    }

}
