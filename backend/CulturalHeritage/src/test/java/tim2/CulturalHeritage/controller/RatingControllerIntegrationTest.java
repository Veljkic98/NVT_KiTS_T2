package tim2.CulturalHeritage.controller;

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
import tim2.CulturalHeritage.dto.requestDTO.RatingRequestDTO;
import tim2.CulturalHeritage.dto.responseDTO.AuthUserLoginResponseDTO;
import tim2.CulturalHeritage.dto.responseDTO.RatingResponseDTO;
import tim2.CulturalHeritage.model.Rating;
import tim2.CulturalHeritage.restTemplateHelp.RestResponsePage;
import tim2.CulturalHeritage.service.RatingService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static tim2.CulturalHeritage.constants.LoginConstants.*;
import static tim2.CulturalHeritage.constants.RatingConstants.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class RatingControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private HttpHeaders headers;

    @Autowired
    RatingController ratingController;

    @Autowired
    RatingService ratingService;

    private Pageable pageable = PageRequest.of(0, PAGE_SIZE);

    private HttpHeaders withoutTokenHeaders = new HttpHeaders();

    @Before
    public void loginUser() {
        ResponseEntity<AuthUserLoginResponseDTO> responseEntity = restTemplate.postForEntity("/auth/login",
                new AuthUserLoginDTO(USER_EMAIL, USER_PASS), AuthUserLoginResponseDTO.class);

        String accessToken = "Bearer " + responseEntity.getBody().getAccessToken();

        headers = new HttpHeaders();
        headers.add("Authorization", accessToken);

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Content-Type", "application/json");
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testCreateRatingValid(){
        HttpEntity<Object> httpEntity = new HttpEntity<>(new RatingRequestDTO(NEW_GRADE, CH_ID), headers);

        ResponseEntity<RatingResponseDTO> responseEntity =
                restTemplate.exchange("/api/ratings", HttpMethod.POST, httpEntity, RatingResponseDTO.class);
        RatingResponseDTO created = responseEntity.getBody();
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(LOGGED_IN_USER_ID, created.getUserID());
        assertEquals(CH_ID, created.getChID());
        assertEquals(NEW_GRADE, created.getGrade());
        assertEquals(NUMBER_OF_RATINGS_IN_DB + 1, ratingService.findAll(pageable).getNumberOfElements());
    }

    @Test
    public void testCreateRatingInvalidCulturalHeritage(){
        HttpEntity<Object> httpEntity = new HttpEntity<>(new RatingRequestDTO(NEW_GRADE, CH_INVALID_ID), headers);

        ResponseEntity<RatingResponseDTO> responseEntity =
                restTemplate.exchange("/api/ratings", HttpMethod.POST, httpEntity, RatingResponseDTO.class);
        RatingResponseDTO created = responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(created);
        assertEquals(NUMBER_OF_RATINGS_IN_DB, ratingService.findAll(pageable).getNumberOfElements());

    }

    @Test
    public void testCreateRatingInvalidAlreadyRated(){
        HttpEntity<Object> httpEntity = new HttpEntity<>(new RatingRequestDTO(NEW_GRADE, ALREADY_GRADED_CH_ID), headers);

        ResponseEntity<RatingResponseDTO> responseEntity =
                restTemplate.exchange("/api/ratings", HttpMethod.POST, httpEntity, RatingResponseDTO.class);

        RatingResponseDTO created = responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(created);
        assertEquals(NUMBER_OF_RATINGS_IN_DB, ratingService.findAll(pageable).getNumberOfElements());
    }

    @Test
    public void testGetAllRatings(){

        ParameterizedTypeReference<RestResponsePage<RatingResponseDTO>> responseType = new ParameterizedTypeReference<RestResponsePage<RatingResponseDTO>>() {
        };

        ResponseEntity<RestResponsePage<RatingResponseDTO>> responseEntity = restTemplate.exchange(
                "/api/ratings/by-page/?page=" + PAGE_NUM + "&size=" + PAGE_SIZE + "&sort=id,ASC", HttpMethod.GET, null/* httpEntity */,
                responseType);

        List<RatingResponseDTO> responseList = responseEntity.getBody().getContent();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(responseList.size(), NUMBER_OF_RATINGS_IN_DB);
    }

    @Test
    public void testFindById() {

        Rating rating = ratingService.findById(EXIST_RATING_ID);

        ResponseEntity<RatingResponseDTO> responseEntity = restTemplate.getForEntity("/api/ratings/" + EXIST_RATING_ID,
                RatingResponseDTO.class);

        RatingResponseDTO responseDTO = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseDTO);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(rating.getId(), responseDTO.getId());
        assertEquals(rating.getGrade(), responseDTO.getGrade());
        assertEquals(rating.getAuthenticatedUser().getId(), responseDTO.getUserID());
    }

    @Test
    public void testFindByInvalidId() {
        ResponseEntity<RatingResponseDTO> responseEntity = restTemplate.getForEntity("/api/ratings/" + NONEXIST_RATING_ID,
                RatingResponseDTO.class);

        RatingResponseDTO responseDTO = responseEntity.getBody();

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseDTO);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testDeleteValid() {

        HttpEntity<Object> requestEntity = new HttpEntity<Object>(headers);

        ResponseEntity<Void> responseEntity = restTemplate.exchange("/api/ratings/" + RATING_FROM_LOGGED_IN_USER,
                HttpMethod.DELETE, requestEntity, Void.class);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertEquals(NUMBER_OF_RATINGS_IN_DB - 1, ratingService.findAll(pageable).getNumberOfElements());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testDeleteInvalidID() {

        HttpEntity<Object> requestEntity = new HttpEntity<Object>(headers);

        ResponseEntity<Void> responseEntity = restTemplate.exchange("/api/ratings/" + NONEXIST_RATING_ID,
                HttpMethod.DELETE, requestEntity, Void.class);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testDeleteInvalidAccesDenied() {
        //User is trying to delete another's user rating
        HttpEntity<Object> requestEntity = new HttpEntity<Object>(headers);

        ResponseEntity<Void> responseEntity = restTemplate.exchange("/api/ratings/" + EXIST_RATING_ID,
                HttpMethod.DELETE, requestEntity, Void.class);

        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testUpdateValid() {
        RatingRequestDTO ratingReq = new RatingRequestDTO();
        ratingReq.setGrade(NEW_GRADE);
        ratingReq.setCulturalHeritageId(CH_ID);

        HttpEntity<Object> requestEntity = new HttpEntity<Object>(ratingReq, headers);

        ResponseEntity<RatingResponseDTO> responseEntity = restTemplate.exchange("/api/ratings/" + RATING_FROM_LOGGED_IN_USER,
                HttpMethod.PUT, requestEntity, RatingResponseDTO.class);

        RatingResponseDTO responseDTO = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(RATING_FROM_LOGGED_IN_USER, responseDTO.getId());
        assertEquals(NEW_GRADE, responseDTO.getGrade());
        assertEquals(NUMBER_OF_RATINGS_IN_DB, ratingService.findAll(pageable).getNumberOfElements());
    }
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testUpdateInvalidID() {
        RatingRequestDTO ratingReq = new RatingRequestDTO();
        ratingReq.setGrade(NEW_GRADE);
        ratingReq.setCulturalHeritageId(CH_ID);

        HttpEntity<Object> requestEntity = new HttpEntity<Object>(ratingReq, headers);

        ResponseEntity<RatingResponseDTO> responseEntity = restTemplate.exchange("/api/ratings/" + NONEXIST_RATING_ID,
                HttpMethod.PUT, requestEntity, RatingResponseDTO.class);

        RatingResponseDTO responseDTO = responseEntity.getBody();

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseDTO);
    }
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testUpdateInvalidAccessDenied() {
        RatingRequestDTO ratingReq = new RatingRequestDTO();
        ratingReq.setGrade(NEW_GRADE);
        ratingReq.setCulturalHeritageId(CH_ID);

        HttpEntity<Object> requestEntity = new HttpEntity<Object>(ratingReq, headers);
    //User is trying to change rating from another user
        ResponseEntity<RatingResponseDTO> responseEntity = restTemplate.exchange("/api/ratings/" + EXIST_RATING_ID,
                HttpMethod.PUT, requestEntity, RatingResponseDTO.class);

        RatingResponseDTO responseDTO = responseEntity.getBody();

        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
        assertNull(responseDTO);
    }

}
