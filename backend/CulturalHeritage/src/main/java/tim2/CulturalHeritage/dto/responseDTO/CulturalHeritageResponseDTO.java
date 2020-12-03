package tim2.CulturalHeritage.dto.responseDTO;


import javax.validation.constraints.NotBlank;

public class CulturalHeritageResponseDTO {
    private long id;

    @NotBlank(message="Name cannot be blank")
    private String name;

    @NotBlank(message="Description cannot be blank")
    private String description;

    @NotBlank(message="Location cannot be blank")
    //private LocationResponseDTO location;

    @NotBlank(message="Subtype cannot be blank")
    private CHSubtypeResponseDTO chsubtype;
    // news, comments, images later

    public CulturalHeritageResponseDTO(){}

    public CulturalHeritageResponseDTO(long id, String name, String description, CHSubtypeResponseDTO chsubtype){
        this.id = id;
        this.name = name;
        this.description = description;
        //this.location = location;
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

//    public LocationResponseDTO getLocation() {
//        return location;
//    }
//
//    public void setLocation(LocationResponseDTO location) {
//        this.location = location;
//    }

    public CHSubtypeResponseDTO getChsubtype() {
        return chsubtype;
    }

    public void setChsubtype(CHSubtypeResponseDTO chsubtype) {
        this.chsubtype = chsubtype;
    }


}
