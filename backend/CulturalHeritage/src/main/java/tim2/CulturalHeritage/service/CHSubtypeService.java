package tim2.CulturalHeritage.service;

import java.util.List;

import tim2.CulturalHeritage.model.CHSubtype;

public interface CHSubtypeService {

    public List<CHSubtype> findAll();

    public CHSubtype findById(Long id);

    public CHSubtype add(CHSubtype chSubtype);

    public CHSubtype update(CHSubtype chSubtype);

    public void deleteById(Long id);
}
