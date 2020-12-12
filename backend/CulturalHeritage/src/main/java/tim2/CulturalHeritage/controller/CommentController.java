package tim2.CulturalHeritage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import tim2.CulturalHeritage.dto.requestDTO.CommentRequestDTO;
import tim2.CulturalHeritage.dto.responseDTO.CommentResponseDTO;
import tim2.CulturalHeritage.helper.ApiErrors;
import tim2.CulturalHeritage.helper.CommentMapper;
import tim2.CulturalHeritage.model.AuthenticatedUser;
import tim2.CulturalHeritage.model.Comment;
import tim2.CulturalHeritage.service.CommentService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private CommentMapper commentMapper = new CommentMapper();

    @Autowired
    private CommentService commentService;


    @RequestMapping(value="/by-page", method= RequestMethod.GET)
    public ResponseEntity<Page<CommentResponseDTO>> findAll(Pageable pageable) {

        Page<Comment> resultPage = commentService.findAll(pageable);
        List<CommentResponseDTO> commentsDTO = commentMapper.toDtoList(resultPage.toList());
        Page<CommentResponseDTO> pageCommentsDTO = new PageImpl<>(commentsDTO, resultPage.getPageable(), resultPage.getTotalElements());

        return new ResponseEntity<>(pageCommentsDTO, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {

        try {
            Comment com = commentService.findById(id);
            return new ResponseEntity<>(commentMapper.toDto(com), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    
    @PostMapping
    // @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> add(@RequestPart("file") MultipartFile file,
        @Valid @RequestPart("comment") CommentRequestDTO commentRequestDTO , Errors errors) {
        
        if (errors.hasErrors()) {
            return new ResponseEntity(new ApiErrors(errors.getAllErrors()), HttpStatus.BAD_REQUEST);
        }
        try {
            Comment comment = commentMapper.toEntity(commentRequestDTO);
            comment = commentService.add(comment, file);
            CommentResponseDTO commentResponseDTO = commentMapper.toDto(comment);
            return new ResponseEntity<>(commentResponseDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody CommentRequestDTO commentRequest, Errors errors) {
        if (errors.hasErrors()) {
            return new ResponseEntity(new ApiErrors(errors.getAllErrors()), HttpStatus.BAD_REQUEST);
        }
        AuthenticatedUser user = (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            Comment com = commentService.findById(id);
            if (com.getAuthenticatedUser().getId() != user.getId()) {
                return new ResponseEntity<>("", HttpStatus.FORBIDDEN);
            }
            com.setContent(commentRequest.getContent());
            commentService.update(com);
            return new ResponseEntity<>(commentMapper.toDto(com), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        AuthenticatedUser user = (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            Comment com = commentService.findById(id);
            if (com.getAuthenticatedUser().getId() != user.getId()) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            commentService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
