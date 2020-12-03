package tim2.CulturalHeritage.dto.requestDTO;

public class CHTypeRequestDTO {
    private String name;

    public CHTypeRequestDTO(){}

    public CHTypeRequestDTO(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
