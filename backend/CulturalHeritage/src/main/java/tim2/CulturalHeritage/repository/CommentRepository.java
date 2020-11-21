package tim2.CulturalHeritage.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tim2.CulturalHeritage.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
