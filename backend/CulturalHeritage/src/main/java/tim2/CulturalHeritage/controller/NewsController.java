package tim2.CulturalHeritage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import tim2.CulturalHeritage.dto.requestDTO.NewsRequestDTO;
import tim2.CulturalHeritage.dto.responseDTO.NewsResponseDTO;
import tim2.CulturalHeritage.helper.NewsMapper;
import tim2.CulturalHeritage.model.News;
import tim2.CulturalHeritage.service.NewsService;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    @Autowired
    private NewsService newsService;
    private static NewsMapper newsMapper = new NewsMapper();

    @RequestMapping(value = "/by-page", method = RequestMethod.GET)
    public ResponseEntity<Page<NewsResponseDTO>> findAll(Pageable pageable) {

        Page<News> resultPage = newsService.findAll(pageable);
        List<NewsResponseDTO> newsDTO = newsMapper.toDtoList(resultPage.toList());
        Page<NewsResponseDTO> newsDTOPage = new PageImpl<>(newsDTO, resultPage.getPageable(),
                resultPage.getTotalElements());

        return new ResponseEntity<>(newsDTOPage, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<NewsResponseDTO> findById(@PathVariable Long id) {

        System.out.println(id);
        News news = newsService.findById(id);

        if (null != news) {
            NewsResponseDTO response = newsMapper.toDto(news);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    // @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<NewsResponseDTO> add(@RequestPart("file") MultipartFile file,
            @RequestPart("news") NewsRequestDTO newsRequestDTO) {

        News news = newsMapper.toEntity(newsRequestDTO);
        news = newsService.add(news, file);

        NewsResponseDTO response = newsMapper.toDto(news);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    // @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> update(@RequestPart("file") MultipartFile file,
            @RequestPart("news") NewsRequestDTO newsRequestDTO, @PathVariable Long id) {

        News updatedNews = newsMapper.toEntity(newsRequestDTO);
        updatedNews.setId(id);

        try {
            updatedNews = newsService.update(updatedNews, file);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        NewsResponseDTO response = newsMapper.toDto(updatedNews);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    // @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        try {
            newsService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) { // if ID is null
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (EmptyResultDataAccessException e) { // if there isn't news with specific ID
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
