package tim2.CulturalHeritage.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Admin extends Person {
    
    @OneToMany(mappedBy="admin")
	private List<News> news;
}
