package tim2.CulturalHeritage.service;

import tim2.CulturalHeritage.model.FileDB;

public interface FileDBService {
    
    public FileDB add(FileDB fileDB);
    
    public FileDB findById(Long id);
}
