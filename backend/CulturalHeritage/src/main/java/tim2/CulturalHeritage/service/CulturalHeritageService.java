package tim2.CulturalHeritage.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tim2.CulturalHeritage.dto.CulturalHeritageDTO;
import tim2.CulturalHeritage.model.CulturalHeritage;

public interface CulturalHeritageService {

    public List<CulturalHeritage> findAll();

    public Page<CulturalHeritage> findAll(Pageable p);

    public CulturalHeritage findById(Long id);

    public CulturalHeritage add(CulturalHeritage culturalHeritage);

    public CulturalHeritage update(CulturalHeritage culturalHeritage);

    public void deleteById(Long id);
}
