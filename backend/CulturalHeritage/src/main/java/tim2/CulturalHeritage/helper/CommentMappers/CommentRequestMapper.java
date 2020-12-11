package tim2.CulturalHeritage.helper.CommentMappers;

import tim2.CulturalHeritage.dto.requestDTO.CommentRequestDTO;
import tim2.CulturalHeritage.helper.MapperInterface;
import tim2.CulturalHeritage.model.Comment;

import java.util.ArrayList;
import java.util.List;

public class CommentRequestMapper implements MapperInterface<Comment, CommentRequestDTO> {


    @Override
    public Comment toEntity(CommentRequestDTO dto) {
        Comment com = new Comment();
        com.setContent(dto.getContent());

        return com;
    }

    @Override
    public CommentRequestDTO toDto(Comment entity) {
        return new CommentRequestDTO(entity.getContent(), entity.getCulturalHeritage().getId());
    }


    @Override
    public List<CommentRequestDTO> toDtoList(List<Comment> entityList) {
        List<CommentRequestDTO> commentsDTO = new ArrayList<>();
        for(Comment c: entityList){
            commentsDTO.add(toDto(c));
        }

        return commentsDTO;
    }
}
