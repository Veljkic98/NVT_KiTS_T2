package tim2.CulturalHeritage.dto.responseDTO;

import tim2.CulturalHeritage.dto.responseDTO.AuthUserResponseDTO;


public class CommentResponseDTO {
    private long id;
    private String content;
    private AuthUserResponseDTO author;
    // images?

    public CommentResponseDTO(){}

    public CommentResponseDTO(long id, String content, AuthUserResponseDTO author) {
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
