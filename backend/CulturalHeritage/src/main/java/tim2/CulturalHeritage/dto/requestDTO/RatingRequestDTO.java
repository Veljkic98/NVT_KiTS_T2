package tim2.CulturalHeritage.dto.requestDTO;

import tim2.CulturalHeritage.dto.CulturalHeritageDTO;
import tim2.CulturalHeritage.dto.responseDTO.AuthUserResponseDTO;

import javax.validation.constraints.NotBlank;

public class RatingRequestDTO {

    @NotBlank(message="Grade can not be blank")
    private int grade;

    private CulturalHeritageDTO culturalHeritage;

    public RatingRequestDTO(int grade,CulturalHeritageDTO culturalHeritage) {
        this.grade = grade;
        this.culturalHeritage = culturalHeritage;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public CulturalHeritageDTO getCulturalHeritage() {
        return culturalHeritage;
    }

    public void setCulturalHeritage(CulturalHeritageDTO culturalHeritage) {
        this.culturalHeritage = culturalHeritage;
    }
}
