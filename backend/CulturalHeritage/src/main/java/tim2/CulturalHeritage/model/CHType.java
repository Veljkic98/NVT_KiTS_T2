package tim2.CulturalHeritage.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "chtype")
public class CHType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "chtype", cascade = CascadeType.ALL)
    private List<CHSubtype> subtypes;

    public CHType() {
    }

    public CHType(Long id, String name, List<CHSubtype> subtypes) {
        this.id = id;
        this.name = name;
        this.subtypes = subtypes;
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

    public List<CHSubtype> getSubtypes() {
        return this.subtypes;
    }

    public void setSubtypes(List<CHSubtype> subtypes) {
        this.subtypes = subtypes;
    }

}
