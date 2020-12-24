package tim2.CulturalHeritage.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import tim2.CulturalHeritage.model.News;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static tim2.CulturalHeritage.constants.NewsConstants.*;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class NewsRepositoryIntegration {
  
  @Autowired
  private NewsRepository newsRepository;

  //ovo mi ne treba
  @Test
  public void testFindById_ValidID(){
    News found = newsRepository.findById(NEWS_ID).orElse(null);
    assertEquals(NEWS_ID, found.getId()); 
  }

  //ovo mi ne treba
  @Test
  public void testFindById_InvalidID(){
    News found = newsRepository.findById(NEWS_ID + 1L).orElse(null);
    assertNull(found);
  }

  @Test
  public void testFindAll(){
    //TODO: URADITI
  }


}
