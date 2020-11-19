package tim2.CulturalHeritage.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
//Ova klasa je koren hijerarhije koja koristi koncept - jedan tabela po konkretnoj klasi
@Inheritance(strategy= InheritanceType.TABLE_PER_CLASS)
public abstract class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "approved")
    private Boolean approved;

    @Column(name = "is_logged_in")
    private Boolean isLoggedIn;

    public Person() {}
}
