package tim2.CulturalHeritage.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.JoinColumn;

@Entity
public class AuthenticatedUser extends Person {

    @ManyToMany
    @JoinTable(name = "subscription",
    	joinColumns = @JoinColumn(name = "authenticated_user_id", referencedColumnName = "id"),
    	inverseJoinColumns = @JoinColumn(name = "cultural_heritage_id", referencedColumnName = "id"))
    private List<CulturalHeritage> culturalHeritages;
}
