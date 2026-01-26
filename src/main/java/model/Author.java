package model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "authors")
public class Author extends PanacheEntity {

    public String firstName;

    public String lastName;

    public LocalDate birthDate;

    public String nationality;

    @ManyToMany(mappedBy = "authors")
    public List<Book> books;


    //////////////////////////////////// Static query methods

    public static Author findUnique(String firstName, String lastName) {
        return find("firstName = ?1 and lastName = ?2",
                firstName, lastName).firstResult();
    }

}
