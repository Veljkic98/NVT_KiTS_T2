package tim2.CulturalHeritage.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tim2.CulturalHeritage.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

}
