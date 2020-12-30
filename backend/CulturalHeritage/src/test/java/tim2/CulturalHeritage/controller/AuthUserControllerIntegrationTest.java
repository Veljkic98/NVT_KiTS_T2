package tim2.CulturalHeritage.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import tim2.CulturalHeritage.dto.requestDTO.AuthUserLoginDTO;
import tim2.CulturalHeritage.dto.requestDTO.AuthUserRequestDTO;
import tim2.CulturalHeritage.dto.responseDTO.AuthUserLoginResponseDTO;
import tim2.CulturalHeritage.dto.responseDTO.AuthUserResponseDTO;
import tim2.CulturalHeritage.service.AuthenticatedUserService;

import static org.junit.jupiter.api.Assertions.*;
import static tim2.CulturalHeritage.constants.AuthUserConstants.*;
import static tim2.CulturalHeritage.constants.LoginConstants.*;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class AuthUserControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AuthenticatedUserService authenticatedUserService;

    private HttpHeaders headers = new HttpHeaders();

    private HttpHeaders headersWithToken = new HttpHeaders();

    private Pageable pageable = PageRequest.of(PAGE_NUM, PAGE_SIZE);

    @Test
    public void testGetOne() {
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<AuthUserResponseDTO> responseEntity =
                restTemplate.exchange("/api/authenticated-users/" + EXIST_USER_ID, HttpMethod.GET, httpEntity,
                        AuthUserResponseDTO.class);

        AuthUserResponseDTO user = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(user);
    }

    @Test
    public void testGetOneInvaliID() {
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<AuthUserResponseDTO> responseEntity =
                restTemplate.exchange("/api/authenticated-users/" + NONEXIST_USER_ID, HttpMethod.GET, httpEntity,
                        AuthUserResponseDTO.class);

        AuthUserResponseDTO user = responseEntity.getBody();

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(user);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testCreateUserValid() {
        AuthUserRequestDTO userRequest = new AuthUserRequestDTO(NEW_FIRST_NAME, NEW_LAST_NAME, NEW_EMAIL, NEW_PASSWORD);

        HttpEntity<Object> httpEntity = new HttpEntity<>(userRequest, headers);

        ResponseEntity<AuthUserResponseDTO> responseEntity =
                restTemplate.exchange("/api/authenticated-users/" , HttpMethod.POST, httpEntity,
                        AuthUserResponseDTO.class);

        AuthUserResponseDTO user = responseEntity.getBody();

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(NEW_EMAIL, user.getEmail());
        assertEquals(NEW_FIRST_NAME, user.getFirstName());
        assertEquals(NEW_LAST_NAME, user.getLastName());
        assertNotNull(user.getId());
        assertTrue(!user.isApproved());

        assertEquals(ALL_USERS + 1, authenticatedUserService.findAll(pageable).getNumberOfElements());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testCreateUserInvalidEmail() {
        AuthUserRequestDTO userRequest = new AuthUserRequestDTO(NEW_FIRST_NAME, NEW_LAST_NAME, EXIST_EMAIL, NEW_PASSWORD);

        HttpEntity<Object> httpEntity = new HttpEntity<>(userRequest, headers);

        ResponseEntity<AuthUserResponseDTO> responseEntity =
                restTemplate.exchange("/api/authenticated-users/" , HttpMethod.POST, httpEntity,
                        AuthUserResponseDTO.class);

        AuthUserResponseDTO user = responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(user.getId());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testVerify() {
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<AuthUserResponseDTO> responseEntity =
                restTemplate.exchange("/api/authenticated-users/verify/" + 2 , HttpMethod.GET, httpEntity,
                        AuthUserResponseDTO.class);

        AuthUserResponseDTO user = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(user.isApproved());
        assertEquals(ALL_USERS, authenticatedUserService.findAll(pageable).getNumberOfElements());
    }
    
    @Test
    public void testGetProfileDetailsAdmin() {
        //admin is logged in
        loginAdmin();
        HttpEntity<Object> httpEntity = new HttpEntity<>(headersWithToken);

        ResponseEntity<AuthUserResponseDTO> responseEntity =
                restTemplate.exchange("/api/authenticated-users/me/", HttpMethod.GET, httpEntity,
                        AuthUserResponseDTO.class);

        AuthUserResponseDTO user = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(user.isApproved());
        assertEquals(user.getEmail(), ADMIN_EMAIL);
        assertEquals(ALL_USERS, authenticatedUserService.findAll(pageable).getNumberOfElements());
    }

    @Test
    public void testGetProfileDetailsAuthUser() {
        //user is logged in
        loginUser();
        HttpEntity<Object> httpEntity = new HttpEntity<>(headersWithToken);

        ResponseEntity<AuthUserResponseDTO> responseEntity =
                restTemplate.exchange("/api/authenticated-users/me/", HttpMethod.GET, httpEntity,
                        AuthUserResponseDTO.class);

        AuthUserResponseDTO user = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(user.isApproved());
        assertEquals(user.getEmail(), USER_EMAIL);
        assertEquals(user.getId(), LOGGED_IN_USER_ID);
        assertEquals(ALL_USERS, authenticatedUserService.findAll(pageable).getNumberOfElements());
    }

    public void loginAdmin() {
        ResponseEntity<AuthUserLoginResponseDTO> responseEntity = restTemplate.postForEntity("/auth/login",
                new AuthUserLoginDTO(ADMIN_EMAIL, ADMIN_PASS), AuthUserLoginResponseDTO.class);

        String accessToken = "Bearer " + responseEntity.getBody().getAccessToken();

        headersWithToken.add("Authorization", accessToken);

        headersWithToken.setContentType(MediaType.APPLICATION_JSON);
        headersWithToken.add("Content-Type", "application/json");
    }

    public void loginUser() {
        ResponseEntity<AuthUserLoginResponseDTO> responseEntity = restTemplate.postForEntity("/auth/login",
                new AuthUserLoginDTO(USER_EMAIL, USER_PASS), AuthUserLoginResponseDTO.class);

        String accessToken = "Bearer " + responseEntity.getBody().getAccessToken();

        headers = new HttpHeaders();
        headersWithToken.add("Authorization", accessToken);

        headersWithToken.setContentType(MediaType.APPLICATION_JSON);
        headersWithToken.add("Content-Type", "application/json");
    }
}
