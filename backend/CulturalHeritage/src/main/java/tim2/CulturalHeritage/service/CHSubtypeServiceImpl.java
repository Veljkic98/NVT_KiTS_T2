package tim2.CulturalHeritage.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import tim2.CulturalHeritage.model.CHSubtype;
import tim2.CulturalHeritage.repository.CHSubtypeRepository;

import javax.persistence.EntityNotFoundException;

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
    public CHSubtype add(CHSubtype chSubtype) throws DataIntegrityViolationException {
        CHSubtype existName = chSubtypeRepository.findByName(chSubtype.getName());
        if(existName != null) {
            throw new DataIntegrityViolationException("Name must be unique");
        }
        return chSubtypeRepository.save(chSubtype);
    }

    @Override
    public CHSubtype update(CHSubtype chSubtype) {
        return chSubtypeRepository.save(chSubtype);
    }

    @Override
    public void deleteById(Long id) {
        Optional<CHSubtype> exists = chSubtypeRepository.findById(id);
        if(exists.isEmpty()) {
            throw new EntityNotFoundException("There is no subtype with given id");
        }

        chSubtypeRepository.deleteById(id);
    }

}
