package tim2.CulturalHeritage.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "rating")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "grade", nullable = false)
    private int grade;

    @ManyToOne(fetch = FetchType.LAZY)
    private CulturalHeritage culturalHeritage;


    public Rating(Long id, int grade, CulturalHeritage culturalHeritage) {
        this.id = id;
        this.grade = grade;
        this.culturalHeritage = culturalHeritage;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getGrade() {
        return this.grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public CulturalHeritage getCulturalHeritage() {
        return this.culturalHeritage;
    }

    public void setCulturalHeritage(CulturalHeritage culturalHeritage) {
        this.culturalHeritage = culturalHeritage;
    }

}
