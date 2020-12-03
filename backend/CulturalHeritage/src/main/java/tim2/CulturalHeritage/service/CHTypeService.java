package tim2.CulturalHeritage.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tim2.CulturalHeritage.model.CHType;

public interface CHTypeService {

    public List<CHType> findAll();
    public Page<CHType> findAll(Pageable pageable);

    public CHType findById(Long id);

    public CHType add(CHType chType);

    public CHType update(CHType chType);

    public void deleteById(Long id);
}
