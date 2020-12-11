package tim2.CulturalHeritage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import tim2.CulturalHeritage.dto.requestDTO.NewsRequestDTO;
import tim2.CulturalHeritage.helper.NewsMapper;
import tim2.CulturalHeritage.model.Admin;
import tim2.CulturalHeritage.model.CulturalHeritage;
import tim2.CulturalHeritage.model.FileDB;
import tim2.CulturalHeritage.model.News;
import tim2.CulturalHeritage.repository.NewsRepository;

@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private FileDBService fileDBService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private CulturalHeritageService culturalHeritageService;

    @Override
    public Page<News> findAll(Pageable pageable) {
        return newsRepository.findAll(pageable);
    }

    @Override
    public News findById(Long id) {
        return newsRepository.findById(id).orElse(null);
    }

    @Override
    public News add(NewsRequestDTO newsRequestDTO, MultipartFile file) {

        NewsMapper newsMapper = new NewsMapper();
        News news = new News();

        try {
            Admin admin = adminService.findById(newsRequestDTO.getAdminID());
            CulturalHeritage culturalHeritage = culturalHeritageService.findById(newsRequestDTO.getCulturalHeritageID());

            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            FileDB fileDB = new FileDB(fileName, file.getContentType(), file.getBytes());

            news = newsMapper.toEntity(newsRequestDTO);
            news.setAdmin(admin);
            news.setCulturalHeritage(culturalHeritage);
            news.setImages(fileDB);

            fileDBService.add(fileDB);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return newsRepository.save(news);
    }

    @Override
    public News update(News news) {
        return newsRepository.save(news);
    }

    @Override
    public void deleteById(Long id) {
        newsRepository.deleteById(id);
    }

}
