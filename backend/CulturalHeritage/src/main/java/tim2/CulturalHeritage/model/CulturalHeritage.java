package tim2.CulturalHeritage.model;

import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "cultural_heritage")
public class CulturalHeritage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne
    private Location Location;

    @ManyToOne
    private CHSubtype chsubtype;

    @OneToMany(mappedBy = "culturalHeritage", cascade = CascadeType.ALL)
    private List<News> news;

    @OneToMany(mappedBy = "culturalHeritage", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @ElementCollection
    @CollectionTable(name = "images")
    private Map<Long, byte[]> images;

    public CulturalHeritage() {
    }

    public CulturalHeritage(Long id, String name, String description, Location Location, CHSubtype chsubtype,
            List<News> news, List<Comment> comments, Map<Long, byte[]> images) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.Location = Location;
        this.chsubtype = chsubtype;
        this.news = news;
        this.comments = comments;
        this.images = images;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Location getLocation() {
        return this.Location;
    }

    public void setLocation(Location Location) {
        this.Location = Location;
    }

    public CHSubtype getChsubtype() {
        return this.chsubtype;
    }

    public void setChsubtype(CHSubtype chsubtype) {
        this.chsubtype = chsubtype;
    }

    public List<News> getNews() {
        return this.news;
    }

    public void setNews(List<News> news) {
        this.news = news;
    }

    public List<Comment> getComments() {
        return this.comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Map<Long, byte[]> getImages() {
        return this.images;
    }

    public void setImages(Map<Long, byte[]> images) {
        this.images = images;
    }

}
