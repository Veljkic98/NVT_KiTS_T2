package tim2.CulturalHeritage.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tim2.CulturalHeritage.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findAll(Pageable pageable);

    @Query("select c from Comment c where c.culturalHeritage.id = :chID")
    public Page<Comment> findAll(Pageable pageable, @Param("chID") Long chID);
}
