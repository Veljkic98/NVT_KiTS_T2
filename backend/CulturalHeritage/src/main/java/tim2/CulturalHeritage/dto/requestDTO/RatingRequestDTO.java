package tim2.CulturalHeritage.dto.requestDTO;

import javax.validation.constraints.NotNull;

public class RatingRequestDTO {

    @NotNull(message = "Grade can not be blank")
    private int grade;

    @NotNull(message = "Cultural heritage id cannot be blank")
    private Long culturalHeritageId;

    public RatingRequestDTO(int grade, Long culturalHeritageId) {
        this.grade = grade;
        this.culturalHeritageId = culturalHeritageId;
    }

    public RatingRequestDTO() {
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public long getCulturalHeritageId() {
        return culturalHeritageId;
    }

    public void setCulturalHeritageId(long culturalHeritageId) {
        this.culturalHeritageId = culturalHeritageId;
    }
}
