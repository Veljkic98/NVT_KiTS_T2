package tim2.CulturalHeritage.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import tim2.CulturalHeritage.dto.requestDTO.FilterRequestDTO;
import tim2.CulturalHeritage.model.AuthenticatedUser;
import tim2.CulturalHeritage.model.CulturalHeritage;
import tim2.CulturalHeritage.model.FileDB;
import tim2.CulturalHeritage.repository.CulturalHeritageRepository;

@Service
public class CulturalHeritageServiceImpl implements CulturalHeritageService {

    @Autowired
    private CulturalHeritageRepository culturalHeritageRepository;

    @Autowired
    private FileDBService fileDBService;

    @Override
    public List<CulturalHeritage> findAll() {
        return culturalHeritageRepository.findAll();
    }

    public Page<CulturalHeritage> findAll(Pageable pageable) {
        return culturalHeritageRepository.findAll(pageable);
    }

    @Override
    public CulturalHeritage findById(Long id) {
        return culturalHeritageRepository.findById(id).orElse(null);
    }

    @Override
    public CulturalHeritage add(CulturalHeritage culturalHeritage, MultipartFile file) {

        try {
            FileDB fileDB;
            fileDB = fileDBService.add(file);
            culturalHeritage.setImages(fileDB);

            return culturalHeritageRepository.save(culturalHeritage);
        } catch (NullPointerException e) {
            return culturalHeritageRepository.save(culturalHeritage);
        }
    }

    @Override
    public CulturalHeritage update(CulturalHeritage culturalHeritage, MultipartFile file) {

        CulturalHeritage culturalHeritage2 = culturalHeritageRepository.findById(culturalHeritage.getId()).orElse(null);

        if (null == culturalHeritage2)
            throw new EntityNotFoundException();

        FileDB fileDB = fileDBService.add(file);
        culturalHeritage.setImages(fileDB);

        culturalHeritage.setNews(culturalHeritage2.getNews());
        culturalHeritage.setComments(culturalHeritage2.getComments());
        culturalHeritage.setRatings(culturalHeritage2.getRatings());

        return culturalHeritageRepository.save(culturalHeritage);
    }

    @Override
    public void deleteById(Long id) {

        if (null == culturalHeritageRepository.findById(id).orElse(null))
            throw new EntityNotFoundException();

        culturalHeritageRepository.deleteById(id);
    }

    @Override
    public void unsubscribe(Long id) {

        AuthenticatedUser user = (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        CulturalHeritage ch = culturalHeritageRepository.findById(id).orElse(null);

        user.getCulturalHeritages().remove(ch);

        culturalHeritageRepository.save(ch);
    }

    @Override
    public Page<CulturalHeritage> filter(FilterRequestDTO filterDTO, Pageable page) {

        Page<CulturalHeritage> res;

        if (filterDTO.getType().equalsIgnoreCase("name")) {
            res = culturalHeritageRepository.findByNameContains(filterDTO.getValue(), page);
        } else if (filterDTO.getType().equalsIgnoreCase("chSubtypeName")) {
            res = culturalHeritageRepository.findByChsubtypeNameContains(filterDTO.getValue(), page);
        } else if (filterDTO.getType().equalsIgnoreCase("locationCity")) {
            res = culturalHeritageRepository.findByLocationCity(filterDTO.getValue(), page);
        } else if (filterDTO.getType().equals("locationCountry")) {
            res = culturalHeritageRepository.findByLocationCountry(filterDTO.getValue(), page);
        } else {
            res = culturalHeritageRepository.findAll(page);
        }
        return res;
    }

}
