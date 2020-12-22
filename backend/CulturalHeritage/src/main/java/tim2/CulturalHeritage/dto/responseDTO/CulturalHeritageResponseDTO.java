package tim2.CulturalHeritage.dto.responseDTO;

public class CulturalHeritageResponseDTO {

    private Long id;
    private String name;
    private String description;
    private Long locationID;
    private Long chsubtypeID;
    private String imageUri;
    private float avgRating;


    public CulturalHeritageResponseDTO() {}

    public CulturalHeritageResponseDTO(Long id, String name, String description, Long locationID, Long chsubtypeID, String imageUri, float avgRating) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.locationID = locationID;
        this.chsubtypeID = chsubtypeID;
        this.imageUri = imageUri;
        this.avgRating = avgRating;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getLocationID() {
        return this.locationID;
    }

    public void setLocationID(Long locationID) {
        this.locationID = locationID;
    }

    public Long getChsubtypeID() {
        return this.chsubtypeID;
    }

    public void setChsubtypeID(Long chsubtypeID) {
        this.chsubtypeID = chsubtypeID;
    }

    public String getImageUri() {
        return this.imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public float getAvgRating() { return avgRating; }

    public void setAvgRating(float avgRating) { this.avgRating = avgRating; }
}
