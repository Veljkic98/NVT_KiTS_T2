package tim2.CulturalHeritage.dto.requestDTO;

public class CHSubtypeRequestDTO {
    private String name;


    public CHSubtypeRequestDTO(){}

    public CHSubtypeRequestDTO(long id, String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
