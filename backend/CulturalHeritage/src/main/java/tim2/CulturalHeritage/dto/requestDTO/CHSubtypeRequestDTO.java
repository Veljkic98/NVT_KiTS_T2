package tim2.CulturalHeritage.dto.requestDTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CHSubtypeRequestDTO {

    @NotBlank(message = "CH subtype name can not be blank.")
    private String name;

    @NotNull(message = "CH type ID can not be null.")
    private Long chTypeID;

    public CHSubtypeRequestDTO(){}

    public CHSubtypeRequestDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getChTypeID() { return chTypeID; }

    public void setChTypeID(Long chTypeID) { this.chTypeID = chTypeID; }
}
