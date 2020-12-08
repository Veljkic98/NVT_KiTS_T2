package tim2.CulturalHeritage.model;

import java.util.List;

import javax.persistence.*;

@Entity
public class AuthenticatedUser extends Person {

    @ManyToMany
    @JoinTable(name = "subscription", 
        joinColumns = @JoinColumn(name = "authenticated_user_id", referencedColumnName = "id"), 
        inverseJoinColumns = @JoinColumn(name = "cultural_heritage_id", referencedColumnName = "id"))
    private List<CulturalHeritage> culturalHeritages;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_authority",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
    private List<Authority> authorities;

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


}
