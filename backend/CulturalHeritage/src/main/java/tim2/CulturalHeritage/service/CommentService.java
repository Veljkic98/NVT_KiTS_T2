package tim2.CulturalHeritage.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tim2.CulturalHeritage.model.Comment;

public interface CommentService {

    public Page<Comment> findAll(Pageable pageable);

    public Comment findById(Long id);

    public Comment add(Comment comment);

    public Comment update(Comment comment);

    public void deleteById(Long id);
}
