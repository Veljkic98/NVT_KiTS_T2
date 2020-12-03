package tim2.CulturalHeritage.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "chsubtype")
public class CHSubtype {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

    @OneToMany(mappedBy = "chsubtype", cascade = CascadeType.ALL)
    private List<CulturalHeritage> culturalHeritages;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private CHType chtype;



    public CHSubtype() {
    }

    public CHSubtype(Long id, String name, CHType chtype) {
        this.id = id;
        this.name = name;
        this.chtype = chtype;
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

    public CHType getChtype() {
        return this.chtype;
    }

    public void setChtype(CHType chtype) {
        this.chtype = chtype;
    }

}
