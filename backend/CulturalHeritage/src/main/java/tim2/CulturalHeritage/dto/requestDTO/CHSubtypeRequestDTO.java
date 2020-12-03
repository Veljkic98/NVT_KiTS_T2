package tim2.CulturalHeritage.dto.requestDTO;

public class CHSubtypeRequestDTO {
    private long id;
    private String name;


    public CHSubtypeRequestDTO(){}

    public CHSubtypeRequestDTO(long id, String name) {
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
