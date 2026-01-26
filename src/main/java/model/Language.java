package model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "languages")
public class Language extends PanacheEntity {

    public String name;

    public String code;

    public String shortName;
}
