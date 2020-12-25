package tim2.CulturalHeritage.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tim2.CulturalHeritage.constants.CulturalHeritageConstants.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import tim2.CulturalHeritage.model.CulturalHeritage;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class CulturalHeritageServiceIntegrationTest {

    @Autowired
    private CulturalHeritageService culturalHeritageService;

    @Test
    public void findAll_ok_list() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<CulturalHeritage> chPage = culturalHeritageService.findAll(pageable);
        assertEquals(NUMBER_OF_CH_IN_DB, chPage.getNumberOfElements());
    }

}
