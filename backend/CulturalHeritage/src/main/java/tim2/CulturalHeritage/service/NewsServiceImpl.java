package tim2.CulturalHeritage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import tim2.CulturalHeritage.model.FileDB;
import tim2.CulturalHeritage.model.News;
import tim2.CulturalHeritage.repository.NewsRepository;

@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private FileDBService fileDBService;

    @Override
    public Page<News> findAll(Pageable pageable) {
        return newsRepository.findAll(pageable);
    }

    @Override
    public News findById(Long id) {
        return newsRepository.findById(id).orElse(null);
    }

    @Override
    public News add(News news, MultipartFile file) {

        FileDB fileDB;
        fileDB = fileDBService.add(file);
        news.setImages(fileDB);

        return newsRepository.save(news);
    }

    @Override
    public News update(News news, MultipartFile file) {

        FileDB fileDB;
        fileDB = fileDBService.add(file);
        news.setImages(fileDB);

        if (null == newsRepository.findById(news.getId())) {
            throw new IllegalArgumentException("There is no news with id: " + news.getId() + ".");
        }

        return newsRepository.save(news);
    }

    @Override
    public void deleteById(Long id) {

        if (id == null)
            throw new IllegalArgumentException("Id cannot be null");

        newsRepository.deleteById(id);
    }

}
