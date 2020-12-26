package tim2.CulturalHeritage.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import tim2.CulturalHeritage.model.AuthenticatedUser;
import tim2.CulturalHeritage.model.News;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
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





}
