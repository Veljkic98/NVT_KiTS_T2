package tim2.CulturalHeritage.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.MethodMode;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import tim2.CulturalHeritage.model.FileDB;

import static tim2.CulturalHeritage.constants.FileDBConstants.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class FileDBServiceIntegrationTest {

  @Autowired
  private FileDBService fileDBService;

  @Test
  @DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
  public void add_WithFile_ShouldReturnFile() throws IOException {
    File image = new File("src/test/resources/cultural-heritage-management.jpg");
    byte[] imageBytes= Files.readAllBytes(image.toPath());
    MockMultipartFile file = new MockMultipartFile("file", "image.jpg", MediaType.IMAGE_JPEG_VALUE, imageBytes);

    FileDB created =fileDBService.add(file);
    assertEquals(FILE_ID, created.getId());
  }

  @Test
  @DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
  public void add_WithoutFile_ShouldReturnNull() throws IOException {
    MockMultipartFile file = null;

    FileDB created =fileDBService.add(file);
    assertNull(created);
  }

  @Test
  @DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
  public void findById_ValidID_ShouldReturnFileDB() throws IOException{
    //post file in the db
    File image = new File("src/test/resources/cultural-heritage-management.jpg");
    byte[] imageBytes= Files.readAllBytes(image.toPath());
    MockMultipartFile file = new MockMultipartFile("file", "image.jpg", MediaType.IMAGE_JPEG_VALUE, imageBytes);
    fileDBService.add(file);

    //find by id
    FileDB found = fileDBService.findById(FILE_ID);
    assertEquals(FILE_ID, found.getId());
  }

  @Test
  public void findById_InvalidID_ShoudReturnNull(){
    FileDB found = fileDBService.findById(FILE_ID_NOT_FOUND);
    assertNull(found);
  }
}
