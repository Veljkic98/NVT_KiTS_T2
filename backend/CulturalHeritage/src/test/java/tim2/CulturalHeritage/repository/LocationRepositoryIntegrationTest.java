package tim2.CulturalHeritage.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tim2.CulturalHeritage.constants.LocationConstants.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import tim2.CulturalHeritage.model.Location;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class LocationRepositoryIntegrationTest {

    @Autowired
    private LocationRepository locationRepository;

    @Test
    public void findAll_firstPage_list() {

        Pageable pageable = PageRequest.of(0, PAGE_SIZE);

        Page<Location> locationsPage = locationRepository.findAll(pageable);

        assertEquals(locationsPage.getNumberOfElements(), LOCATIONS_NUMBER);
    }

    @Test
    public void findAll_secondPage_emptyList() {

        Pageable pageable = PageRequest.of(1, PAGE_SIZE);

        Page<Location> locationsPage = locationRepository.findAll(pageable);

        assertEquals(locationsPage.getNumberOfElements(), 0);
    }

}
