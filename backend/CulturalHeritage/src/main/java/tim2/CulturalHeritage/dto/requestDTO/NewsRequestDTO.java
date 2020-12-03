package tim2.CulturalHeritage.dto.requestDTO;

public class NewsRequestDTO {

    private String heading;

    private String content;

    private long culturalHeritageID;

    private long adminID;

    public NewsRequestDTO(Long id, String heading, String content, long culturalHeritageID, long adminID) {
        this.heading = heading;
        this.content = content;
        this.culturalHeritageID = culturalHeritageID;
        this.adminID = adminID;
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
}


