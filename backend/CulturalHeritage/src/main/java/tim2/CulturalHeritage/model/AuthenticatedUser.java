package tim2.CulturalHeritage.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

import javax.persistence.*;

@Entity
public class AuthenticatedUser extends Person implements UserDetails {

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

    @Column(name = "last_password_reset_date")
    private Timestamp lastPasswordResetDate;

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
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    public Timestamp getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    public void setLastPasswordResetDate(Timestamp lastPasswordResetDate) {
        this.lastPasswordResetDate = lastPasswordResetDate;
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
}