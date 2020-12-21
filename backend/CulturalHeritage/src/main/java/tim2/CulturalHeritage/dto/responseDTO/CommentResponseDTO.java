package tim2.CulturalHeritage.dto.responseDTO;


public class CommentResponseDTO {
    
    private Long id;
    private String content;
    private Long authenticatedUserID;
    private Long culturaHeritageID;
    private String imageUri;

    public CommentResponseDTO(){}

    public CommentResponseDTO(Long id, String content, Long authenticatedUserID, Long culturaHeritageID, String imageUri) {
        this.id = id;
        this.content = content;
        this.authenticatedUserID = authenticatedUserID;
        this.culturaHeritageID = culturaHeritageID;
        this.imageUri = imageUri;
    }
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getAuthenticatedUserID() {
        return this.authenticatedUserID;
    }

    public void setAuthenticatedUserID(Long authenticatedUserID) {
        this.authenticatedUserID = authenticatedUserID;
    }

    public Long getCulturaHeritageID() {
        return this.culturaHeritageID;
    }

    public void setCulturaHeritageID(Long culturaHeritageID) {
        this.culturaHeritageID = culturaHeritageID;
    }

    public String getImageUri() {
        return this.imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

}

