package tim2.CulturalHeritage.dto.responseDTO;


public class RatingResponseDTO {

    private Long id;
    private int grade;
    private Long chID;
    private Long userID;


    public RatingResponseDTO(Long id, int grade, Long chID, Long userID) {
        this.id = id;
        this.grade = grade;
        this.grade = grade;
        this.chID = chID;
        this.userID = userID;
    }

    public RatingResponseDTO() {}

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public Long getChID() {
        return chID;
    }

    public void setChID(Long chID) {
        this.chID = chID;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
