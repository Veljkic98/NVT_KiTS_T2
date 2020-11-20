package tim2.CulturalHeritage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim2.CulturalHeritage.model.Person;
import tim2.CulturalHeritage.repository.PersonRepository;

@Service
public class PersonService {
    
    @Autowired
    private PersonRepository personRepository;

    public Person findOne(Long id) {
		return personRepository.findById(id).orElseGet(null);
	}
	
	public List<Person> findAll() {
		return personRepository.findAll();
	}
	
	public Person save(Person Admin) {
		return personRepository.save(Admin);
	}
	
	public void remove(Long id) {
		personRepository.deleteById(id);
    }

}
