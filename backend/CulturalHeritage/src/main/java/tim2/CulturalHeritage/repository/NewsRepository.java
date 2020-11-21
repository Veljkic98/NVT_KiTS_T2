package tim2.CulturalHeritage.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tim2.CulturalHeritage.model.News;

public interface NewsRepository extends JpaRepository<News, Long> {

}
