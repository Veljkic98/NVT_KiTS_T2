package tim2.CulturalHeritage.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static tim2.CulturalHeritage.constants.CulturalHeritageConstants.*;
import static tim2.CulturalHeritage.constants.LoginConstants.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.persistence.EntityNotFoundException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.MethodMode;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import tim2.CulturalHeritage.dto.requestDTO.AuthUserLoginDTO;
import tim2.CulturalHeritage.dto.requestDTO.FilterRequestDTO;
import tim2.CulturalHeritage.dto.responseDTO.AuthUserLoginResponseDTO;
import tim2.CulturalHeritage.model.CulturalHeritage;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class CulturalHeritageServiceIntegrationTest {

    @Autowired
    private CulturalHeritageService culturalHeritageService;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void findAll_ok_list() {

        Pageable pageable = PageRequest.of(0, 5);
        Page<CulturalHeritage> chPage = culturalHeritageService.findAll(pageable);
        assertEquals(NUMBER_OF_CH_IN_DB, chPage.getNumberOfElements());
    }

    @Test
    public void findById_ValidID_ShouldReturnCH() {

        CulturalHeritage ch = culturalHeritageService.findById(CH_ID);
        assertEquals(CH_ID, ch.getId());
    }

    @Test
    public void findById_InvalidID_ShoudReturnNull() {

        CulturalHeritage ch = culturalHeritageService.findById(CH_ID_NOT_FOUND);
        assertNull(ch);
    }

    @Test
    @DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
    public void add_WithFileValidParams_ShouldReturnCH() throws IOException {

        CulturalHeritage ch = new CulturalHeritage(null, NAME, DESCRIPTION, LOCATION, CH_SUBTYPE, null, null, null);
        File image = new File("src/test/resources/cultural-heritage-management.jpg");
        byte[] imageBytes = Files.readAllBytes(image.toPath());
        MockMultipartFile file = new MockMultipartFile("file", "image.jpg",
                org.springframework.http.MediaType.IMAGE_JPEG_VALUE, imageBytes);

        CulturalHeritage created = culturalHeritageService.add(ch, file);
        assertEquals(NAME, created.getName());
    }

    @Test(expected = DataIntegrityViolationException.class)
    @DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
    public void add_WithoutName_ShouldThrowException() {

        CulturalHeritage ch = new CulturalHeritage(null, null, DESCRIPTION, LOCATION, CH_SUBTYPE, null, null, null);
        MockMultipartFile file = null;
        CulturalHeritage created = culturalHeritageService.add(ch, file);
    }

    @Test
    @DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
    public void update_ValidID_ShouldReturnCH() throws IOException {

        CulturalHeritage ch = new CulturalHeritage(CH_ID, NAME, DESCRIPTION, LOCATION, CH_SUBTYPE, null, null, null);
        File image = new File("src/test/resources/cultural-heritage-management.jpg");
        byte[] imageBytes = Files.readAllBytes(image.toPath());
        MockMultipartFile file = new MockMultipartFile("file", "image.jpg",
                org.springframework.http.MediaType.IMAGE_JPEG_VALUE, imageBytes);

        CulturalHeritage updated = culturalHeritageService.update(ch, file);
        assertEquals(NAME, updated.getName());
    }

    @Test(expected = EntityNotFoundException.class)
    @DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
    public void update_InvalidID_ShouldThrowException() throws IOException {

        CulturalHeritage ch = new CulturalHeritage(CH_ID_NOT_FOUND, NAME, DESCRIPTION, LOCATION, CH_SUBTYPE, null, null,
                null);
        File image = new File("src/test/resources/cultural-heritage-management.jpg");
        byte[] imageBytes = Files.readAllBytes(image.toPath());
        MockMultipartFile file = new MockMultipartFile("file", "image.jpg",
                org.springframework.http.MediaType.IMAGE_JPEG_VALUE, imageBytes);

        CulturalHeritage updated = culturalHeritageService.update(ch, file);
    }

    @Test
    @DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
    public void delete_ValidID_ShouldDelete() {

        culturalHeritageService.deleteById(CH_ID_DELETE_SHOULD);
    }

    @Test(expected = EntityNotFoundException.class)
    @DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
    public void delete_InvalidID_ShouldThrowException() {

        culturalHeritageService.deleteById(CH_ID_NOT_FOUND);
    }

    @Test
    public void filterName() {

        Pageable pageable = PageRequest.of(0, PAGE_SIZE);
        FilterRequestDTO filterDTO = new FilterRequestDTO("name", FILTER_NAME);
        Page<CulturalHeritage> chPage = culturalHeritageService.filter(filterDTO, pageable);
        assertEquals(FILTER_NAME_RESULTS, chPage.getNumberOfElements());
    }

    @Test
    public void filterNameInvalid() {

        Pageable pageable = PageRequest.of(0, PAGE_SIZE);
        FilterRequestDTO filterDTO = new FilterRequestDTO("name", FILTER_INVALID);
        Page<CulturalHeritage> chPage = culturalHeritageService.filter(filterDTO, pageable);
        assertEquals(0, chPage.getNumberOfElements());
    }

    @Test
    public void testFilterBySubtypeNameValid() {

        Pageable pageable = PageRequest.of(0, PAGE_SIZE);
        FilterRequestDTO filterDTO = new FilterRequestDTO("chSubtypeName", FILTER_SUBTYPE);
        Page<CulturalHeritage> chPage = culturalHeritageService.filter(filterDTO, pageable);
        assertEquals(FILTER_SUBTYPE_RESULTS, chPage.getNumberOfElements());
    }

    @Test
    public void testFilterBySubtypeNameInvalid() {

        Pageable pageable = PageRequest.of(0, PAGE_SIZE);
        FilterRequestDTO filterDTO = new FilterRequestDTO("chSubtypeName", FILTER_INVALID);
        Page<CulturalHeritage> chPage = culturalHeritageService.filter(filterDTO, pageable);
        assertEquals(0, chPage.getNumberOfElements());
    }

    @Test
    public void testFilterByCityValid() {

        Pageable pageable = PageRequest.of(0, PAGE_SIZE);
        FilterRequestDTO filterDTO = new FilterRequestDTO("locationCity", FILTER_CITY);
        Page<CulturalHeritage> chPage = culturalHeritageService.filter(filterDTO, pageable);
        assertEquals(FILTER_CITY_RESULTS, chPage.getNumberOfElements());
    }

    @Test
    public void testFilterByCityInvalid() {

        Pageable pageable = PageRequest.of(0, PAGE_SIZE);
        FilterRequestDTO filterDTO = new FilterRequestDTO("locationCity", FILTER_INVALID);
        Page<CulturalHeritage> chPage = culturalHeritageService.filter(filterDTO, pageable);
        assertEquals(0, chPage.getNumberOfElements());
    }

    @Test
    public void testFilterByCountryValid() {

        Pageable pageable = PageRequest.of(0, PAGE_SIZE);
        FilterRequestDTO filterDTO = new FilterRequestDTO("locationCountry", FILTER_COUNTRY);
        Page<CulturalHeritage> chPage = culturalHeritageService.filter(filterDTO, pageable);
        assertEquals(FILTER_COUNTRY_RESULTS, chPage.getNumberOfElements());
    }

    @Test
    public void testFilterByCountryInvalid() {

        Pageable pageable = PageRequest.of(0, PAGE_SIZE);
        FilterRequestDTO filterDTO = new FilterRequestDTO("locationCountry", FILTER_INVALID);
        Page<CulturalHeritage> chPage = culturalHeritageService.filter(filterDTO, pageable);
        assertEquals(0, chPage.getNumberOfElements());
    }

    public HttpHeaders userLogin() {

        ResponseEntity<AuthUserLoginResponseDTO> responseEntity = restTemplate.postForEntity("/auth/login",
                new AuthUserLoginDTO(USER_EMAIL, ADMIN_PASS), AuthUserLoginResponseDTO.class);

        String accessToken = "Bearer " + responseEntity.getBody().getAccessToken();

        HttpHeaders authHeaders = new HttpHeaders();
        authHeaders.add("Authorization", accessToken);
        return authHeaders;
    }

}
