package tim2.CulturalHeritage.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tim2.CulturalHeritage.constants.NewsConstants.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import tim2.CulturalHeritage.model.News;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class NewsServiceIntegrationTest {

    @Autowired
    private NewsService newsService;

    @Test
    public void findAllForCH_chIdOk_list() {

        Pageable pageable = PageRequest.of(0, PAGE_SIZE);

        Page<News> newsPage = newsService.findAll(pageable, CH_ID);
        
        List<News> newsList = newsPage.getContent();
        assertEquals(newsList.size(), PAGE_SIZE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void findAllForCH_chIdNull_list() {

        Pageable pageable = PageRequest.of(0, PAGE_SIZE);

        newsService.findAll(pageable, null);
    }

    @Test(expected = EntityNotFoundException.class)
    public void findAllForCH_chIdNotExists_list() {

        Pageable pageable = PageRequest.of(0, PAGE_SIZE);

        newsService.findAll(pageable, CH_ID_NOT_EXISTS);
    }

}
