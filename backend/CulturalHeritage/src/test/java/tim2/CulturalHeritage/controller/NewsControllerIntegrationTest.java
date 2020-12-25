package tim2.CulturalHeritage.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tim2.CulturalHeritage.constants.NewsConstants.*;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import tim2.CulturalHeritage.dto.responseDTO.NewsResponseDTO;
import tim2.CulturalHeritage.model.News;
import tim2.CulturalHeritage.restTemplateHelp.RestResponsePage;
import tim2.CulturalHeritage.service.NewsService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class NewsControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private NewsService newsService;

    @Test
    public void findAllForCH_chIdOk_listAndOk() {

        Pageable pageable = PageRequest.of(0, PAGE_SIZE);
        Page<News> newsPage = newsService.findAll(pageable, CH_ID);
        List<News> newsList = newsPage.getContent();

        int size = newsList.size();

        ParameterizedTypeReference<RestResponsePage<NewsResponseDTO>> responseType = 
        new ParameterizedTypeReference<RestResponsePage<NewsResponseDTO>>() {};

        ResponseEntity<RestResponsePage<NewsResponseDTO>> responseEntity = restTemplate.exchange(
                "/api/news/by-page/1/?page=0&size=5&sort=id,ASC", HttpMethod.GET, null/* httpEntity */, responseType);

        List<NewsResponseDTO> responseList = responseEntity.getBody().getContent();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(responseList.size(), size);
    }

    @Test
    public void findAllForCH_chIdNotExists_listAndOk() {

        ParameterizedTypeReference<RestResponsePage<NewsResponseDTO>> responseType = 
        new ParameterizedTypeReference<RestResponsePage<NewsResponseDTO>>() {};

        ResponseEntity<RestResponsePage<NewsResponseDTO>> responseEntity = restTemplate.exchange(
                "/api/news/by-page/" + CH_ID_NOT_EXISTS + "/?page=0&size=5&sort=id,ASC", HttpMethod.GET, null/* httpEntity */, responseType);


        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

}
