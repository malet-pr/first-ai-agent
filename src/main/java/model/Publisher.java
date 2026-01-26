package model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "publishers")
public class Publisher extends PanacheEntity {

    public String name;

    @ElementCollection
    @CollectionTable(name = "publisher_locations", joinColumns = @JoinColumn(name = "publisher_id"))
    @Column(name = "location")
    public List<String> locations;

    @ManyToMany(mappedBy = "publishers")
    public List<Book> books;


    //////////////////////////////////// Static query methods

    public static Publisher findByName(String name) {
        return find("name", name).firstResult();
    }



}
