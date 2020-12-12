package tim2.CulturalHeritage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import tim2.CulturalHeritage.model.Comment;
import tim2.CulturalHeritage.model.FileDB;
import tim2.CulturalHeritage.repository.CommentRepository;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private FileDBService fileDBService;

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
        FileDB fileDB = fileDBService.add(file);
        comment.setImages(fileDB);
        return commentRepository.save(comment);
    }

    @Override
    public Comment update(Comment comment) {
        if(null == commentRepository.findById(comment.getId()).orElse(null)){
            return null;
        }
        return commentRepository.save(comment);
    }

    @Override
    public void deleteById(Long id) {
        commentRepository.deleteById(id);
    }

}
