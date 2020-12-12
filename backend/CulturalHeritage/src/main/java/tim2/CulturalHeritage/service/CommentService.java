package tim2.CulturalHeritage.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import tim2.CulturalHeritage.model.Comment;

public interface CommentService {

    public Page<Comment> findAll(Pageable pageable);

    public Comment findById(Long id);

    public Comment add(Comment comment, MultipartFile file);

    public Comment update(Comment comment);

    public void deleteById(Long id);
}
