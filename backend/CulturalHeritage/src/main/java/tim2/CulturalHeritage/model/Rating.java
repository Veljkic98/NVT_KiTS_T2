package tim2.CulturalHeritage.model;

import javax.persistence.*;

@Entity
@Table(name = "rating",
        uniqueConstraints = {@UniqueConstraint(columnNames={"authenticated_user_id", "cultural_heritage_id"})})
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "grade", nullable = false)
    private int grade;

    @ManyToOne
    private CulturalHeritage culturalHeritage;

    @ManyToOne
    private AuthenticatedUser authenticatedUser;

    public Rating() {
    }

    public Rating(Long id, int grade, CulturalHeritage culturalHeritage, AuthenticatedUser authenticatedUser) {
        this.id = id;
        this.grade = grade;
        this.culturalHeritage = culturalHeritage;
        this.authenticatedUser = authenticatedUser;
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

    public AuthenticatedUser getAuthenticatedUser() {
        return this.authenticatedUser;
    }

    public void setAuthenticatedUser(AuthenticatedUser authenticatedUser) {
        this.authenticatedUser = authenticatedUser;
    }

}
