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
import tim2.CulturalHeritage.model.AuthenticatedUser;

import static org.junit.jupiter.api.Assertions.*;
import static tim2.CulturalHeritage.constants.AuthUserConstants.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class AuthUserRepoIntegrationTest {
    @Autowired
    AuthenticatedUserRepository authenticatedUserRepository;

    @Test
    public void testFindByEmail(){
        AuthenticatedUser found = authenticatedUserRepository.findByEmail(EXIST_EMAIL);
        assertNotNull(found);
    }

    @Test
    public void testFindByEmailNotFound(){
        AuthenticatedUser found = authenticatedUserRepository.findByEmail(NONEXIST_EMAIL);
        assertNull(found);
    }
    @Test
    public void testFindAll(){
        Pageable pageable = PageRequest.of(PAGE_NUM, PAGE_SIZE);
        Page<AuthenticatedUser> found = authenticatedUserRepository.findAll(pageable);
        assertEquals(ALL_USERS, found.getNumberOfElements());
    }

    @Test
    public void testFindById(){
        AuthenticatedUser entity  = authenticatedUserRepository.findById(EXIST_USER_ID).orElse(null);
        assertNotNull(entity);
    }

    @Test
    public void testFindByIdNotFound(){
        AuthenticatedUser entity  = authenticatedUserRepository.findById(NONEXIST_USER_ID).orElse(null);
        assertNull(entity);
    }
}
