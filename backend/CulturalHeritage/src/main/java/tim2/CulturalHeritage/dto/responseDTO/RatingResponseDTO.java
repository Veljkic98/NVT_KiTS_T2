package tim2.CulturalHeritage.dto.responseDTO;

import javax.validation.constraints.NotBlank;

public class RatingResponseDTO {

    private long id;

    private int grade;

    private long chID;

    private long userID;


    public RatingResponseDTO(long id, int grade, long chID, long userID) {
        this.id = id;
        this.grade = grade;
        this.grade = grade;
        this.chID = chID;
        this.userID = userID;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public long getChID() {
        return chID;
    }

    public void setChID(long chID) {
        this.chID = chID;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
