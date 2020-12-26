package tim2.CulturalHeritage.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tim2.CulturalHeritage.constants.CHTypeConstants.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import tim2.CulturalHeritage.model.CHType;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class CHTypeRepositoryIntegratioTest {

    @Autowired
    private CHTypeRepository chTypeRepository;

    @Test
    public void findAll_firstPage_list() {

        Pageable pageable = PageRequest.of(0, PAGE_SIZE);

        Page<CHType> typesPage = chTypeRepository.findAll(pageable);

        assertEquals(typesPage.getNumberOfElements(), NUMBER_OF_CH_TYPES);
    }

    @Test
    public void findAll_secondPage_emptyList() {

        Pageable pageable = PageRequest.of(1, PAGE_SIZE);

        Page<CHType> typesPage = chTypeRepository.findAll(pageable);

        assertEquals(typesPage.getNumberOfElements(), 0);
    }
    
}
