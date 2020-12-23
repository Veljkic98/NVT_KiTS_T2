package tim2.CulturalHeritage.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import tim2.CulturalHeritage.model.News;
import tim2.CulturalHeritage.repository.NewsRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class NewsServiceUnit {
  @Autowired
  private NewsService newsService;

  @MockBean
  private NewsRepository newsRepository;

  @Before
  public void setup(){
    News news = new News();
    news.setId(1L);
    given(newsRepository.findById(1L)).willReturn(Optional.of(news));
  }
  @Test
  public void test(){
    assertNull(null);
  }

  @Test
  public void testFindById(){
    News found = newsService.findById(1L);
    System.out.println("==============================");
    System.out.println(found);
    verify(newsRepository, times(1)).findById(1L);
    assertEquals(1L, found.getId());
  }
}
