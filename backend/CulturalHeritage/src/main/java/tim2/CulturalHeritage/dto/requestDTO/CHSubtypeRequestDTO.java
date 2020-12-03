package tim2.CulturalHeritage.dto.requestDTO;

import tim2.CulturalHeritage.dto.responseDTO.CHTypeResponseDTO;

//Bice izmjena, zavisi kako cemo CHType zadrzavati, dodavati i mijenjati
public class CHSubtypeRequestDTO {
    private String name;
    private CHTypeResponseDTO type;

    public CHSubtypeRequestDTO(){}

    public CHSubtypeRequestDTO(long id, String name, CHTypeResponseDTO type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CHTypeResponseDTO getType() {
        return type;
    }

    public void setType(CHTypeResponseDTO type) {
        this.type = type;
    }
}
