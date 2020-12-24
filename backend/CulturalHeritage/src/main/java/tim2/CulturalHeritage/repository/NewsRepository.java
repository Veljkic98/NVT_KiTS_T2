package tim2.CulturalHeritage.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tim2.CulturalHeritage.model.News;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    public Page<News> findAll(Pageable pageable);

    @Query("select n from News n where n.culturalHeritage.id is ?1")
    public Page<News> findAll(Pageable pageable, Long chID);
}
