package tim2.CulturalHeritage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tim2.CulturalHeritage.dto.responseDTO.LocationResponseDTO;
import tim2.CulturalHeritage.dto.responseDTO.NewsResponseDTO;
import tim2.CulturalHeritage.helper.NewsMapper;
import tim2.CulturalHeritage.model.Location;
import tim2.CulturalHeritage.model.News;
import tim2.CulturalHeritage.service.NewsService;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    private NewsMapper newsMapper = new NewsMapper();

    @RequestMapping(value="/by-page", method= RequestMethod.GET)
    public ResponseEntity<Page<NewsResponseDTO>> findAll(Pageable pageable) {
        Page<News> resultPage = newsService.findAll(pageable);
        List<NewsResponseDTO> newsDTO = newsMapper.toDtoList(resultPage.toList());
        Page<NewsResponseDTO> newsDTOPage = new PageImpl<>(newsDTO, resultPage.getPageable(), resultPage.getTotalElements());

        return new ResponseEntity<>(newsDTOPage, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {

        try {
            News news = newsService.findById(id);
            return new ResponseEntity<>(newsMapper.toDto(news), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<News> add(@RequestBody News news) {

        newsService.add(news);

        return new ResponseEntity<>(news, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<News> update(@RequestBody News news) {

        try {
            newsService.update(news);
            return new ResponseEntity<>(news, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        try {
            newsService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
