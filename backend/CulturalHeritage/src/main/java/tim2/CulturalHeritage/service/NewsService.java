package tim2.CulturalHeritage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim2.CulturalHeritage.model.News;
import tim2.CulturalHeritage.repository.NewsRepository;

@Service
public class NewsService {
    
    @Autowired
    private NewsRepository newsRepository;

    public News findOne(Long id) {
		return newsRepository.findById(id).orElseGet(null);
	}
	
	public List<News> findAll() {
		return newsRepository.findAll();
	}
	
	public News save(News Admin) {
		return newsRepository.save(Admin);
	}
	
	public void remove(Long id) {
		newsRepository.deleteById(id);
    }

}
