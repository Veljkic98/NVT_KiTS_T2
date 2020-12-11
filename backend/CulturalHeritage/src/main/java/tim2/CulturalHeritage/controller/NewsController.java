package tim2.CulturalHeritage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import tim2.CulturalHeritage.dto.requestDTO.NewsRequestDTO;
import tim2.CulturalHeritage.dto.responseDTO.NewsResponseDTO;
import tim2.CulturalHeritage.helper.NewsMapper;
import tim2.CulturalHeritage.model.FileDB;
import tim2.CulturalHeritage.model.News;
import tim2.CulturalHeritage.repository.FileDBRepository;
import tim2.CulturalHeritage.service.NewsService;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @Autowired
    private FileDBRepository fileDBRepository;

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
    public ResponseEntity<?> add(@RequestPart("file") MultipartFile file,
            @RequestPart("news") NewsRequestDTO newsRequestDTO) {

        News news = newsService.add(newsRequestDTO, file);

        NewsResponseDTO newsResponseDTO = newsMapper.toDto(news);

        return new ResponseEntity<>(newsResponseDTO, HttpStatus.CREATED);
    }

    @GetMapping(path = "/images/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable Long id) {

        FileDB fileDB = fileDBRepository.findById(id).orElse(null);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
                .body(fileDB.getData());
    }

    @PutMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> update(@RequestBody NewsRequestDTO newsRequestDTO) {

        try {
            News updatedNews = newsMapper.toEntity(newsRequestDTO);
            updatedNews = newsService.update(updatedNews);
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
