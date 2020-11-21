package tim2.CulturalHeritage.service;

import java.util.List;

import tim2.CulturalHeritage.model.Comment;

public interface CommentService {

    public List<Comment> findAll();

    public Comment findById(Long id);

    public Comment add(Comment comment);

    public Comment update(Comment comment);

    public void deleteById(Long id);
}
