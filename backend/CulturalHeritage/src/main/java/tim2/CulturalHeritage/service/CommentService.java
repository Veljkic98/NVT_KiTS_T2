package tim2.CulturalHeritage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim2.CulturalHeritage.model.Comment;
import tim2.CulturalHeritage.repository.CommentRepository;

@Service
public class CommentService {
    
    @Autowired
    private CommentRepository commentRepository;

    public Comment findOne(Long id) {
		return commentRepository.findById(id).orElseGet(null);
	}
	
	public List<Comment> findAll() {
		return commentRepository.findAll();
	}
	
	public Comment save(Comment Admin) {
		return commentRepository.save(Admin);
	}
	
	public void remove(Long id) {
		commentRepository.deleteById(id);
    }

}
