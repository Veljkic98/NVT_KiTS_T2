package tim2.CulturalHeritage.dto.responseDTO;


public class CommentResponseDTO {
    private long id;

    private String content;

    private AuthUserResponseDTO author;

    private long culturaHeritageID;

    public CommentResponseDTO(){}

    public CommentResponseDTO(long id, String content, AuthUserResponseDTO author, long culturaHeritageID) {
        this.id = id;
        this.content = content;
        this.author = author;
        this.culturaHeritageID = culturaHeritageID;
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

    public long getCulturaHeritageID() {
        return culturaHeritageID;
    }

    public void setCulturaHeritageID(long culturaHeritageID) {
        this.culturaHeritageID = culturaHeritageID;
    }
}
