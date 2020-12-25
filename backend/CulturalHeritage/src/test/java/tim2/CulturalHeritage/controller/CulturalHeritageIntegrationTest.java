package tim2.CulturalHeritage.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;

import tim2.CulturalHeritage.dto.requestDTO.AuthUserLoginDTO;
import tim2.CulturalHeritage.dto.requestDTO.CulturalHeritageRequestDTO;
import tim2.CulturalHeritage.dto.responseDTO.AuthUserLoginResponseDTO;
import tim2.CulturalHeritage.dto.responseDTO.CulturalHeritageResponseDTO;
import tim2.CulturalHeritage.model.CulturalHeritage;
import tim2.CulturalHeritage.service.CulturalHeritageService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tim2.CulturalHeritage.constants.CulturalHeritageConstants.*;
import static tim2.CulturalHeritage.constants.LoginConstants.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class CulturalHeritageIntegrationTest {
  
  @Autowired
  private TestRestTemplate restTemplate;

  @Autowired
  private CulturalHeritageService culturalHeritageService;

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
  private HttpEntity<LinkedMultiValueMap<String, Object>> createFormData( CulturalHeritageRequestDTO chDTO, String path){

    //Dto is param -> body -> form data
    // set JSON content type for dto. 
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<CulturalHeritageRequestDTO> dto = new HttpEntity<>(chDTO, headers);

    //params map is corresponding to form data when sending request
    //params map should contains file (multipart file) and  news dto as json
    LinkedMultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();

    //add multipart file to params (to form data when sending request) if file is passed
    if(null == path || "".equals(path)){
      params.add("file", null);
     }
    else{
      params.add("file", new FileSystemResource(path));
    }
      
    //add dto obj to params (to form data when sending request)
    params.add("culturalHeritageRequestDTO", dto);

     //add authentication headers when sending request (add admin)
    HttpHeaders headersAuth = login();
    return new HttpEntity<>(params, headersAuth);
  }

  
  @Test
  public void update_ValidID_ShouldReturnCH(){
    CulturalHeritageRequestDTO chDTO = new CulturalHeritageRequestDTO(NAME, DESCRIPTION, LOCATION_ID, CH_SUBTYPE_ID);
    String imgPath = "src/test/resources/cultural-heritage-management.jpg";

    HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = createFormData(chDTO, imgPath);

    ResponseEntity<CulturalHeritageResponseDTO> responseEntity = 
    restTemplate.exchange("/api/cultural-heritages/" + CH_ID, HttpMethod.PUT ,requestEntity, CulturalHeritageResponseDTO.class);

    CulturalHeritageResponseDTO chResponseDTO = responseEntity.getBody();
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
  }
}
