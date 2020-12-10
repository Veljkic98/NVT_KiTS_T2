package tim2.CulturalHeritage.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import tim2.CulturalHeritage.dto.requestDTO.NewsRequestDTO;
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

    @RequestMapping(value = "/by-page", method = RequestMethod.GET)
    public ResponseEntity<Page<NewsResponseDTO>> findAll(Pageable pageable) {
        Page<News> resultPage = newsService.findAll(pageable);
        List<NewsResponseDTO> newsDTO = newsMapper.toDtoList(resultPage.toList());
        Page<NewsResponseDTO> newsDTOPage = new PageImpl<>(newsDTO, resultPage.getPageable(),
                resultPage.getTotalElements());

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
    // @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> add(@RequestPart("file") MultipartFile file, @RequestPart("news") NewsRequestDTO news) {
        // System.out.println("--------------------------");
        // System.out.println(news);
        News entity = newsMapper.toEntity(news);
        entity = newsService.add(entity, file);
        
        String fileDownloadUri = ServletUriComponentsBuilder
        .fromCurrentContextPath()
        .path("api/news/")
        .path(entity.getId() + "")
        .toUriString();

        return new ResponseEntity<>(entity, HttpStatus.CREATED);
    }

    @PutMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<NewsResponseDTO> update(@RequestBody NewsResponseDTO news) {

        try {
            News updatedNews = newsMapper.toEntity(news);
            newsService.update(updatedNews);
            return new ResponseEntity<>(newsMapper.toDto(updatedNews), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        try {
            newsService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
