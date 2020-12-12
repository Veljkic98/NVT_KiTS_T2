package tim2.CulturalHeritage.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

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
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            FileDB fileDB = new FileDB(fileName, file.getContentType(), file.getBytes());

            culturalHeritage.setImages(fileDB);
            
            fileDBService.add(fileDB);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return culturalHeritageRepository.save(culturalHeritage);
    }

    @Override
    public CulturalHeritage update(CulturalHeritage culturalHeritage) {
        return culturalHeritageRepository.save(culturalHeritage);
    }

    @Override
    public void deleteById(Long id) {
        culturalHeritageRepository.deleteById(id);
    }

}
