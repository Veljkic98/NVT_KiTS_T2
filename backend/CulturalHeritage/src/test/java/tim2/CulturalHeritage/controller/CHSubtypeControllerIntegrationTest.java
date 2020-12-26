package tim2.CulturalHeritage.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import tim2.CulturalHeritage.dto.requestDTO.AuthUserLoginDTO;
import tim2.CulturalHeritage.dto.requestDTO.CHSubtypeRequestDTO;
import tim2.CulturalHeritage.dto.responseDTO.AuthUserLoginResponseDTO;
import tim2.CulturalHeritage.dto.responseDTO.CHSubtypeResponseDTO;
import tim2.CulturalHeritage.helper.ApiErrors;
import tim2.CulturalHeritage.model.CHSubtype;
import tim2.CulturalHeritage.service.CHSubtypeService;


import javax.persistence.EntityNotFoundException;
import java.util.List;

import static tim2.CulturalHeritage.constants.CHSubtypeConstants.*;
import static tim2.CulturalHeritage.constants.LoginConstants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;



@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class CHSubtypeControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    CHSubtypeService chSubtypeService;

    private HttpHeaders headers;

    private HttpHeaders withoutTokenHeaders = new HttpHeaders();


    @Before
    public void login() {
        ResponseEntity<AuthUserLoginResponseDTO> responseEntity = restTemplate.postForEntity("/auth/login",
                new AuthUserLoginDTO(ADMIN_EMAIL, ADMIN_PASS), AuthUserLoginResponseDTO.class);

        String accessToken = "Bearer " + responseEntity.getBody().getAccessToken();

        headers = new HttpHeaders();
        headers.add("Authorization", accessToken);

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Content-Type", "application/json");
    }

    @Test
    public void testGetAllSubtypes() throws JsonProcessingException {
        HttpEntity<Object> httpEntity = new HttpEntity<>(withoutTokenHeaders);

        ObjectMapper objectMapper = new ObjectMapper();

        ResponseEntity<String> responseEntity =
                restTemplate.exchange("/api/ch-subtypes", HttpMethod.GET, httpEntity, String.class);

        List<CHSubtypeResponseDTO> subtypes = objectMapper.readValue(responseEntity.getBody(), new TypeReference<List<CHSubtypeResponseDTO>>() {});
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(SUBTYPES_ALL, subtypes.size());
    }

    @Test
    public void testCreateSubtypeValid() {
        HttpEntity<Object> httpEntity = new HttpEntity<>(new CHSubtypeRequestDTO(NEW_VALID_SUBTYPE_NAME, EXIST_TYPE_ID), headers);
        ResponseEntity<CHSubtypeResponseDTO> responseEntity =
                restTemplate.exchange("/api/ch-subtypes", HttpMethod.POST, httpEntity, CHSubtypeResponseDTO.class);
        CHSubtypeResponseDTO created = responseEntity.getBody();

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(NEW_VALID_SUBTYPE_NAME, created.getName());

        assertDoesNotThrow(() -> {
            chSubtypeService.deleteById(created.getId());
        });
    }

    @Test
    public void testCreateSubtypeInvalidName() {
        HttpEntity<Object> httpEntity = new HttpEntity<>(new CHSubtypeRequestDTO(NEW_INVALID_SUBTYPE_NAME, EXIST_TYPE_ID), headers);

        ResponseEntity<CHSubtypeResponseDTO> responseEntity =
                restTemplate.exchange("/api/ch-subtypes", HttpMethod.POST, httpEntity, CHSubtypeResponseDTO.class);
        CHSubtypeResponseDTO created = responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(created.getId());
    }

    @Test
    public void testCreateSubcategoryInvalidCategory(){
        HttpEntity<Object> httpEntity = new HttpEntity<>(new CHSubtypeRequestDTO(NEW_VALID_SUBTYPE_NAME, NONEXIST_TYPE_ID), headers);

        ResponseEntity<CHSubtypeResponseDTO> responseEntity =
                restTemplate.exchange("/api/ch-subtypes", HttpMethod.POST, httpEntity, CHSubtypeResponseDTO.class);
        CHSubtypeResponseDTO created = responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        assertNull(created.getId());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testDeleteSubtypeValid() throws Exception{

        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<?> response = restTemplate.exchange("/api/ch-subtypes/" + CAN_DELETE_SUBTYPE_ID, HttpMethod.DELETE, httpEntity, String.class);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testDeleteSubtypeInvalidNotFound() throws Exception{

        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<?> response = restTemplate.exchange("/api/ch-subtypes/" + NONEXIST_SUTYPE_ID, HttpMethod.DELETE, httpEntity, String.class);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }

    @Test
    @Transactional
    @Rollback(true)
    public void testDeleteSubtypeInvalidCHRelated() throws Exception{

        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<?> response = restTemplate.exchange("/api/ch-subtypes/" + EXIST_SUBTYPE_ID, HttpMethod.DELETE, httpEntity, String.class);

    }

}
