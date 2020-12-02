package tim2.CulturalHeritage.dto;

import tim2.CulturalHeritage.dto.responseDTO.AuthUserResponseDTO;
import tim2.CulturalHeritage.dto.responseDTO.AuthUserResponseDTO;

public class CommentDTO {
    private long id;
    private String content;
    private AuthUserResponseDTO author;
    // images?

    public CommentDTO(){}

    public CommentDTO(long id, String content, AuthUserResponseDTO author) {
        this.id = id;
        this.content = content;
        this.author = author;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public AuthUserResponseDTO getAuthor() {
        return author;
    }

    public void setAuthor(AuthUserResponseDTO author) {
        this.author = author;
    }
}
