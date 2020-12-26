package tim2.CulturalHeritage.service;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import tim2.CulturalHeritage.model.*;
import tim2.CulturalHeritage.repository.RatingRepository;

import javax.persistence.EntityNotFoundException;

import static tim2.CulturalHeritage.constants.RatingConstants.*;
import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class RatingServiceIntegrationTest {

    @Autowired
    RatingService ratingService;

    @Autowired
    RatingRepository ratingRepository;


    @Autowired
    AuthenticatedUserService authUserService;

    @Autowired
    CulturalHeritageService culturalHeritageService;

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testCreateNewRatingValid(){
        Rating newRating = new Rating();
        AuthenticatedUser user = new AuthenticatedUser();
        user.setId(USER_ID);
        CulturalHeritage ch = new CulturalHeritage();
        ch.setId(CH_ID);

        newRating.setGrade(NEW_GRADE);
        newRating.setAuthenticatedUser(user);
        newRating.setCulturalHeritage(ch);

        Rating created = ratingService.add(newRating);
        assertEquals(USER_ID, created.getAuthenticatedUser().getId());
        assertEquals(CH_ID, created.getCulturalHeritage().getId());
        assertEquals(NEW_GRADE, created.getGrade());
        assertNotNull(created);
    }

    @Test
    public void testCreateNewRatingInvalidUser(){
        Rating newRating = new Rating();
        newRating.setGrade(NEW_GRADE);

        AuthenticatedUser user = new AuthenticatedUser();
        user.setId(NON_EXIST_USER);
        CulturalHeritage ch = new CulturalHeritage();
        ch.setId(CH_ID);

        assertThrows(AssertionError.class , () -> {
            Rating created = ratingService.add(newRating);
            assertNull(created);
        });
    }

    @Test
    public void testCreateNewRatingInvalidCulturalHeritage(){
        Rating newRating = new Rating();
        newRating.setGrade(NEW_GRADE);

        AuthenticatedUser user = new AuthenticatedUser();
        user.setId(USER_ID);
        CulturalHeritage ch = new CulturalHeritage();
        ch.setId(CH_INVALID_ID);

        assertThrows(AssertionError.class , () -> {
            Rating created = ratingService.add(newRating);
            assertNull(created);
        });
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testCreateNewRatingAlreadyRated(){
        Rating newRating = new Rating();
        newRating.setGrade(NEW_GRADE);
        AuthenticatedUser user = new AuthenticatedUser();
        user.setId(USER_ALREADY_GRADED_ID);
        CulturalHeritage ch = new CulturalHeritage();
        ch.setId(CH_ID);

        newRating.setGrade(NEW_GRADE);
        newRating.setAuthenticatedUser(user);
        newRating.setCulturalHeritage(ch);

        Rating created = ratingService.add(newRating);
    }

    @Test
    public void testFindAll(){
        Pageable pageable = PageRequest.of(PAGE_NUM, PAGE_SIZE);
        Page<Rating> found = ratingService.findAll(pageable);
        assertEquals(3, found.getNumberOfElements());
        assertEquals(NUMBER_OF_RATINGS_IN_DB, found.getNumberOfElements());
    }

    @Test
    public void testFindOneFound(){
        Rating found = ratingService.findById(EXIST_RATING_ID);
        assertEquals(found.getId(), EXIST_RATING_ID);
    }

    @Test
    public void testFindOneNotFound(){
        Rating found = ratingService.findById(NONEXIST_RATING_ID);
        assertNull(found);
    }

    @Test(expected = EntityNotFoundException.class)
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testDeleteNotFound() {
        Rating rating = new Rating();
        rating.setId(NONEXIST_RATING_ID);
        ratingService.deleteById(rating.getId());
    }

    @Test(expected = EntityNotFoundException.class)
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testUpdateNotFound() {
        Rating rating = new Rating();
        rating.setId(NONEXIST_RATING_ID);
        ratingService.update(rating);
    }
}