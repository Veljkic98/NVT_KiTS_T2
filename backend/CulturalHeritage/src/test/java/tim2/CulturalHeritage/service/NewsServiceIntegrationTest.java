package tim2.CulturalHeritage.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import static tim2.CulturalHeritage.constants.NewsConstants.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.MethodMode;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import tim2.CulturalHeritage.model.News;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class NewsServiceIntegrationTest {
  @Autowired
  private NewsService newsService;

  @Test
  public void findAll_shouldReturnPageable() {
    Pageable pageable = PageRequest.of(0, 5);
    Page<News> found = newsService.findAll(pageable);
    assertEquals(3, found.getNumberOfElements());
  }

  @Test
  public void findById_ValidID_ShouldReturnNews() {
    News found = newsService.findById(NEWS_ID);
    assertEquals(NEWS_ID, found.getId());
  }

  @Test
  public void findById_InvalidID_ShoudReturnNull() {
    News found = newsService.findById(100L);
    assertNull(found);
  }

  @Test
  @DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
  public void add_WithoutFile_ShouldReturnNews() {
    News news = new News(null, HEADING, CONTENT, null, null, null);
    MockMultipartFile file = null;
    News created = newsService.add(news, file);
    assertEquals(HEADING, created.getHeading());
  }

  @Test
  @DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
  public void add_WithFile_ShouldReturnNews() throws IOException {
    News news = new News(null, HEADING, CONTENT, null, null, null);
    File image = new File("src/test/resources/cultural-heritage-management.jpg");
    byte[] imageBytes= Files.readAllBytes(image.toPath());
    MockMultipartFile file = new MockMultipartFile("file", "image.jpg", MediaType.IMAGE_JPEG_VALUE, imageBytes);
    
    News created = newsService.add(news, file);
    assertEquals(HEADING, created.getHeading());
  }

  @Test(expected = DataIntegrityViolationException.class)
  @DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
  public void add_WithoutHeading_ShouldThrowException(){
    News news = new News(null, null, CONTENT, null, null, null);
    MockMultipartFile file = null;
    News created = newsService.add(news, file);
  }

  @Test
  @DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
  public void update_ValidID_ShouldRetunNews(){
    News news = new News(NEWS_ID, HEADING, CONTENT, null, null, null);
    MockMultipartFile file = null;
    News updated = newsService.update(news, file);
    assertEquals(HEADING, updated.getHeading());
  }

  @Test(expected = EntityNotFoundException.class)
  @DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
  public void update_InvalidID_ShouldThrowException(){
    News news = new News(NEWS_ID_NOT_FOUND, HEADING, CONTENT, null, null, null);
    MockMultipartFile file = null;
    News updated = newsService.update(news, file);
    assertEquals(HEADING, updated.getHeading());
  }

  @Test
  @DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
  public void delete_ValidID_ShouldDelete(){
    newsService.deleteById(NEWS_ID);
  }

  @Test(expected = EntityNotFoundException.class)
  @DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
  public void delete_InvalidID_ShouldThrowException(){
    newsService.deleteById(NEWS_ID_NOT_FOUND);
  }

  @Test
  public void findAllForCH_chIdOk_list() {

      Pageable pageable = PageRequest.of(0, PAGE_SIZE);

      Page<News> newsPage = newsService.findAll(pageable, CH_ID);
      
      List<News> newsList = newsPage.getContent();
      assertEquals(newsList.size(),3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void findAllForCH_chIdNull_list() {

      Pageable pageable = PageRequest.of(0, PAGE_SIZE);

      newsService.findAll(pageable, null);
  }

  @Test(expected = EntityNotFoundException.class)
  public void findAllForCH_chIdNotExists_list() {

      Pageable pageable = PageRequest.of(0, PAGE_SIZE);

      newsService.findAll(pageable, CH_ID_NOT_EXISTS);
  }

}
