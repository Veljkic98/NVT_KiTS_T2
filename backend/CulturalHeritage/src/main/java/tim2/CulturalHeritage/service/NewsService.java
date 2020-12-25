package tim2.CulturalHeritage.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import tim2.CulturalHeritage.model.News;

public interface NewsService {

    public Page<News> findAll(Pageable pageable);
    
    public Page<News> findAll(Pageable pageable, Long chID);

    public News findById(Long id);

    public News add(News news, MultipartFile file);

    public News update(News news, MultipartFile file);

    public void deleteById(Long id);

}
