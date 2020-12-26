package tim2.CulturalHeritage.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static tim2.CulturalHeritage.constants.CHSubtypeConstants.*;

import static org.junit.jupiter.api.Assertions.*;
import static tim2.CulturalHeritage.constants.CHSubtypeConstants.EXIST_TYPE_ID;
import static tim2.CulturalHeritage.constants.CHSubtypeConstants.NEW_VALID_SUBTYPE_NAME;
import static tim2.CulturalHeritage.constants.LoginConstants.ADMIN_EMAIL;
import static tim2.CulturalHeritage.constants.LoginConstants.ADMIN_PASS;
import static tim2.CulturalHeritage.constants.CHTypeConstants.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import tim2.CulturalHeritage.dto.requestDTO.AuthUserLoginDTO;
import tim2.CulturalHeritage.dto.requestDTO.CHTypeRequestDTO;
import tim2.CulturalHeritage.dto.responseDTO.AuthUserLoginResponseDTO;
import tim2.CulturalHeritage.dto.responseDTO.CHSubtypeResponseDTO;
import tim2.CulturalHeritage.dto.responseDTO.CHTypeResponseDTO;
import tim2.CulturalHeritage.dto.responseDTO.CulturalHeritageResponseDTO;
import tim2.CulturalHeritage.restTemplateHelp.RestResponsePage;
import tim2.CulturalHeritage.service.CHTypeService;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class CHTypeControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CHTypeService chTypeService;

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
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void add_dtoOk_true() {

        Pageable pageable = PageRequest.of(0, PAGE_SIZE);
        int size = chTypeService.findAll(pageable).getNumberOfElements();

        CHTypeRequestDTO requestDTO = new CHTypeRequestDTO();
        requestDTO.setName("naziv");
        HttpEntity<Object> httpEntity = new HttpEntity<>(requestDTO, headers);


        ResponseEntity<CHTypeResponseDTO> responseEntity = restTemplate.postForEntity("/api/ch-types", httpEntity,
                CHTypeResponseDTO.class);

        CHTypeResponseDTO createdDTO = responseEntity.getBody();

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(createdDTO);
        assertEquals(createdDTO.getName(), "naziv");
        assertTrue(createdDTO.getSubtypes().isEmpty());
        assertEquals(size + 1, chTypeService.findAll(pageable).getNumberOfElements());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void add_nameNull_badRequest() {

        CHTypeRequestDTO requestDTO = new CHTypeRequestDTO();
        HttpEntity<Object> httpEntity = new HttpEntity<>(requestDTO, headers);

        ResponseEntity<CHTypeResponseDTO> responseEntity = restTemplate.postForEntity("/api/ch-types", httpEntity,
                CHTypeResponseDTO.class);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void add_nameEmpty_badRequest() {
        CHTypeRequestDTO requestDTO = new CHTypeRequestDTO();
        requestDTO.setName("");
        HttpEntity<Object> httpEntity = new HttpEntity<>(requestDTO, headers);

        ResponseEntity<CHTypeResponseDTO> responseEntity = restTemplate.postForEntity("/api/ch-types", httpEntity,
                CHTypeResponseDTO.class);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void add_nameExists_badRequest() {
        CHTypeRequestDTO requestDTO = new CHTypeRequestDTO();
        requestDTO.setName("masd");
        HttpEntity<Object> httpEntity = new HttpEntity<>(requestDTO, headers);

        ResponseEntity<CHTypeResponseDTO> responseEntity = restTemplate.postForEntity("/api/ch-types", httpEntity,
                CHTypeResponseDTO.class);

        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testDeleteTypeValid(){

        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity =
                restTemplate.exchange("/api/ch-types/" + TYPE_ID_WITHOUT_SUBTYPES, HttpMethod.DELETE, httpEntity, String.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testDeleteTypeInvalid(){

        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity =
                restTemplate.exchange("/api/ch-types/" + TYPE_NONEXIST_ID, HttpMethod.DELETE, httpEntity, String.class);
        ;
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testDeleteTypeInvalidReferencing(){

        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity =
                restTemplate.exchange("/api/ch-types/" + TYPE_ID_WITH_SUBTYPES , HttpMethod.DELETE, httpEntity, String.class);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }


    @Test
    public void testGetAllCHTypesPageable() throws JsonProcessingException {
        ParameterizedTypeReference<RestResponsePage<CHTypeResponseDTO>> responseType = new ParameterizedTypeReference<RestResponsePage<CHTypeResponseDTO>>() {
        };

        ResponseEntity<RestResponsePage<CHTypeResponseDTO>> responseEntity = restTemplate
                .exchange("/api/ch-types/by-page/?page=0&size=" + TYPES_ALL, HttpMethod.GET, null/* httpEntity */, responseType);

        List<CHTypeResponseDTO> responseList = responseEntity.getBody().getContent();

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(TYPES_ALL, responseList.size());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testUpdateTypeValid(){
        HttpEntity<Object> httpEntity = new HttpEntity<>(new CHTypeRequestDTO(NAME), headers);
        ResponseEntity<CHTypeResponseDTO> responseEntity =
                restTemplate.exchange("/api/ch-types/" + TYPE_ID_WITH_SUBTYPES, HttpMethod.PUT, httpEntity, CHTypeResponseDTO.class);
        CHTypeResponseDTO updatedType = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(NAME, updatedType.getName());
        assertEquals(TYPE_ID_WITH_SUBTYPES, updatedType.getId());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testUpdateTypeValidIDInvalidName(){
        HttpEntity<Object> httpEntity = new HttpEntity<>(new CHTypeRequestDTO(NAME_EXISTS), headers);
        ResponseEntity<CHTypeResponseDTO> responseEntity =
                restTemplate.exchange("/api/ch-types/" + TYPE_ID_WITHOUT_SUBTYPES, HttpMethod.PUT, httpEntity, CHTypeResponseDTO.class);
        CHTypeResponseDTO updatedType = responseEntity.getBody();

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(updatedType);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testUpdateTypeInvalidIDValidName(){
        HttpEntity<Object> httpEntity = new HttpEntity<>(new CHTypeRequestDTO(NAME), headers);
        ResponseEntity<CHTypeResponseDTO> responseEntity =
                restTemplate.exchange("/api/ch-types/" + TYPE_NONEXIST_ID, HttpMethod.PUT, httpEntity, CHTypeResponseDTO.class);
        CHTypeResponseDTO updatedType = responseEntity.getBody();

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(updatedType);
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testUpdateTypeInvalidIDInvalidName(){
        HttpEntity<Object> httpEntity = new HttpEntity<>(new CHTypeRequestDTO(NAME_EXISTS), headers);
        ResponseEntity<CHTypeResponseDTO> responseEntity =
                restTemplate.exchange("/api/ch-types/" + TYPE_NONEXIST_ID, HttpMethod.PUT, httpEntity, CHTypeResponseDTO.class);
        CHTypeResponseDTO updatedType = responseEntity.getBody();

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(updatedType);
    }
}
