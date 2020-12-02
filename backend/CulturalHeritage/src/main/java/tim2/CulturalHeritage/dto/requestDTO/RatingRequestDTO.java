package tim2.CulturalHeritage.dto.requestDTO;


import tim2.CulturalHeritage.dto.responseDTO.AuthUserResponseDTO;
import tim2.CulturalHeritage.dto.responseDTO.CulturalHeritageResponseDTO;

import javax.validation.constraints.NotBlank;

public class RatingRequestDTO {

    @NotBlank(message="Grade can not be blank")
    private int grade;

    private CulturalHeritageResponseDTO culturalHeritage;

    public RatingRequestDTO(int grade, CulturalHeritageResponseDTO culturalHeritage) {
        this.grade = grade;
        this.culturalHeritage = culturalHeritage;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public CulturalHeritageResponseDTO getCulturalHeritage() {
        return culturalHeritage;
    }

    public void setCulturalHeritage(CulturalHeritageResponseDTO culturalHeritage) {
        this.culturalHeritage = culturalHeritage;
    }
}
