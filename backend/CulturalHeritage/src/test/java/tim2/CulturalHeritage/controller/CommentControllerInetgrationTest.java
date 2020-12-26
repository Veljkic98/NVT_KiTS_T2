package tim2.CulturalHeritage.controller;

import org.junit.Before;
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
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.MethodMode;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;

import tim2.CulturalHeritage.constants.CommentConstants;
import tim2.CulturalHeritage.dto.requestDTO.AuthUserLoginDTO;
import tim2.CulturalHeritage.dto.requestDTO.CommentRequestDTO;
import tim2.CulturalHeritage.dto.responseDTO.AuthUserLoginResponseDTO;
import tim2.CulturalHeritage.dto.responseDTO.CommentResponseDTO;
import tim2.CulturalHeritage.model.Comment;
import tim2.CulturalHeritage.restTemplateHelp.RestResponsePage;
import tim2.CulturalHeritage.service.CommentService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static tim2.CulturalHeritage.constants.LoginConstants.*;
import static tim2.CulturalHeritage.constants.CommentConstants.*;
import static tim2.CulturalHeritage.constants.CulturalHeritageConstants.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class CommentControllerInetgrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CommentService commentService;

    private HttpHeaders headers;

    private Pageable pageable = PageRequest.of(0, CommentConstants.PAGE_SIZE);

    private HttpEntity<LinkedMultiValueMap<String, Object>> createFormData(CommentRequestDTO comDTO, String path) {

        // Dto is param -> body -> form data
        // set JSON content type for dto.
        HttpHeaders jsonHeaders = new HttpHeaders();
        jsonHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CommentRequestDTO> dto = new HttpEntity<>(comDTO, jsonHeaders);

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

        // add dto obj to params (to form data when sending request)
        params.add("comment", dto);

        return new HttpEntity<>(params, headers);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void addValidWithFile() {
        CommentRequestDTO comDTO = new CommentRequestDTO(CONTENT, CH_ID);

        HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = createFormData(comDTO, IMAGE_SRC);

        ResponseEntity<CommentResponseDTO> responseEntity = restTemplate.postForEntity("/api/comments", requestEntity,
                CommentResponseDTO.class);

        CommentResponseDTO commentResponseDTO = responseEntity.getBody();
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(CONTENT, commentResponseDTO.getContent());
        assertEquals(LOGGED_IN_USER_ID, commentResponseDTO.getAuthenticatedUserID());
        assertEquals(NUMBER_OF_COMMENTS_IN_DB + 1, commentService.findAll(pageable).getNumberOfElements());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void addInvalidChID() {
        CommentRequestDTO comDTO = new CommentRequestDTO(CONTENT, CH_ID_NOT_FOUND);

        HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = createFormData(comDTO, IMAGE_SRC);

        ResponseEntity<CommentResponseDTO> responseEntity = restTemplate.postForEntity("/api/comments", requestEntity,
                CommentResponseDTO.class);

        CommentResponseDTO commentResponseDTO = responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(commentResponseDTO);
        assertEquals(NUMBER_OF_COMMENTS_IN_DB, commentService.findAll(pageable).getNumberOfElements());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void addValidWithoutFile() {
        CommentRequestDTO comDTO = new CommentRequestDTO(CONTENT, CH_ID);

        HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = createFormData(comDTO, "");

        ResponseEntity<CommentResponseDTO> responseEntity = restTemplate.postForEntity("/api/comments", requestEntity,
                CommentResponseDTO.class);

        CommentResponseDTO commentResponseDTO = responseEntity.getBody();
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(CONTENT, commentResponseDTO.getContent());
        assertEquals(LOGGED_IN_USER_ID, commentResponseDTO.getAuthenticatedUserID());
        assertEquals(NUMBER_OF_COMMENTS_IN_DB + 1, commentService.findAll(pageable).getNumberOfElements());
    }

    @Test
    public void findAll_ok_listAndOk() {

        int size = commentService.findAll(pageable).getNumberOfElements();

        ParameterizedTypeReference<RestResponsePage<CommentResponseDTO>> responseType = new ParameterizedTypeReference<RestResponsePage<CommentResponseDTO>>() {
        };

        ResponseEntity<RestResponsePage<CommentResponseDTO>> responseEntity = restTemplate.exchange(
                "/api/comments/by-page/?page=0&size=5&sort=id,ASC", HttpMethod.GET, null/* httpEntity */, responseType);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(size, responseEntity.getBody().getNumberOfElements());
    }

    @Test
    public void findById_idOk_listAndOk() {

        Comment comment = commentService.findById(ID_OK);

        ResponseEntity<CommentResponseDTO> responseEntity = restTemplate.getForEntity("/api/comments/" + ID_OK,
                CommentResponseDTO.class);

        CommentResponseDTO responseDTO = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseDTO);
        assertEquals(responseDTO.getId(), comment.getId());
        assertEquals(responseDTO.getContent(), comment.getContent());
    }

    @Test
    public void findById_idNotExists_listAndOk() {

        ResponseEntity<CommentResponseDTO> responseEntity = restTemplate.getForEntity("/api/comments/" + ID_NOT_EXISTS,
                CommentResponseDTO.class);

        CommentResponseDTO responseDTO = responseEntity.getBody();

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseDTO);
    }

    @Test
    @DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
    public void delete_LoggedInIdOk_noContent() {

        int size = commentService.findAll(pageable).getNumberOfElements();

        HttpHeaders authHeaders = login_user();
        HttpEntity<Object> requestEntity = new HttpEntity<Object>(null, authHeaders);

        ResponseEntity<Void> responseEntity = restTemplate.exchange("/api/comments/" + ID_OK, HttpMethod.DELETE,
                requestEntity, Void.class);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertEquals(size - 1, commentService.findAll(pageable).getNumberOfElements());
    }

    @Test
    @DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
    public void delete_LoggedInIdNotOk_noContent() {

        int size = commentService.findAll(pageable).getNumberOfElements();

        HttpHeaders authHeaders = login_user();
        HttpEntity<Object> requestEntity = new HttpEntity<Object>(null, authHeaders);

        ResponseEntity<Void> responseEntity = restTemplate.exchange("/api/comments/" + ID_OK_2, HttpMethod.DELETE,
                requestEntity, Void.class);

        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
        assertEquals(size , commentService.findAll(pageable).getNumberOfElements());
    }

    @Test
    @DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
    public void delete_notLoggedInIdOk_noContent() {

        int size = commentService.findAll(pageable).getNumberOfElements();

        ResponseEntity<Void> responseEntity = restTemplate.exchange("/api/comments/" + ID_OK, HttpMethod.DELETE,
                null, Void.class);

        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
        assertEquals(size , commentService.findAll(pageable).getNumberOfElements());
    }

    public HttpHeaders login_admin() {

        ResponseEntity<AuthUserLoginResponseDTO> responseEntity = restTemplate.postForEntity("/auth/login",
                new AuthUserLoginDTO(ADMIN_EMAIL, ADMIN_PASS), AuthUserLoginResponseDTO.class);

        String accessToken = "Bearer " + responseEntity.getBody().getAccessToken();

        HttpHeaders authHeaders = new HttpHeaders();
        authHeaders.add("Authorization", accessToken);
        return authHeaders;
    }

    public HttpHeaders login_user() {

        ResponseEntity<AuthUserLoginResponseDTO> responseEntity = restTemplate.postForEntity("/auth/login",
                new AuthUserLoginDTO(USER_EMAIL_3, USER_PASS_3), AuthUserLoginResponseDTO.class);

        String accessToken = "Bearer " + responseEntity.getBody().getAccessToken();

        HttpHeaders authHeaders = new HttpHeaders();
        authHeaders.add("Authorization", accessToken);
        return authHeaders;
    }
}
