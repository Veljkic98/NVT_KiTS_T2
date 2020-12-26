package tim2.CulturalHeritage.repository;

import org.junit.runner.RunWith;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import tim2.CulturalHeritage.model.CHSubtype;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static tim2.CulturalHeritage.constants.CHSubtypeConstants.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class CHSubtypeRepoIntegrationTest {

    @Autowired
    CHSubtypeRepository chSubtypeRepository;

    @Test
    public void testFindByName() {
        CHSubtype found = chSubtypeRepository.findByName(EXIST_SUBTYPE_NAME);
        assertEquals(EXIST_SUBTYPE_NAME, found.getName());
    }

    @Test
    public void testFindByNameNotFound() {
        CHSubtype found = chSubtypeRepository.findByName(NONEXIST_SUBTYPE_NAME);
        assertNull(found);
    }

    @Test
    public void testFindAll() {
        List<CHSubtype> list = chSubtypeRepository.findAll();
        assertEquals(SUBTYPES_ALL, list.size());
    }

    @Test
    public void testFindById() {
        Optional<CHSubtype> entity = chSubtypeRepository.findById(EXIST_SUBTYPE_ID);
        assertTrue(entity.isPresent());
    }

    @Test
    public void testFindByIdNotFound() {
        Optional<CHSubtype> entity = chSubtypeRepository.findById(NONEXIST_SUTYPE_ID);
        assertTrue(entity.isEmpty());
    }
}
