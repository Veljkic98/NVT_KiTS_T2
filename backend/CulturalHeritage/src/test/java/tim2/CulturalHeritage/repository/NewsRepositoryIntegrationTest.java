package tim2.CulturalHeritage.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tim2.CulturalHeritage.constants.NewsConstants.*;

import java.util.List;

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
public class NewsRepositoryIntegrationTest {

    @Autowired
    private NewsRepository newsRepository;

    @Test
    public void findAllForCH_chIdOk_listOfNews() {

        Pageable pageable = PageRequest.of(0, PAGE_SIZE);
        Page<News> newsPage = newsRepository.findAll(pageable, CH_ID);

        assertEquals(newsPage.getNumberOfElements(), 1);
    }

    @Test
    public void findAllForCH_chIdNull_emptyList() {

        Pageable pageable = PageRequest.of(0, PAGE_SIZE);
        Page<News> newsPage = newsRepository.findAll(pageable, null);
        List<News> newsList = newsPage.getContent();

        assertTrue(newsList.isEmpty());
    }

    @Test
    public void findAllForCH_chIdNotExists_emptyList() {

        Pageable pageable = PageRequest.of(0, PAGE_SIZE);
        Page<News> newsPage = newsRepository.findAll(pageable, CH_ID_NOT_EXISTS);
        List<News> newsList = newsPage.getContent();

        assertTrue(newsList.isEmpty());
    }
}
