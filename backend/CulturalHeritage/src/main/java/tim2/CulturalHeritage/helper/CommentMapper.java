package tim2.CulturalHeritage.helper;

import tim2.CulturalHeritage.dto.responseDTO.CommentResponseDTO;
import tim2.CulturalHeritage.model.Comment;

import java.util.List;

public class CommentMapper implements MapperInterface<Comment, CommentResponseDTO> {

    @Override
    public Comment toEntity(CommentResponseDTO dto) {
        return null;
    }

    @Override
    public CommentResponseDTO toDto(Comment entity) {
        return null;
    }

    @Override
    public List<CommentResponseDTO> toDtoList(List<Comment> entityList) {
        return null;
    }
}