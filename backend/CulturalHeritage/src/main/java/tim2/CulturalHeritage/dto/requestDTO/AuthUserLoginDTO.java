package tim2.CulturalHeritage.dto.requestDTO;

import javax.validation.constraints.NotBlank;

public class AuthUserLoginDTO {
    
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    public AuthUserLoginDTO(){}

    public AuthUserLoginDTO(String user, String pw){
        this.username = user;
        this.password = pw;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
