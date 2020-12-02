package tim2.CulturalHeritage.dto;


import tim2.CulturalHeritage.dto.responseDTO.LocationResponseDTO;
import tim2.CulturalHeritage.model.CHSubtype;
import tim2.CulturalHeritage.model.CulturalHeritage;
import tim2.CulturalHeritage.model.Location;

import javax.validation.constraints.NotBlank;

public class CulturalHeritageDTO {
    private long id;

    @NotBlank(message="Name cannot be blank")
    private String name;

    @NotBlank(message="Description cannot be blank")
    private String description;

    @NotBlank(message="Location cannot be blank")
    private Location location;

    @NotBlank(message="Subtype cannot be blank")
    private CHSubtypeDTO chsubtype;
    // news, comments, images later

    public CulturalHeritageDTO(){}

    public CulturalHeritageDTO(long id, String name, String description, Location location, CHSubtypeDTO chsubtype){
        this.id = id;
        this.name = name;
        this.description = description;
        this.location = location;
        this.chsubtype = chsubtype;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public CHSubtypeDTO getChsubtype() {
        return chsubtype;
    }

    public void setChsubtype(CHSubtypeDTO chsubtype) {
        this.chsubtype = chsubtype;
    }


}
