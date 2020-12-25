package tim2.CulturalHeritage.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import tim2.CulturalHeritage.dto.requestDTO.AuthUserLoginDTO;
import tim2.CulturalHeritage.dto.requestDTO.CommentRequestDTO;
import tim2.CulturalHeritage.dto.requestDTO.NewsRequestDTO;
import tim2.CulturalHeritage.dto.responseDTO.AuthUserLoginResponseDTO;
import tim2.CulturalHeritage.dto.responseDTO.CommentResponseDTO;
import tim2.CulturalHeritage.dto.responseDTO.NewsResponseDTO;
import tim2.CulturalHeritage.service.CommentService;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

    @Before
    public void login() {
        ResponseEntity<AuthUserLoginResponseDTO> responseEntity = restTemplate.postForEntity("/auth/login",
                new AuthUserLoginDTO(USER_EMAIL, USER_PASS), AuthUserLoginResponseDTO.class);

        String accessToken = "Bearer " + responseEntity.getBody().getAccessToken();

        headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
    }

    private HttpEntity<LinkedMultiValueMap<String, Object>> createFormData(CommentRequestDTO comDTO, String path){

        //Dto is param -> body -> form data
        // set JSON content type for dto.
        HttpHeaders jsonHeaders = new HttpHeaders();
        jsonHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CommentRequestDTO> dto = new HttpEntity<>(comDTO, jsonHeaders);

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
        params.add("comment", dto);


        return new HttpEntity<>(params, headers);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void addValidWithFile(){
        CommentRequestDTO comDTO = new CommentRequestDTO(CONTENT, CH_ID);

        HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = createFormData(comDTO, IMAGE_SRC);

        ResponseEntity<CommentResponseDTO> responseEntity =
                restTemplate.postForEntity("/api/comments", requestEntity, CommentResponseDTO.class);

        CommentResponseDTO commentResponseDTO = responseEntity.getBody();
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(CONTENT, commentResponseDTO.getContent());
        assertEquals(LOGGED_IN_USER_ID, commentResponseDTO.getAuthenticatedUserID());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void addInvalidChID(){
        CommentRequestDTO comDTO = new CommentRequestDTO(CONTENT, CH_ID_NOT_FOUND);

        HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = createFormData(comDTO, IMAGE_SRC);

        ResponseEntity<CommentResponseDTO> responseEntity =
                restTemplate.postForEntity("/api/comments", requestEntity, CommentResponseDTO.class);

        CommentResponseDTO commentResponseDTO = responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(commentResponseDTO);
    }

}
