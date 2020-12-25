package tim2.CulturalHeritage.dto.requestDTO;


public class FilterRequestDTO {

    private String type;
    private String value;

    public FilterRequestDTO(String type, String value) {
        this.type = type;
        this.value = value;
    }

    public FilterRequestDTO() { }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
