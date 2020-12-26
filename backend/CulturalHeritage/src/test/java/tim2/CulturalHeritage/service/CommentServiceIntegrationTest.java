package tim2.CulturalHeritage.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import tim2.CulturalHeritage.model.AuthenticatedUser;
import tim2.CulturalHeritage.model.Comment;
import tim2.CulturalHeritage.model.CulturalHeritage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tim2.CulturalHeritage.constants.NewsConstants.CONTENT;

import static tim2.CulturalHeritage.constants.CommentConstants.*;
import static tim2.CulturalHeritage.constants.CulturalHeritageConstants.CH_ID;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class CommentServiceIntegrationTest {

    @Autowired
    CommentService commentService;

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void addValidWithoutFile() {
        Comment com = new Comment();
        AuthenticatedUser user = new AuthenticatedUser();
        user.setId(USER_ID);
        CulturalHeritage ch = new CulturalHeritage();
        ch.setId(CH_ID);
        com.setAuthenticatedUser(user);
        com.setContent(CONTENT);
        com.setCulturalHeritage(ch);

        MockMultipartFile file = null;
        Comment created = commentService.add(com, file);
        assertEquals(CONTENT, created.getContent());
        assertEquals(USER_ID, created.getAuthenticatedUser().getId());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void addValidWithFile() throws IOException {
        Comment com = new Comment();
        AuthenticatedUser user = new AuthenticatedUser();
        user.setId(USER_ID);
        CulturalHeritage ch = new CulturalHeritage();
        ch.setId(CH_ID);
        com.setAuthenticatedUser(user);
        com.setContent(CONTENT);
        com.setCulturalHeritage(ch);

        File image = new File(IMAGE_SRC);
        byte[] imageBytes= Files.readAllBytes(image.toPath());
        MockMultipartFile file = new MockMultipartFile("file", "image.jpg", MediaType.IMAGE_JPEG_VALUE, imageBytes);

        Comment created = commentService.add(com, file);
        assertEquals(CONTENT, created.getContent());
        assertEquals(USER_ID, created.getAuthenticatedUser().getId());
    }

    @Test(expected = DataIntegrityViolationException.class)
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void addMissingContent(){
        Comment com = new Comment();
        AuthenticatedUser user = new AuthenticatedUser();
        user.setId(USER_ID);
        CulturalHeritage ch = new CulturalHeritage();
        ch.setId(CH_ID);
        com.setAuthenticatedUser(user);
        com.setContent(NULL_CONTENT);
        com.setCulturalHeritage(ch);
        MockMultipartFile file = null;

        Comment created = commentService.add(com, file);
    }

    @Test(expected = NullPointerException.class)
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void addMissingCh(){
        Comment com = new Comment();
        AuthenticatedUser user = new AuthenticatedUser();
        user.setId(USER_ID);

        com.setAuthenticatedUser(user);
        com.setContent(CONTENT);
        MockMultipartFile file = null;

        Comment created = commentService.add(com, file);
    }
}
