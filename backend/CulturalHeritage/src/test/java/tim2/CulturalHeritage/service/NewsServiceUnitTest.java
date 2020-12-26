package tim2.CulturalHeritage.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static tim2.CulturalHeritage.constants.NewsConstants.*;

import tim2.CulturalHeritage.model.Admin;
import tim2.CulturalHeritage.model.CulturalHeritage;
import tim2.CulturalHeritage.model.News;
import tim2.CulturalHeritage.repository.NewsRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class NewsServiceUnitTest {
  @Autowired
  private NewsService newsService;

  @MockBean
  private NewsRepository newsRepository;

  @Before
  public void setup(){
    News news = new News(null, HEADING, CONTENT, null, null, null);
    Admin admin = new Admin();
    admin.setId(1L);
    CulturalHeritage ch = new CulturalHeritage();
    ch.setId(1L);
    News newsWithID = new News(NEWS_ID, HEADING, CONTENT, ch, admin, null);

    given(newsRepository.findById(NEWS_ID)).willReturn(Optional.of(newsWithID));
    given(newsRepository.save(news)).willReturn(newsWithID);

  }

  @Test
  public void findById_ValidID_ShouldReturnNews(){
    News found = newsService.findById(NEWS_ID);
    verify(newsRepository, times(1)).findById(NEWS_ID);
    assertEquals(NEWS_ID, found.getId());
  }

}
