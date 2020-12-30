package tim2.CulturalHeritage.service;

import java.security.AccessControlException;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import tim2.CulturalHeritage.model.AuthenticatedUser;
import tim2.CulturalHeritage.model.Comment;
import tim2.CulturalHeritage.model.FileDB;
import tim2.CulturalHeritage.repository.CommentRepository;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private FileDBService fileDBService;

    @Autowired
    private CulturalHeritageService chService;

    @Override
    public Page<Comment> findAll(Pageable pageable) {
        return commentRepository.findAll(pageable);
    }

    @Override
    public Comment findById(Long id) {
        return commentRepository.findById(id).orElse(null);
    }

    @Override
    public Comment add(Comment comment, MultipartFile file) {
        if (comment.getCulturalHeritage() == null) {
            throw new NullPointerException();
        }
        FileDB fileDB = fileDBService.add(file);
        comment.setImages(fileDB);
        return commentRepository.save(comment);
    }

    @Override
    public Comment update(Comment comment, MultipartFile file) throws AccessControlException {

        //if id is not found in the db
        if(null == commentRepository.findById(comment.getId()).orElse(null)){
            throw new EntityNotFoundException();
        }

        //if one user is trying to update other user's comment
        AuthenticatedUser userFromDB = commentRepository.findById(comment.getId()).orElse(null).getAuthenticatedUser();
        if(comment.getAuthenticatedUser().getId() != userFromDB.getId()){
            throw new AccessControlException("not allowed");
        }

        FileDB fileDB = fileDBService.add(file);
        comment.setImages(fileDB);
        return commentRepository.save(comment);
    }

    @Override
    public void deleteById(Long id) throws AccessControlException {

        //if id is not found in the db
        if(null == commentRepository.findById(id).orElse(null)){
            throw new EntityNotFoundException();
        }


        //if one user is trying to delete other user's comment
        AuthenticatedUser currentUser = (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AuthenticatedUser userFromDB = commentRepository.findById(id).orElse(null).getAuthenticatedUser();
        if(currentUser.getId() != userFromDB.getId()){
            throw new AccessControlException("not allowed");
        }

        commentRepository.deleteById(id);
    }

    @Override
    public Page<Comment> findAll(Pageable pageable, Long chID) {
        if (chID == null)
            throw new IllegalArgumentException();

        if (null == chService.findById(chID))
            throw new EntityNotFoundException();

        return commentRepository.findAll(pageable, chID);
    }

}
