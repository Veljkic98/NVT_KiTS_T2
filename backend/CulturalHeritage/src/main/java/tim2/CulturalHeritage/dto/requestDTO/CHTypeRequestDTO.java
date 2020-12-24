package tim2.CulturalHeritage.dto.requestDTO;

import javax.validation.constraints.NotBlank;

public class CHTypeRequestDTO {

    @NotBlank(message = "CH type name can not be blank.")
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
