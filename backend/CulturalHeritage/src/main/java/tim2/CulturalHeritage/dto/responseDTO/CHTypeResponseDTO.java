package tim2.CulturalHeritage.dto.responseDTO;

import java.util.List;

public class CHTypeResponseDTO {
    private long id;
    private String name;
    private List<CHSubtypeResponseDTO> subtypes;

    public CHTypeResponseDTO(){}

    public CHTypeResponseDTO(long id, String name, List<CHSubtypeResponseDTO> subs ) {
        this.id = id;
        this.name = name;
        this.subtypes = subs;

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

    public List<CHSubtypeResponseDTO> getSubtypes() {
        return subtypes;
    }

    public void setSubtypes(List<CHSubtypeResponseDTO> subtypes) {
        this.subtypes = subtypes;
    }
}
