package tim2.CulturalHeritage.service;

import java.util.List;

import tim2.CulturalHeritage.model.News;


public interface NewsService {

	public List<News> findAll();

  public News findById(Long id);

  public News add(News news);

  public News update(News news);

  public void deleteById(Long id);


}
