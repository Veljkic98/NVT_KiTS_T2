package tim2.CulturalHeritage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim2.CulturalHeritage.model.Person;
import tim2.CulturalHeritage.repository.PersonRepository;

@Service
public class PersonServiceImpl implements PersonService {

  @Autowired
  private PersonRepository personRepository;

  @Override
  public List<Person> findAll() {
    return personRepository.findAll();
  }

  @Override
  public Person findById(Long id) {
    return personRepository.findById(id).orElse(null);
  }

  @Override
  public Person add(Person person) {
    return personRepository.save(person);
  }

  @Override
  public Person update(Person person) {
    return personRepository.save(person);
  }

  @Override
  public void deleteById(Long id) {
    personRepository.deleteById(id);
  }
  
}
