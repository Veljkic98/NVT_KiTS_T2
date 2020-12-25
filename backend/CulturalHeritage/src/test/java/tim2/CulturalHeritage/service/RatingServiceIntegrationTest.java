package tim2.CulturalHeritage.service;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import tim2.CulturalHeritage.model.AuthenticatedUser;
import tim2.CulturalHeritage.model.CulturalHeritage;
import tim2.CulturalHeritage.model.Rating;
import tim2.CulturalHeritage.repository.RatingRepository;


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
}