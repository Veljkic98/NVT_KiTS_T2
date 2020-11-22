package tim2.CulturalHeritage.service;

import java.util.List;

import tim2.CulturalHeritage.model.Person;

public interface PersonService {

    public List<Person> findAll();

    public Person findById(Long id);

    public Person add(Person person);

    public Person update(Person person);

    public void deleteById(Long id);
}
