package tim2.CulturalHeritage.helper;

import tim2.CulturalHeritage.dto.CommentDTO;
import tim2.CulturalHeritage.model.Comment;

import java.util.List;

public class CommentMapper implements MapperInterface<Comment, CommentDTO> {

    @Override
    public Comment toEntity(CommentDTO dto) {
        return null;
    }

    @Override
    public CommentDTO toDto(Comment entity) {
        return null;
    }

    @Override
    public List<CommentDTO> toDtoList(List<Comment> entityList) {
        return null;
    }
}