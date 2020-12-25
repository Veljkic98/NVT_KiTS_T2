package tim2.CulturalHeritage.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static tim2.CulturalHeritage.constants.LocationConstants.*;
import static tim2.CulturalHeritage.constants.LoginConstants.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.MethodMode;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import tim2.CulturalHeritage.dto.requestDTO.AuthUserLoginDTO;
import tim2.CulturalHeritage.dto.requestDTO.LocationRequestDTO;
import tim2.CulturalHeritage.dto.responseDTO.AuthUserLoginResponseDTO;
import tim2.CulturalHeritage.dto.responseDTO.LocationResponseDTO;
import tim2.CulturalHeritage.model.Location;
import tim2.CulturalHeritage.restTemplateHelp.RestResponsePage;
import tim2.CulturalHeritage.service.LocationService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class LocationControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private LocationService locationService;

    private Pageable pageable = PageRequest.of(0, PAGE_SIZE);

    @Test
    public void findById_ValidId_ShouldReturnNews() {

        Location location = locationService.findById(ID_IN_USE);

        ResponseEntity<LocationResponseDTO> responseEntity = restTemplate.getForEntity("/api/locations/" + ID_IN_USE,
                LocationResponseDTO.class);

        LocationResponseDTO responseDTO = responseEntity.getBody();

        assertNotNull(responseDTO);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(location.getId(), responseDTO.getId());
        assertEquals(location.getLatitude(), responseDTO.getLatitude());
        assertEquals(location.getLongitude(), responseDTO.getLongitude());
    }

    @Test
    public void findById_idNotExists_ShouldReturnNews() {

        ResponseEntity<LocationResponseDTO> responseEntity = restTemplate
                .getForEntity("/api/locations/" + ID_NOT_EXISTS, LocationResponseDTO.class);

        LocationResponseDTO responseDTO = responseEntity.getBody();

        assertNull(responseDTO);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void findAll_ok_listAndOk() {

        Page<Location> locationPage = locationService.findAll(pageable);
        List<Location> locationList = locationPage.getContent();
        int size = locationList.size();

        ParameterizedTypeReference<RestResponsePage<LocationResponseDTO>> responseType = new ParameterizedTypeReference<RestResponsePage<LocationResponseDTO>>() {
        };

        ResponseEntity<RestResponsePage<LocationResponseDTO>> responseEntity = restTemplate.exchange(
                "/api/locations/by-page/?page=0&size=5&sort=id,ASC", HttpMethod.GET, null/* httpEntity */,
                responseType);

        List<LocationResponseDTO> responseList = responseEntity.getBody().getContent();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(responseList.size(), size);
    }

    @Test
    @DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
    public void delete_LoggedInIdOk_noContent() {

        int size = locationService.findAll(pageable).getNumberOfElements();

        HttpHeaders authHeaders = login();
        HttpEntity<Object> requestEntity = new HttpEntity<Object>(null, authHeaders);

        ResponseEntity<Void> responseEntity = restTemplate.exchange("/api/locations/" + ID_NOT_IN_USE,
                HttpMethod.DELETE, requestEntity, Void.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(size - 1, locationService.findAll(pageable).getNumberOfElements());
    }

    @Test
    @DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
    public void delete_LoggedInIdNotExists_noContent() {

        int size = locationService.findAll(pageable).getNumberOfElements();

        HttpHeaders authHeaders = login();
        HttpEntity<Object> requestEntity = new HttpEntity<Object>(null, authHeaders);

        ResponseEntity<Void> responseEntity = restTemplate.exchange("/api/locations/" + ID_NOT_EXISTS,
                HttpMethod.DELETE, requestEntity, Void.class);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(size, locationService.findAll(pageable).getNumberOfElements());
    }

    @Test
    @DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
    public void delete_LoggedInIdInUse_noContent() {

        int size = locationService.findAll(pageable).getNumberOfElements();

        HttpHeaders authHeaders = login();
        HttpEntity<Object> requestEntity = new HttpEntity<Object>(null, authHeaders);

        ResponseEntity<Void> responseEntity = restTemplate.exchange("/api/locations/" + ID_IN_USE, HttpMethod.DELETE,
                requestEntity, Void.class);

        assertEquals(HttpStatus.NOT_ACCEPTABLE, responseEntity.getStatusCode());
        assertEquals(size, locationService.findAll(pageable).getNumberOfElements());
    }

    @Test
    @DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
    public void add_ok_created() {

        LocationRequestDTO locationRequestDTO = new LocationRequestDTO();
        locationRequestDTO.setLatitude(LATITUDE);
        locationRequestDTO.setLongitude(LONGITUDE);
        locationRequestDTO.setCountry(COUNTRY);
        locationRequestDTO.setCity(CITY);
        locationRequestDTO.setStreet(STREET);

        HttpHeaders headers = login();
        HttpEntity<Object> httpEntity = new HttpEntity<>(locationRequestDTO, headers);

        ResponseEntity<LocationResponseDTO> responseEntity = restTemplate.postForEntity("/api/locations", httpEntity,
                LocationResponseDTO.class);

        LocationResponseDTO responseDTO = responseEntity.getBody();

        assertNotNull(responseDTO);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(locationRequestDTO.getLatitude(), responseDTO.getLatitude());
        assertEquals(locationRequestDTO.getLongitude(), responseDTO.getLongitude());
    }

    @Test
    @DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
    public void add_latNullOtherOk_badRequest() {

        LocationRequestDTO locationRequestDTO = new LocationRequestDTO();
        locationRequestDTO.setLongitude(LONGITUDE);
        locationRequestDTO.setCountry(COUNTRY);
        locationRequestDTO.setCity(CITY);
        locationRequestDTO.setStreet(STREET);

        HttpHeaders headers = login();
        HttpEntity<Object> httpEntity = new HttpEntity<>(locationRequestDTO, headers);

        ResponseEntity<LocationResponseDTO> responseEntity = restTemplate.postForEntity("/api/locations", httpEntity,
                LocationResponseDTO.class);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    @DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
    public void add_lonNullOtherOk_badRequest() {

        LocationRequestDTO locationRequestDTO = new LocationRequestDTO();
        locationRequestDTO.setLatitude(LATITUDE);
        locationRequestDTO.setCountry(COUNTRY);
        locationRequestDTO.setCity(CITY);
        locationRequestDTO.setStreet(STREET);

        HttpHeaders headers = login();
        HttpEntity<Object> httpEntity = new HttpEntity<>(locationRequestDTO, headers);

        ResponseEntity<LocationResponseDTO> responseEntity = restTemplate.postForEntity("/api/locations", httpEntity,
                LocationResponseDTO.class);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    @DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
    public void add_countryNullOtherOk_badRequest() {

        LocationRequestDTO locationRequestDTO = new LocationRequestDTO();
        locationRequestDTO.setLatitude(LATITUDE);
        locationRequestDTO.setLongitude(LONGITUDE);
        locationRequestDTO.setCity(CITY);
        locationRequestDTO.setStreet(STREET);

        HttpHeaders headers = login();
        HttpEntity<Object> httpEntity = new HttpEntity<>(locationRequestDTO, headers);

        ResponseEntity<LocationResponseDTO> responseEntity = restTemplate.postForEntity("/api/locations", httpEntity,
                LocationResponseDTO.class);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    @DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
    public void add_cityNullOtherOk_badRequest() {

        LocationRequestDTO locationRequestDTO = new LocationRequestDTO();
        locationRequestDTO.setLatitude(LATITUDE);
        locationRequestDTO.setLongitude(LONGITUDE);
        locationRequestDTO.setCountry(COUNTRY);
        locationRequestDTO.setStreet(STREET);

        HttpHeaders headers = login();
        HttpEntity<Object> httpEntity = new HttpEntity<>(locationRequestDTO, headers);

        ResponseEntity<LocationResponseDTO> responseEntity = restTemplate.postForEntity("/api/locations", httpEntity,
                LocationResponseDTO.class);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    @DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
    public void add_streetNullOtherOk_badRequest() {

        LocationRequestDTO locationRequestDTO = new LocationRequestDTO();
        locationRequestDTO.setLatitude(LATITUDE);
        locationRequestDTO.setLongitude(LONGITUDE);
        locationRequestDTO.setCountry(COUNTRY);
        locationRequestDTO.setCity(CITY);

        HttpHeaders headers = login();
        HttpEntity<Object> httpEntity = new HttpEntity<>(locationRequestDTO, headers);

        ResponseEntity<LocationResponseDTO> responseEntity = restTemplate.postForEntity("/api/locations", httpEntity,
                LocationResponseDTO.class);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void update_idNotInUse_ok() {

        LocationRequestDTO locationRequestDTO = new LocationRequestDTO();
        locationRequestDTO.setLatitude(LATITUDE);
        locationRequestDTO.setLongitude(LONGITUDE);
        locationRequestDTO.setCountry(COUNTRY);
        locationRequestDTO.setCity(CITY);
        locationRequestDTO.setStreet(STREET);

        HttpHeaders headers = login();
        HttpEntity<Object> httpEntity = new HttpEntity<>(locationRequestDTO, headers);

        ResponseEntity<LocationResponseDTO> responseEntity = restTemplate.exchange("/api/locations/" + ID_NOT_IN_USE,
                HttpMethod.PUT, httpEntity, LocationResponseDTO.class);

        LocationResponseDTO responseDTO = responseEntity.getBody();

        assertNotNull(responseDTO);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(locationRequestDTO.getLatitude(), LATITUDE);
        assertEquals(locationRequestDTO.getLongitude(), LONGITUDE);
        assertEquals(locationRequestDTO.getCountry(), COUNTRY);
        assertEquals(locationRequestDTO.getCity(), CITY);
        assertEquals(locationRequestDTO.getStreet(), STREET);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void update_idInUse_ok() {

        LocationRequestDTO locationRequestDTO = new LocationRequestDTO();
        locationRequestDTO.setLatitude(LATITUDE);
        locationRequestDTO.setLongitude(LONGITUDE);
        locationRequestDTO.setCountry(COUNTRY);
        locationRequestDTO.setCity(CITY);
        locationRequestDTO.setStreet(STREET);

        HttpHeaders headers = login();
        HttpEntity<Object> httpEntity = new HttpEntity<>(locationRequestDTO, headers);

        ResponseEntity<LocationResponseDTO> responseEntity = restTemplate.exchange("/api/locations/" + ID_IN_USE,
                HttpMethod.PUT, httpEntity, LocationResponseDTO.class);

        LocationResponseDTO responseDTO = responseEntity.getBody();

        assertNotNull(responseDTO);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(locationRequestDTO.getLatitude(), LATITUDE);
        assertEquals(locationRequestDTO.getLongitude(), LONGITUDE);
        assertEquals(locationRequestDTO.getCountry(), COUNTRY);
        assertEquals(locationRequestDTO.getCity(), CITY);
        assertEquals(locationRequestDTO.getStreet(), STREET);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void update_idNotExists_ok() {

        LocationRequestDTO locationRequestDTO = new LocationRequestDTO();
        locationRequestDTO.setLatitude(LATITUDE);
        locationRequestDTO.setLongitude(LONGITUDE);
        locationRequestDTO.setCountry(COUNTRY);
        locationRequestDTO.setCity(CITY);
        locationRequestDTO.setStreet(STREET);

        HttpHeaders headers = login();
        HttpEntity<Object> httpEntity = new HttpEntity<>(locationRequestDTO, headers);

        ResponseEntity<LocationResponseDTO> responseEntity = restTemplate.exchange("/api/locations/" + ID_NOT_EXISTS,
                HttpMethod.PUT, httpEntity, LocationResponseDTO.class);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    public HttpHeaders login() {

        ResponseEntity<AuthUserLoginResponseDTO> responseEntity = restTemplate.postForEntity("/auth/login",
                new AuthUserLoginDTO(ADMIN_EMAIL, ADMIN_PASS), AuthUserLoginResponseDTO.class);

        String accessToken = "Bearer " + responseEntity.getBody().getAccessToken();

        HttpHeaders authHeaders = new HttpHeaders();
        authHeaders.add("Authorization", accessToken);
        return authHeaders;
    }
}
