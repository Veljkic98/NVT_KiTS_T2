package tim2.CulturalHeritage.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Admin extends Person {

    @OneToMany(mappedBy = "admin")
    private List<News> news;

    public Admin() {
    }

    public Admin(Long id, String firstName, String lastName, String email, Boolean approved, Boolean isLoggedIn,
            List<News> news) {
        super(id, firstName, lastName, email, approved, isLoggedIn);
        this.news = news;
    }

    public List<News> getNews() {
        return this.news;
    }

    public void setNews(List<News> news) {
        this.news = news;
    }

}
