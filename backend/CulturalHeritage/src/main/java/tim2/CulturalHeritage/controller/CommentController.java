package tim2.CulturalHeritage.controller;

import java.security.AccessControlException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import tim2.CulturalHeritage.dto.requestDTO.CommentRequestDTO;
import tim2.CulturalHeritage.dto.responseDTO.CommentResponseDTO;
import tim2.CulturalHeritage.helper.ApiErrors;
import tim2.CulturalHeritage.helper.CommentMapper;
import tim2.CulturalHeritage.model.Comment;
import tim2.CulturalHeritage.service.CommentService;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private CommentMapper commentMapper = new CommentMapper();

    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "/by-page", method = RequestMethod.GET)
    public ResponseEntity<Page<CommentResponseDTO>> findAll(Pageable pageable) {

        Page<Comment> resultPage = commentService.findAll(pageable);
        List<CommentResponseDTO> commentsDTO = commentMapper.toDtoList(resultPage.toList());
        Page<CommentResponseDTO> pageCommentsDTO = new PageImpl<>(commentsDTO, resultPage.getPageable(),
                resultPage.getTotalElements());

        return new ResponseEntity<>(pageCommentsDTO, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {

        try {
            Comment com = commentService.findById(id);
            return new ResponseEntity<>(commentMapper.toDto(com), HttpStatus.OK);
        }
        catch (NullPointerException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } 
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<CommentResponseDTO> add(@RequestPart(value = "file", required = false) MultipartFile file,
            @Valid @RequestPart("comment") CommentRequestDTO commentRequestDTO, Errors errors) {

        if (errors.hasErrors()) {
            return new ResponseEntity(new ApiErrors(errors.getAllErrors()), HttpStatus.BAD_REQUEST);
        }
        try{
            Comment comment = commentMapper.toEntity(commentRequestDTO);
            comment = commentService.add(comment, file);
            CommentResponseDTO commentResponseDTO = commentMapper.toDto(comment);
            return new ResponseEntity<CommentResponseDTO>(commentResponseDTO, HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/{id}")
    public ResponseEntity<CommentResponseDTO> update(
            @RequestPart("file") MultipartFile file,
            @Valid @RequestPart("comment") CommentRequestDTO commentRequestDTO, 
            @PathVariable Long id,
            Errors errors) {

        if (errors.hasErrors()) {
            return new ResponseEntity(new ApiErrors(errors.getAllErrors()), HttpStatus.BAD_REQUEST);
        }
        try {
            Comment comment = commentMapper.toEntity(commentRequestDTO);
            comment.setId(id);
            comment = commentService.update(comment, file);
            CommentResponseDTO commentResponseDTO = commentMapper.toDto(comment);
            return new ResponseEntity<CommentResponseDTO>(commentResponseDTO, HttpStatus.OK);
        }
        catch(AccessControlException e){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        catch(EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } 
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        
        try {
            commentService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch(AccessControlException e){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } 
        catch(EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } 
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Find all comments for specific Cultural Heritage.
     *
     * @param pageable
     * @param chId     Cultural Heritage id
     * @return
     */
    @GetMapping(value = "/by-page/{chID}")
    public ResponseEntity<Page<CommentResponseDTO>> findAll(Pageable pageable, @PathVariable Long chID) {

        try {
            Page<Comment> resultPage = commentService.findAll(pageable, chID);
            List<CommentResponseDTO> commDTO = commentMapper.toDtoList(resultPage.toList());
            Page<CommentResponseDTO> commDTOPage = new PageImpl<>(commDTO, resultPage.getPageable(),
                    resultPage.getTotalElements());
            return new ResponseEntity<>(commDTOPage, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
