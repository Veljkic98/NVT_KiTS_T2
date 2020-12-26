package tim2.CulturalHeritage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import tim2.CulturalHeritage.model.FileDB;
import tim2.CulturalHeritage.repository.FileDBRepository;

@Service
public class FileDBServiceImpl implements FileDBService {

    @Autowired
    private FileDBRepository fileDBRepository;

    @Override
    public FileDB add(MultipartFile file) {

        try {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            FileDB fileDB = new FileDB(fileName, file.getContentType(), file.getBytes());

            return fileDBRepository.save(fileDB);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public FileDB findById(Long id) {

        return fileDBRepository.findById(id).orElse(null);
    }

}
