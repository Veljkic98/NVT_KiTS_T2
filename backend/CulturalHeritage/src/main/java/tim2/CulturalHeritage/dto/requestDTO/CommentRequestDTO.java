package tim2.CulturalHeritage.dto.requestDTO;


public class CommentRequestDTO {

    private String content;

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
