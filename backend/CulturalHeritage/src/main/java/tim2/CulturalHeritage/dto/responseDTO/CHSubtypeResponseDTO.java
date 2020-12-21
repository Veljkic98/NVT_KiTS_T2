package tim2.CulturalHeritage.dto.responseDTO;


public class CHSubtypeResponseDTO {

    private Long id;

    private String name;

    private Long chTypeID;

    public CHSubtypeResponseDTO(){}

    public CHSubtypeResponseDTO(Long id, String name, Long chTypeID) {
        this.id = id;
        this.name = name;
        this.chTypeID = chTypeID;
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

    public Long getChTypeID() {
        return chTypeID;
    }

    public void setChTypeID(Long chTypeID) {
        this.chTypeID = chTypeID;
    }
}
