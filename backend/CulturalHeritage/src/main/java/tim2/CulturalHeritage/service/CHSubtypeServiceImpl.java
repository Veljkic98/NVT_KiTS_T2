package tim2.CulturalHeritage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim2.CulturalHeritage.model.CHSubtype;
import tim2.CulturalHeritage.repository.CHSubtypeRepository;

@Service
public class CHSubtypeServiceImpl implements CHSubtypeService {
  
  @Autowired
  private CHSubtypeRepository chSubtypeRepository;

  @Override
  public List<CHSubtype> findAll() {
    return chSubtypeRepository.findAll();
  }

  @Override
  public CHSubtype findById(Long id) {
    return chSubtypeRepository.findById(id).orElse(null);
  }

  @Override
  public CHSubtype add(CHSubtype chSubtype) {
    return chSubtypeRepository.save(chSubtype);
  }

  @Override
  public CHSubtype update(CHSubtype chSubtype) {
    return chSubtypeRepository.save(chSubtype);
  }

  @Override
  public void deleteById(Long id) {
    chSubtypeRepository.deleteById(id);
  }
  
}
