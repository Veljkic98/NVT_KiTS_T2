package tim2.CulturalHeritage.service;

import org.springframework.web.multipart.MultipartFile;

import tim2.CulturalHeritage.model.FileDB;

public interface FileDBService {
    
    public FileDB add(MultipartFile file);
    
    public FileDB findById(Long id);
}
