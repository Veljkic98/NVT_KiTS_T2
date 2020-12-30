package tim2.CulturalHeritage.dto.responseDTO;

public class AuthUserResponseDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private boolean approved;

    public AuthUserResponseDTO(Long id, String firstName, String lastName, String email, boolean approved) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.approved = approved;
    }
    
    public AuthUserResponseDTO(Long id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        //For casting logged in user to response (logged in is always approved)
        this.approved = true;
    }

    public AuthUserResponseDTO() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }
}
