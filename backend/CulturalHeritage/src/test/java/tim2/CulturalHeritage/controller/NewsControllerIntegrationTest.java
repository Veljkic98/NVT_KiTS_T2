package tim2.CulturalHeritage.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import tim2.CulturalHeritage.dto.responseDTO.NewsResponseDTO;
import tim2.CulturalHeritage.service.NewsService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static tim2.CulturalHeritage.constants.NewsConstants.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class NewsControllerIntegrationTest {

  @Autowired
  private TestRestTemplate restTemplate;

  @Autowired
  private NewsService newsService;

  
  @Test
  public void findById_ValidId_ShouldReturnNews(){

    ResponseEntity<NewsResponseDTO> responseEntity = 
      restTemplate.getForEntity("/api/news/1", NewsResponseDTO.class);

    NewsResponseDTO newsResponseDTO = responseEntity.getBody();
    assertEquals("Naslov", newsResponseDTO.getHeading());
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
  }

  @Test
  public void findById_InvalidId_ShouldThrowException(){

    ResponseEntity<NewsResponseDTO> responseEntity = 
      restTemplate.getForEntity("/api/news/100", NewsResponseDTO.class);

    NewsResponseDTO newsResponseDTO = responseEntity.getBody();
    assertNull(newsResponseDTO);
    assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
  }

}
