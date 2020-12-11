package tim2.CulturalHeritage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim2.CulturalHeritage.model.FileDB;
import tim2.CulturalHeritage.repository.FileDBRepository;

@Service
public class FileDBServiceImpl implements FileDBService {
    
    @Autowired
    private FileDBRepository fileDBRepository;

    @Override
    public FileDB add(FileDB fileDB) {
        
        return fileDBRepository.save(fileDB);
    }

	@Override
	public FileDB findById(Long id) {

		return fileDBRepository.findById(id).orElse(null);
    }
    
}
