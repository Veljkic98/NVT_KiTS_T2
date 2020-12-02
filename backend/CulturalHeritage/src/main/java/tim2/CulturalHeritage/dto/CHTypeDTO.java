package tim2.CulturalHeritage.dto;

import tim2.CulturalHeritage.model.CHSubtype;

import java.util.List;

public class CHTypeDTO {
    private long id;
    private String name;
    private List<CHSubtype> subtypes;

    public CHTypeDTO(){}

    public CHTypeDTO(long id, String name, List<CHSubtype> subtypes) {
        this.id = id;
        this.name = name;
        this.subtypes = subtypes;
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

    public List<CHSubtype> getSubtypes() {
        return subtypes;
    }

    public void setSubtypes(List<CHSubtype> subtypes) {
        this.subtypes = subtypes;
    }
}
