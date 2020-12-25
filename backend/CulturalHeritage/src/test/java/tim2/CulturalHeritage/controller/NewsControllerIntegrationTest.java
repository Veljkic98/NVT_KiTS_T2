package tim2.CulturalHeritage.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;

import tim2.CulturalHeritage.dto.requestDTO.AuthUserLoginDTO;
import tim2.CulturalHeritage.dto.requestDTO.NewsRequestDTO;
import tim2.CulturalHeritage.dto.responseDTO.AuthUserLoginResponseDTO;
import tim2.CulturalHeritage.dto.responseDTO.NewsResponseDTO;
import tim2.CulturalHeritage.model.Admin;
import tim2.CulturalHeritage.service.NewsService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static tim2.CulturalHeritage.constants.NewsConstants.*;
import static tim2.CulturalHeritage.constants.LoginConstants.*;


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


  
  public HttpHeaders login() {
    ResponseEntity<AuthUserLoginResponseDTO> responseEntity = restTemplate.postForEntity("/auth/login",
            new AuthUserLoginDTO(ADMIN_EMAIL, ADMIN_PASS), AuthUserLoginResponseDTO.class);

    String accessToken = "Bearer " + responseEntity.getBody().getAccessToken();

    HttpHeaders authHeaders = new HttpHeaders();
    authHeaders.add("Authorization", accessToken);
    //auth headers cant be json because of a file
    // authHeaders.setContentType(MediaType.APPLICATION_JSON);
    return authHeaders;
}

  private HttpEntity<LinkedMultiValueMap<String, Object>> createFormData( NewsRequestDTO newsRequestDTO, String path){

    //Dto is param -> body -> form data
    // set JSON content type for dto. 
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<NewsRequestDTO> dto = new HttpEntity<>(newsRequestDTO, headers);

    //params map is corresponding to form data when sending request
    //params map should contains file (multipart file) and  news dto as json
    LinkedMultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();

    //add multipart file to params (to form data when sending request)
    params.add("file", new FileSystemResource(path));
    //add dto obj to params (to form data when sending request)
    params.add("news", dto);

     //add authentication headers when sending request (add admin)
    HttpHeaders headersAuth = login();
    return new HttpEntity<>(params, headersAuth);
  }

  @Test
  public void add_WithFile_ShouldReturnNews(){
    NewsRequestDTO newsRequestDTO = new NewsRequestDTO(null, HEADING, CONTENT, 1, 1);
    String imgPath = "src/test/resources/cultural-heritage-management.jpg";

    HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = createFormData(newsRequestDTO, imgPath);
    
    ResponseEntity<NewsResponseDTO> responseEntity = 
      restTemplate.postForEntity("/api/news", requestEntity, NewsResponseDTO.class);

    NewsResponseDTO newsResponseDTO = responseEntity.getBody();
    assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    assertEquals(HEADING, newsResponseDTO.getHeading());
  }

  @Test
  public void add_WithoutFile_ShouldReturnNews(){
    
  }

}
