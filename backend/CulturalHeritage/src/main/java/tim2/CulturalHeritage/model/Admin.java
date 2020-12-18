package tim2.CulturalHeritage.model;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Admin extends Person {

    @OneToMany(mappedBy = "admin")
    private List<News> news;

    public Admin() {
    }

    public Admin(Long id, String firstName, String lastName, String email, Boolean approved, String password,
            List<News> news) {
        super(id, firstName, lastName, email, approved, password);
        this.news = news;
    }

    public List<News> getNews() {
        return this.news;
    }

    public void setNews(List<News> news) {
        this.news = news;
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
}
