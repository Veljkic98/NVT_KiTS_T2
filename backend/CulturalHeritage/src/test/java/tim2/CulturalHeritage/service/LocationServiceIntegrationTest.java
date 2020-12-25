package tim2.CulturalHeritage.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static tim2.CulturalHeritage.constants.LocationConstants.*;

import javax.persistence.EntityNotFoundException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import tim2.CulturalHeritage.model.Location;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class LocationServiceIntegrationTest {

    @Autowired
    private LocationService locationService;

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void add_locationOk_created() {

        Location location = new Location();
        location.setLatitude(LATITUDE);
        location.setLongitude(LONGITUDE);

        Location created = locationService.add(location);

        assertNotNull(created);
        assertEquals(created.getLatitude(), LATITUDE);
        assertEquals(created.getLongitude(), LONGITUDE);
    }

    @Test(expected = DataIntegrityViolationException.class)
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void add_latNullLonOk_DataIntegrityViolationException() {

        Location location = new Location();
        location.setLongitude(LONGITUDE);

        locationService.add(location);
    }

    @Test(expected = DataIntegrityViolationException.class)
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void add_latOkLonNull_DataIntegrityViolationException() {

        Location location = new Location();
        location.setLatitude(LATITUDE);

        locationService.add(location);
    }

    @Test(expected = DataIntegrityViolationException.class)
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void add_latNullLonNull_DataIntegrityViolationException() {

        Location location = new Location();

        locationService.add(location);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void findAll_ok_list() {

        Pageable pageable = PageRequest.of(0, PAGE_SIZE);

        Page<Location> locationsPage = locationService.findAll(pageable);

        assertEquals(locationsPage.getNumberOfElements(), LOCATIONS_NUMBER);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void delete_idOk_ok() {

        Pageable pageable = PageRequest.of(0, PAGE_SIZE);
        int size = locationService.findAll(pageable).getNumberOfElements();

        locationService.deleteById(2L);

        assertEquals(size - 1, locationService.findAll(pageable).getNumberOfElements());
    }

    @Test(expected = EmptyResultDataAccessException.class)
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void delete_idNotExists_ok() {

        locationService.deleteById(ID_NOT_EXISTS);
    }

    /**
     * Some other entity using this location.
     */
    @Test(expected = DataIntegrityViolationException.class)
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void delete_idInUsage_ok() {

        locationService.deleteById(ID_IN_USE);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void update_locationOk_updated() {

        Location location = new Location();
        location.setId(LOCATION_ID_FOR_UPDATEING);
        location.setLatitude(LATITUDE);
        location.setLongitude(LONGITUDE);

        Location updated = locationService.update(location);

        assertNotNull(updated);
        assertEquals(location.getId(), updated.getId());
        assertEquals(location.getLatitude(), updated.getLatitude());
        assertEquals(location.getLongitude(), updated.getLongitude());
    }

    /**
     * Here location which we want to update is in use.
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void update_locationOk2_updated() {

        Location location = new Location();
        location.setId(LOCATION_ID_IN_USE_FOR_UPDATEING);
        location.setLatitude(LATITUDE);
        location.setLongitude(LONGITUDE);

        Location updated = locationService.update(location);

        assertNotNull(updated);
        assertEquals(location.getId(), updated.getId());
        assertEquals(location.getLatitude(), updated.getLatitude());
        assertEquals(location.getLongitude(), updated.getLongitude());
    }

    @Test(expected = EntityNotFoundException.class)
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void update_locationIdNotExists_updated() {

        Location location = new Location();
        location.setId(ID_NOT_EXISTS);
        location.setLatitude(LATITUDE);
        location.setLongitude(LONGITUDE);

        locationService.update(location);
    }

}
