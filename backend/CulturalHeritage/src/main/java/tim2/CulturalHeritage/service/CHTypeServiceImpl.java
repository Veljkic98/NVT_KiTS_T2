package tim2.CulturalHeritage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import tim2.CulturalHeritage.model.CHType;
import tim2.CulturalHeritage.repository.CHTypeRepository;

@Service
public class CHTypeServiceImpl implements CHTypeService {

    @Autowired
    private CHTypeRepository chTypeRepository;

    @Override
    public List<CHType> findAll() {
        return chTypeRepository.findAll();
    }
    public Page<CHType> findAll(Pageable page) {
        return chTypeRepository.findAll(page);
    }

    @Override
    public CHType findById(Long id) {
        return chTypeRepository.findById(id).orElse(null);
    }

    @Override
    public CHType add(CHType chType) {
        return chTypeRepository.save(chType);
    }

    @Override
    public CHType update(CHType chType) {
        return chTypeRepository.save(chType);
    }

    @Override
    public void deleteById(Long id) {
        chTypeRepository.deleteById(id);
    }

}
