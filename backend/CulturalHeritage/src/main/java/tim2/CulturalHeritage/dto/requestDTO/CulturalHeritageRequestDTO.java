package tim2.CulturalHeritage.dto.requestDTO;


import tim2.CulturalHeritage.dto.responseDTO.CHSubtypeResponseDTO;
import tim2.CulturalHeritage.model.Location;

import javax.validation.constraints.NotBlank;

public class CulturalHeritageRequestDTO {
    @NotBlank(message="Name cannot be blank")
    private String name;

    @NotBlank(message="Description cannot be blank")
    private String description;

    @NotBlank(message="Location cannot be blank")
    private Location location;

    @NotBlank(message="Subtype cannot be blank")
    private CHSubtypeResponseDTO chsubtype;
    // news, comments, images later

    public CulturalHeritageRequestDTO(){}

    public CulturalHeritageRequestDTO(String name, String description, Location location, CHSubtypeResponseDTO chsubtype){
        this.name = name;
        this.description = description;
        this.location = location;
        this.chsubtype = chsubtype;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public CHSubtypeResponseDTO getChsubtype() {
        return chsubtype;
    }

    public void setChsubtype(CHSubtypeResponseDTO chsubtype) {
        this.chsubtype = chsubtype;
    }


}
