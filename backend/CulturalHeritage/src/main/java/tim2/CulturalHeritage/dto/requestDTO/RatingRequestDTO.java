package tim2.CulturalHeritage.dto.requestDTO;

import org.hibernate.annotations.NotFound;

import javax.validation.constraints.NotNull;

public class RatingRequestDTO {

    @NotNull(message="Grade can not be blank")
    private int grade;

    @NotNull(message="Cultural heritage id cannot be blank")
    private long culturalHeritageId;

    public RatingRequestDTO(int grade, long culturalHeritageId) {
        this.grade = grade;
        this.culturalHeritageId = culturalHeritageId;
    }

    public RatingRequestDTO() {}

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
