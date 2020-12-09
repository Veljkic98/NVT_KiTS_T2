package tim2.CulturalHeritage.dto.requestDTO;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CommentRequestDTO {

    @NotBlank(message="Comment content cannot be blank")
    private String content;

    @NotNull(message="Cultural heritage id cannot be blank")
    private long culturalHeritageID;

    public CommentRequestDTO() {
    }

    public CommentRequestDTO(String content, long CHid) {
        this.content = content;
        this.culturalHeritageID = CHid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCulturalHeritageID() {
        return culturalHeritageID;
    }

    public void setCulturalHeritageID(long culturalHeritageID) {
        this.culturalHeritageID = culturalHeritageID;
    }

}