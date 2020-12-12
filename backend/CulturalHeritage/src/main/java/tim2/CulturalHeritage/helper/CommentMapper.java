package tim2.CulturalHeritage.helper;

import tim2.CulturalHeritage.dto.requestDTO.CommentRequestDTO;
import tim2.CulturalHeritage.dto.responseDTO.CommentResponseDTO;
import tim2.CulturalHeritage.model.Comment;
import tim2.CulturalHeritage.model.CulturalHeritage;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class CommentMapper implements MapperInterfaceEnhanced<Comment, CommentResponseDTO, CommentRequestDTO> {

    @Override
    public Comment toEntity(CommentRequestDTO commentRequestDTO) {
        Comment comment = new Comment();
        comment.setContent(commentRequestDTO.getContent());
        //ch
        CulturalHeritage ch = new CulturalHeritage();
        ch.setId(commentRequestDTO.getCulturalHeritageID());
        comment.setCulturalHeritage(ch);
        return comment;
    }

    @Override
    public CommentResponseDTO toDto(Comment comment) {
        String imageUri = ServletUriComponentsBuilder
        .fromCurrentContextPath()
        .path("api/files/")
        .path(comment.getImages().getId() + "")
        .toUriString();
        
        return new CommentResponseDTO(comment.getId(), comment.getContent(), 
            comment.getAuthenticatedUser().getId(), comment.getCulturalHeritage().getId(), imageUri);
    }

    @Override
    public List<CommentResponseDTO> toDtoList(List<Comment> entityList) {
        List<CommentResponseDTO> results = new ArrayList<>();
        if (entityList == null) {
            return results;
        }

        for (Comment comment : entityList) {
            results.add(toDto(comment));
        }

        return results;
    }


}
