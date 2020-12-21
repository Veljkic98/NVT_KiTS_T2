package tim2.CulturalHeritage.dto.responseDTO;

public class NewsResponseDTO {

    private Long id;
    private String heading;
    private String content;
    private long culturalHeritageID;
    private long adminID;
    private String imageUri;

    public NewsResponseDTO(Long id, String heading, String content, long culturalHeritageID, long adminID, String imageUri) {
        this.id = id;
        this.heading = heading;
        this.content = content;
        this.culturalHeritageID = culturalHeritageID;
        this.adminID = adminID;
        this.imageUri = imageUri;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
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

    public long getAdminID() {
        return adminID;
    }

    public void setAdminID(long adminID) {
        this.adminID = adminID;
    }

    public String getImageUri() {
        return this.imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}
