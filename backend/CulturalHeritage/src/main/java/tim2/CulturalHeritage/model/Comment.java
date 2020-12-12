package tim2.CulturalHeritage.model;

import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "content")
    private String content;

    @ManyToOne
    private CulturalHeritage culturalHeritage;

    @ManyToOne
    private AuthenticatedUser authenticatedUser;

    @OneToOne
    private FileDB images;

    public Comment() {
    }

    public Comment(long id, String content, CulturalHeritage culturalHeritage, 
        AuthenticatedUser authenticatedUser, FileDB images) {
        this.id = id;
        this.content = content;
        this.culturalHeritage = culturalHeritage;
        this.authenticatedUser = authenticatedUser;
        this.images = images;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public CulturalHeritage getCulturalHeritage() {
        return this.culturalHeritage;
    }

    public void setCulturalHeritage(CulturalHeritage culturalHeritage) {
        this.culturalHeritage = culturalHeritage;
    }

    public AuthenticatedUser getAuthenticatedUser() {
        return this.authenticatedUser;
    }

    public void setAuthenticatedUser(AuthenticatedUser authenticatedUser) {
        this.authenticatedUser = authenticatedUser;
    }


    public FileDB getImages() {
        return this.images;
    }

    public void setImages(FileDB images) {
        this.images = images;
    }


}
