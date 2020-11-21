package tim2.CulturalHeritage.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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

  @OneToMany(mappedBy="culturalHeritage", cascade=CascadeType.ALL)
  private List<News> news;
  
  @OneToMany(mappedBy="culturalHeritage", cascade=CascadeType.ALL)
  private List<Comment> comments;

  // todo: dodati slike

  public CulturalHeritage() {}

}
