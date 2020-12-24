package tim2.CulturalHeritage.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import tim2.CulturalHeritage.model.News;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tim2.CulturalHeritage.constants.NewsConstants.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource("classpath:test.properties")
public class NewsRepositoryUnit {
  
  @Autowired
  private TestEntityManager entityManager;
  
  @Autowired
  private NewsRepository newsRepository;

  @Before
  public void setup(){
    entityManager.persist(new News(null, HEADING, CONTENT, null, null, null));
  }

  @Test
  public void findById_ValidID(){
    News found = newsRepository.findById(1L).orElse(null);
    assertEquals(CONTENT, found.getContent());
  }
}
