package tim2.CulturalHeritage.dto.responseDTO;

import java.util.List;

public class CHTypeResponseDTO {
    private Long id;

    private String name;

    private List<CHSubtypeResponseDTO> subtypes;

    public CHTypeResponseDTO(){}

    public CHTypeResponseDTO(Long id, String name, List<CHSubtypeResponseDTO> subs ) {
        this.id = id;
        this.name = name;
        this.subtypes = subs;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
