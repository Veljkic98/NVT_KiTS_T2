package tim2.CulturalHeritage.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
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

        int size = chTypeService.findAll().size();
        CHTypeRequestDTO requestDTO = new CHTypeRequestDTO();
        requestDTO.setName("naziv");

        ResponseEntity<CHTypeResponseDTO> responseEntity = restTemplate.postForEntity("/api/ch-types", requestDTO,
                CHTypeResponseDTO.class);

        CHTypeResponseDTO createdDTO = responseEntity.getBody();

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(createdDTO);
        assertEquals(createdDTO.getName(), "naziv");
        assertTrue(createdDTO.getSubtypes().isEmpty());
        assertEquals(size + 1, chTypeService.findAll().size());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void add_nameNull_badRequest() {

        int size = chTypeService.findAll().size();
        CHTypeRequestDTO requestDTO = new CHTypeRequestDTO();

        ResponseEntity<CHTypeResponseDTO> responseEntity = restTemplate.postForEntity("/api/ch-types", requestDTO,
                CHTypeResponseDTO.class);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(size, chTypeService.findAll().size()); // size should be  unchanged
        assertNull(null);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void add_nameEmpty_badRequest() {
        int size = chTypeService.findAll().size();
        CHTypeRequestDTO requestDTO = new CHTypeRequestDTO();
        requestDTO.setName("");

        ResponseEntity<CHTypeResponseDTO> responseEntity = restTemplate.postForEntity("/api/ch-types", requestDTO,
                CHTypeResponseDTO.class);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(size, chTypeService.findAll().size()); // size should be  unchanged
        assertNull(null);
    }

    // @Test
    // @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    // public void add_dtoOkSubtypeListNotEmpty_true() {
    //     // TODO: Da li kada kreiramo novi tip treba da mozemo odmah da mu dodelimo i
    //     // podtipove ako zelimo?
    // }

}
