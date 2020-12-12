package tim2.CulturalHeritage.dto.requestDTO;

import javax.validation.constraints.NotBlank;

public class CulturalHeritageRequestDTO {

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "Description cannot be blank")
    private String description;

    private Long locationID;

    private Long chsubtypeID;

    public CulturalHeritageRequestDTO() {}

    public CulturalHeritageRequestDTO(String name, String description, Long locationID, Long chsubtypeID) {
        this.name = name;
        this.description = description;
        this.locationID = locationID;
        this.chsubtypeID = chsubtypeID;
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

    @Override
    public String toString() {
        return "{" +
            " name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", locationID='" + getLocationID() + "'" +
            ", chsubtypeID='" + getChsubtypeID() + "'" +
            "}";
    }

}
