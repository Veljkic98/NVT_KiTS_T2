package tim2.CulturalHeritage.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static tim2.CulturalHeritage.constants.NewsConstants.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import tim2.CulturalHeritage.dto.requestDTO.CHTypeRequestDTO;
import tim2.CulturalHeritage.dto.responseDTO.CHTypeResponseDTO;
import tim2.CulturalHeritage.service.CHTypeService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class CHTypeControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CHTypeService chTypeService;

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void add_dtoOk_true() {

        Pageable pageable = PageRequest.of(0, PAGE_SIZE);
        int size = chTypeService.findAll(pageable).getNumberOfElements();
        CHTypeRequestDTO requestDTO = new CHTypeRequestDTO();
        requestDTO.setName("naziv");

        ResponseEntity<CHTypeResponseDTO> responseEntity = restTemplate.postForEntity("/api/ch-types", requestDTO,
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

        ResponseEntity<CHTypeResponseDTO> responseEntity = restTemplate.postForEntity("/api/ch-types", requestDTO,
                CHTypeResponseDTO.class);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void add_nameEmpty_badRequest() {
        CHTypeRequestDTO requestDTO = new CHTypeRequestDTO();
        requestDTO.setName("");

        ResponseEntity<CHTypeResponseDTO> responseEntity = restTemplate.postForEntity("/api/ch-types", requestDTO,
                CHTypeResponseDTO.class);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void add_nameExists_badRequest() {
        CHTypeRequestDTO requestDTO = new CHTypeRequestDTO();
        requestDTO.setName("masd");

        ResponseEntity<CHTypeResponseDTO> responseEntity = restTemplate.postForEntity("/api/ch-types", requestDTO,
                CHTypeResponseDTO.class);

        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
    }

}
