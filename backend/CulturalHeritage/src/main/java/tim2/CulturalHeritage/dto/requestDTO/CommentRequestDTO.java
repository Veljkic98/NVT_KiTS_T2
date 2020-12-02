package tim2.CulturalHeritage.dto.requestDTO;

import tim2.CulturalHeritage.dto.responseDTO.AuthUserResponseDTO;

public class CommentRequestDTO {
    private String content;
    private String author;
    // images?

    public CommentRequestDTO(){}

    public CommentRequestDTO(String content, String author) {
        this.content = content;
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
