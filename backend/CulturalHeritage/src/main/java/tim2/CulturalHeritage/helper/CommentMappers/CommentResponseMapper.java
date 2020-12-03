package tim2.CulturalHeritage.helper.CommentMappers;

import tim2.CulturalHeritage.dto.responseDTO.AuthUserResponseDTO;
import tim2.CulturalHeritage.dto.responseDTO.CommentResponseDTO;
import tim2.CulturalHeritage.helper.AuthenticatedUserMappers.AuthUserResponseMapper;
import tim2.CulturalHeritage.helper.MapperInterface;
import tim2.CulturalHeritage.model.Comment;

import java.util.ArrayList;
import java.util.List;

public class CommentResponseMapper implements MapperInterface<Comment, CommentResponseDTO> {

    private AuthUserResponseMapper userMapper = new AuthUserResponseMapper();

    @Override
    public Comment toEntity(CommentResponseDTO dto) {
        return null;
    }

    @Override
    public CommentResponseDTO toDto(Comment entity) {
        AuthUserResponseDTO userDTO = userMapper.toDto(entity.getAuthenticatedUser());

        return new CommentResponseDTO(entity.getId(), entity.getContent(), userDTO, entity.getCulturalHeritage().getId());
    }

    @Override
    public List<CommentResponseDTO> toDtoList(List<Comment> entityList) {
        List<CommentResponseDTO> commentsDTO = new ArrayList<>();
        for(Comment c: entityList){
            commentsDTO.add(toDto(c));
        }

        return commentsDTO;
    }
}