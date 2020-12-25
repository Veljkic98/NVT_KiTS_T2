package tim2.CulturalHeritage.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import tim2.CulturalHeritage.dto.requestDTO.FilterRequestDTO;
import tim2.CulturalHeritage.model.CulturalHeritage;

public interface CulturalHeritageService {

    public List<CulturalHeritage> findAll();

    public Page<CulturalHeritage> findAll(Pageable p);

    public CulturalHeritage findById(Long id);

    public CulturalHeritage add(CulturalHeritage culturalHeritage, MultipartFile file);

    public CulturalHeritage update(CulturalHeritage culturalHeritage, MultipartFile file);

    public void deleteById(Long id);

    public Page<CulturalHeritage> filter(FilterRequestDTO filterDTO, Pageable p);
}
