package tim2.CulturalHeritage.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.MethodMode;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;

import tim2.CulturalHeritage.dto.requestDTO.AuthUserLoginDTO;
import tim2.CulturalHeritage.dto.requestDTO.CulturalHeritageRequestDTO;
import tim2.CulturalHeritage.dto.responseDTO.AuthUserLoginResponseDTO;
import tim2.CulturalHeritage.dto.responseDTO.CulturalHeritageResponseDTO;
import tim2.CulturalHeritage.model.CulturalHeritage;
import tim2.CulturalHeritage.restTemplateHelp.RestResponsePage;
import tim2.CulturalHeritage.service.CulturalHeritageService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static tim2.CulturalHeritage.constants.CulturalHeritageConstants.*;
import static tim2.CulturalHeritage.constants.LoginConstants.*;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class CulturalHeritageControllerIntegrationTest {

  @Autowired
  private TestRestTemplate restTemplate;

  @Autowired
  private CulturalHeritageService culturalHeritageService;

  private Pageable pageable = PageRequest.of(0, PAGE_SIZE);

  public HttpHeaders login() {
    ResponseEntity<AuthUserLoginResponseDTO> responseEntity = restTemplate.postForEntity("/auth/login",
        new AuthUserLoginDTO(ADMIN_EMAIL, ADMIN_PASS), AuthUserLoginResponseDTO.class);

    String accessToken = "Bearer " + responseEntity.getBody().getAccessToken();

    HttpHeaders authHeaders = new HttpHeaders();
    authHeaders.add("Authorization", accessToken);
    // auth headers cant be json because of a file
    // authHeaders.setContentType(MediaType.APPLICATION_JSON);
    return authHeaders;
  }

  private HttpEntity<LinkedMultiValueMap<String, Object>> createFormData(CulturalHeritageRequestDTO chDTO,
      String path) {

    // Dto is param -> body -> form data
    // set JSON content type for dto.
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<CulturalHeritageRequestDTO> dto = new HttpEntity<>(chDTO, headers);

    // params map is corresponding to form data when sending request
    // params map should contains file (multipart file) and news dto as json
    LinkedMultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();

    // add multipart file to params (to form data when sending request) if file is
    // passed
    if (null == path || "".equals(path)) {
      params.add("file", null);
    } else {
      params.add("file", new FileSystemResource(path));
    }
      
    //add dto obj to params (to form data when sending request)
    params.add("culturalHeritageRequestDTO", dto);

    // add authentication headers when sending request (add admin)
    HttpHeaders headersAuth = login();
    return new HttpEntity<>(params, headersAuth);
  }

  @Test
  @DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
  public void add_WithFile_ShouldReturnCH(){
    CulturalHeritageRequestDTO chDTO = new CulturalHeritageRequestDTO(NAME, DESCRIPTION, LOCATION_ID, CH_SUBTYPE_ID);
    String imgPath = "src/test/resources/cultural-heritage-management.jpg";

    HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = createFormData(chDTO, imgPath);

    ResponseEntity<CulturalHeritageResponseDTO> responseEntity = 
    restTemplate.exchange("/api/cultural-heritages", HttpMethod.POST ,requestEntity, CulturalHeritageResponseDTO.class);

    assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    assertEquals(NUMBER_OF_CH_IN_DB + 1, culturalHeritageService.findAll(pageable).getNumberOfElements());
  }
  @Test
  @DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
  public void add_WithoutFile_ShouldBadRequest(){
    CulturalHeritageRequestDTO chDTO = new CulturalHeritageRequestDTO(NAME, DESCRIPTION, LOCATION_ID, CH_SUBTYPE_ID);

    HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = createFormData(chDTO, "");

    ResponseEntity<CulturalHeritageResponseDTO> responseEntity = 
    restTemplate.exchange("/api/cultural-heritages", HttpMethod.POST ,requestEntity, CulturalHeritageResponseDTO.class);

    assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    assertEquals(NUMBER_OF_CH_IN_DB, culturalHeritageService.findAll(pageable).getNumberOfElements());
  }

  
  @Test
  @DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
  public void update_ValidID_ShouldReturnCH() {
    CulturalHeritageRequestDTO chDTO = new CulturalHeritageRequestDTO(NAME, DESCRIPTION, LOCATION_ID, CH_SUBTYPE_ID);
    String imgPath = "src/test/resources/cultural-heritage-management.jpg";

    HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = createFormData(chDTO, imgPath);

    ResponseEntity<CulturalHeritageResponseDTO> responseEntity = restTemplate
        .exchange("/api/cultural-heritages/" + CH_ID, HttpMethod.PUT, requestEntity, CulturalHeritageResponseDTO.class);

    CulturalHeritageResponseDTO chResponseDTO = responseEntity.getBody();
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals(NAME, chResponseDTO.getName());
    assertEquals(NUMBER_OF_CH_IN_DB, culturalHeritageService.findAll(pageable).getNumberOfElements());

  }

  @Test
  @DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
  public void update_InvalidID_ShouldReturnNotFound(){
    CulturalHeritageRequestDTO chDTO = new CulturalHeritageRequestDTO(NAME, DESCRIPTION, LOCATION_ID, CH_SUBTYPE_ID);
    String imgPath = "src/test/resources/cultural-heritage-management.jpg";

    HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = createFormData(chDTO, imgPath);

    ResponseEntity<CulturalHeritageResponseDTO> responseEntity = 
    restTemplate.exchange("/api/cultural-heritages/" + CH_ID_NOT_FOUND, HttpMethod.PUT ,requestEntity, CulturalHeritageResponseDTO.class);

    assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
  }

  @Test
  @DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
  public void delete_NotLoggedIn_ShouldReturnUNAUTHORIZED(){
    ResponseEntity<Void> responseEntity = 
      restTemplate.exchange("/api/cultural-heritages/" + CH_ID, HttpMethod.DELETE, null, Void.class);
    
    assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
  }

  @Test
  @DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
  public void delete_LoggedInValidID_ShouldDelete(){
    HttpHeaders authHeaders = login();
    HttpEntity<Object> requestEntity = new HttpEntity<>(null, authHeaders);
    
    ResponseEntity<Void> responseEntity = 
      restTemplate.exchange("/api/cultural-heritages/" + CH_ID, HttpMethod.DELETE, requestEntity, Void.class);

    assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    assertEquals(NUMBER_OF_CH_IN_DB -1, culturalHeritageService.findAll(pageable).getNumberOfElements());
  }

  @Test
  @DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
  public void delete_InvalidID_ShouldNotDelete(){
    HttpHeaders authHeaders = login();
    HttpEntity<Object> requestEntity = new HttpEntity<Object>(null, authHeaders);
    
    ResponseEntity<Void> responseEntity = 
      restTemplate.exchange("/api/cultural-heritages/" + CH_ID_NOT_FOUND, HttpMethod.DELETE, requestEntity, Void.class);
    
    assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    assertEquals(NUMBER_OF_CH_IN_DB, culturalHeritageService.findAll(pageable).getNumberOfElements());
  }

  @Test
  public void findAll_ok_listAndOk() {
    Page<CulturalHeritage> chPage = culturalHeritageService.findAll(pageable);
    List<CulturalHeritage> chList = chPage.getContent();

    int size = chList.size();

    ParameterizedTypeReference<RestResponsePage<CulturalHeritageResponseDTO>> responseType = new ParameterizedTypeReference<RestResponsePage<CulturalHeritageResponseDTO>>() {
    };

    ResponseEntity<RestResponsePage<CulturalHeritageResponseDTO>> responseEntity = restTemplate
        .exchange("/api/cultural-heritages/by-page/?page=0&size=5&sort=id,ASC", HttpMethod.GET, null/* httpEntity */, responseType);

    List<CulturalHeritageResponseDTO> responseList = responseEntity.getBody().getContent();

    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals(responseList.size(), size);
  }

  @Test
  @DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
  public void findById_ValidID_ShouldReturnCH(){
    ResponseEntity<CulturalHeritageResponseDTO> responseEntity= 
      restTemplate.getForEntity("/api/cultural-heritages/" + CH_ID, CulturalHeritageResponseDTO.class);

    CulturalHeritageResponseDTO culturalHeritageResponseDTO = responseEntity.getBody();
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals(CH_ID, culturalHeritageResponseDTO.getId());
  }

  @Test
  @DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
  public void findById_InvalidID_ShouldReturnNotFound(){
    ResponseEntity<CulturalHeritageResponseDTO> responseEntity= 
      restTemplate.getForEntity("/api/cultural-heritages/" + CH_ID_NOT_FOUND, CulturalHeritageResponseDTO.class);

    CulturalHeritageResponseDTO culturalHeritageResponseDTO = responseEntity.getBody();
    assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    assertNull(culturalHeritageResponseDTO);
  }
}
