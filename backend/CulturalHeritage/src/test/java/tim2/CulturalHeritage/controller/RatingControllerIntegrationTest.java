package tim2.CulturalHeritage.controller;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.Page;
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
import tim2.CulturalHeritage.service.RatingService;


import javax.validation.constraints.Null;

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
}
