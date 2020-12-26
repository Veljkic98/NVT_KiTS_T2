package tim2.CulturalHeritage.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import tim2.CulturalHeritage.model.Rating;

import static org.junit.jupiter.api.Assertions.*;
import static tim2.CulturalHeritage.constants.RatingConstants.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class RatingRepoIntegrationTest {
    @Autowired
    RatingRepository ratingRepository;

    @Test
    public void testFindAll() {
        Pageable pageable = PageRequest.of(PAGE_NUM, PAGE_SIZE);
        Page<Rating> page = ratingRepository.findAll(pageable);
        assertEquals(NUMBER_OF_RATINGS_IN_DB, page.getTotalElements());
    }

    @Test
    public void testFindById() {
        Rating entity = ratingRepository.findById(EXIST_RATING_ID).orElse(null);
        assertNotNull(entity);
    }

    @Test
    public void testFindByIdNotFound() {
        Rating entity = ratingRepository.findById(NONEXIST_RATING_ID).orElse(null);
        assertNull(entity);
    }
}
