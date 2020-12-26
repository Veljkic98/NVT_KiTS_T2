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
import tim2.CulturalHeritage.model.AuthenticatedUser;

import static org.junit.jupiter.api.Assertions.*;
import static tim2.CulturalHeritage.constants.AuthUserConstants.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class AuthUserServiceIntegrationTest {

    @Autowired
    AuthenticatedUserService authenticatedUserService;

    @Test
    public void testFindAll() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<AuthenticatedUser> found = authenticatedUserService.findAll(pageable);
        assertEquals(ALL_USERS, found.getNumberOfElements());
    }

    @Test
    public void testFindOneFound() {
        AuthenticatedUser found = authenticatedUserService.findById(EXIST_USER_ID);
        assertEquals(EXIST_USER_ID, found.getId());
    }

    @Test
    public void testFindOneNotFound() {
        AuthenticatedUser found = authenticatedUserService.findById(NONEXIST_USER_ID);
        assertNull(found);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testSaveOneValid() {
        AuthenticatedUser user = new AuthenticatedUser();
        user.setEmail(NEW_EMAIL);
        user.setPassword(NEW_PASSWORD);
        user.setFirstName(NEW_FIRST_NAME);
        user.setLastName(NEW_LAST_NAME);
        AuthenticatedUser saved = authenticatedUserService.add(user);

        assertEquals(NEW_EMAIL, saved.getEmail());
        assertEquals(NEW_FIRST_NAME, saved.getFirstName());
        assertEquals(NEW_LAST_NAME, saved.getLastName());
    }

    @Test(expected = DataIntegrityViolationException.class)
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testSaveOneInvalidEmail() {
    //    Email must be unique
        AuthenticatedUser user = new AuthenticatedUser();
        user.setEmail(EXIST_EMAIL);
        user.setPassword(NEW_PASSWORD);
        user.setFirstName(NEW_FIRST_NAME);
        user.setLastName(NEW_LAST_NAME);
        AuthenticatedUser saved = authenticatedUserService.add(user);

        assertNull(saved);
    }

    @Test()
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testSetVerifyValid() {
        AuthenticatedUser user = new AuthenticatedUser();
        user.setId(EXIST_USER_NOT_VERIFIED_ID);
        user.setEmail(NEW_EMAIL);
        user.setPassword(NEW_PASSWORD);
        user.setFirstName(NEW_FIRST_NAME);
        user.setLastName(NEW_LAST_NAME);

        authenticatedUserService.setVerified(user);
        assertTrue(user.isApproved());
    }
}
