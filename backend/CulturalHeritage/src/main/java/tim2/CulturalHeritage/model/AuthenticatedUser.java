package tim2.CulturalHeritage.model;

import java.util.List;

import javax.persistence.*;

@Entity
public class AuthenticatedUser extends Person  {

    @ManyToMany
    @JoinTable(name = "subscription", 
        joinColumns = @JoinColumn(name = "authenticated_user_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "cultural_heritage_id", referencedColumnName = "id"))
    private List<CulturalHeritage> culturalHeritages;


    public AuthenticatedUser() {
        super();
    }

    public AuthenticatedUser(Long id, String firstName, String lastName, String email, Boolean approved, String password,
    List<CulturalHeritage> culturalHeritages) {
        super(id, firstName, lastName, email, approved, password);
        this.culturalHeritages = culturalHeritages;
    }

    public List<CulturalHeritage> getCulturalHeritages() {
        return this.culturalHeritages;
    }

    public void setCulturalHeritages(List<CulturalHeritage> culturalHeritages) {
        this.culturalHeritages = culturalHeritages;
    }


    @Override
    public String getUsername() {
        return this.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        String s = "";
        for (CulturalHeritage culturalHeritage : getCulturalHeritages()) {
            s = s + culturalHeritage;
        }
        return "{" +
            " culturalHeritages='" + s + "'" +
            "}";
    }
}