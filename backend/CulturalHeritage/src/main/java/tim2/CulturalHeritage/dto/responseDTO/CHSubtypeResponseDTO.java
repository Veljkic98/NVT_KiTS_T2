package tim2.CulturalHeritage.dto.responseDTO;

import tim2.CulturalHeritage.model.CHSubtype;

public class CHSubtypeResponseDTO {
    private long id;
    private String name;


    public CHSubtypeResponseDTO(){}

    public CHSubtypeResponseDTO(long id, String name) {
        this.id = id;
        this.name = name;
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
}
