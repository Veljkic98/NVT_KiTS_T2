package tim2.CulturalHeritage.helper;

import tim2.CulturalHeritage.dto.requestDTO.CommentRequestDTO;
import tim2.CulturalHeritage.dto.responseDTO.CommentResponseDTO;
import tim2.CulturalHeritage.model.AuthenticatedUser;
import tim2.CulturalHeritage.model.Comment;
import tim2.CulturalHeritage.model.CulturalHeritage;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class CommentMapper implements MapperInterface<Comment, CommentResponseDTO, CommentRequestDTO> {

    @Override
    public Comment toEntity(CommentRequestDTO commentRequestDTO) throws ClassCastException{
        Comment comment = new Comment();
        comment.setContent(commentRequestDTO.getContent());
        //ch
        CulturalHeritage ch = new CulturalHeritage();
        ch.setId(commentRequestDTO.getCulturalHeritageID());
        comment.setCulturalHeritage(ch);
        //authenticated user
        AuthenticatedUser user = (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        comment.setAuthenticatedUser(user);
        return comment;
    }

    @Override
    public CommentResponseDTO toDto(Comment comment) {
        String imageUri;
        try{
            imageUri = ServletUriComponentsBuilder
            .fromCurrentContextPath()
            .path("api/files/")
            .path(comment.getImages().getId() + "")
            .toUriString();

        }catch(NullPointerException e){
            imageUri = null;
        }
        String userName = comment.getAuthenticatedUser().getFirstName() + " " + comment.getAuthenticatedUser().getLastName();
        
        return new CommentResponseDTO(comment.getId(), comment.getContent(), 
            comment.getAuthenticatedUser().getId(), comment.getCulturalHeritage().getId(), imageUri, userName);
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
