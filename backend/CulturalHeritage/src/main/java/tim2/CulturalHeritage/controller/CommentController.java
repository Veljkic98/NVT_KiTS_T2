package tim2.CulturalHeritage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tim2.CulturalHeritage.dto.requestDTO.CommentRequestDTO;
import tim2.CulturalHeritage.dto.responseDTO.CommentResponseDTO;
import tim2.CulturalHeritage.helper.CommentMappers.CommentRequestMapper;
import tim2.CulturalHeritage.helper.CommentMappers.CommentResponseMapper;
import tim2.CulturalHeritage.model.Comment;
import tim2.CulturalHeritage.model.CulturalHeritage;
import tim2.CulturalHeritage.service.CommentService;
import tim2.CulturalHeritage.service.CulturalHeritageService;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private CommentResponseMapper commentResponseMapper = new CommentResponseMapper();

    private CommentRequestMapper commentRequestMapper = new CommentRequestMapper();

    @Autowired
    private CommentService commentService;

    @Autowired
    private CulturalHeritageService culturalHeritageService;

    @RequestMapping(value="/by-page", method= RequestMethod.GET)
    public ResponseEntity<Page<CommentResponseDTO>> findAll(Pageable pageable) {

        Page<Comment> resultPage = commentService.findAll(pageable);
        List<CommentResponseDTO> commentsDTO = commentResponseMapper.toDtoList(resultPage.toList());
        Page<CommentResponseDTO> pageCommentsDTO = new PageImpl<>(commentsDTO, resultPage.getPageable(), resultPage.getTotalElements());

        return new ResponseEntity<>(pageCommentsDTO, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {

        try {
            Comment com = commentService.findById(id);
            return new ResponseEntity<>(commentResponseMapper.toDto(com), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody CommentRequestDTO commentRequest) {
        try {
            Comment com = commentRequestMapper.toEntity(commentRequest);
            //ovde bi trebalo na com objekat dodati usera koji je ulogovan
            CulturalHeritage ch = culturalHeritageService.findById(commentRequest.getCulturalHeritageID());
            com.setCulturalHeritage(ch);
            commentService.add(com);

            return new ResponseEntity<>(commentResponseMapper.toDto(com), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody CommentRequestDTO commentRequest) {

        try {
            Comment com = commentService.findById(id);
            com.setContent(commentRequest.getContent());
            commentService.update(com);
            return new ResponseEntity<>(commentResponseMapper.toDto(com), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        try {
            commentService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
