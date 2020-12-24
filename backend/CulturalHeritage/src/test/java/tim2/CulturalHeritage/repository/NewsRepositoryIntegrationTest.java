package tim2.CulturalHeritage.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import tim2.CulturalHeritage.model.News;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static tim2.CulturalHeritage.constants.NewsConstants.*;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class NewsRepositoryIntegrationTest {
  
  @Autowired
  private NewsRepository newsRepository;

  @Test
  public void testFindById_ValidID(){
    News found = newsRepository.findById(NEWS_ID).orElse(null);
    assertEquals(NEWS_ID, found.getId()); 
  }

  @Test
  public void testFindById_InvalidID(){
    News found = newsRepository.findById(NEWS_ID_NOT_FOUND).orElse(null);
    assertNull(found);
  }

  @Test
  public void testFindAll(){
    Pageable pageable = PageRequest.of(0, 5);
    Page<News> found = newsRepository.findAll(pageable);
    assertEquals(3, found.getNumberOfElements());
  }
}
