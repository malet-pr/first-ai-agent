package model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "reviews")
public class Review extends PanacheEntity {

    public String source;

    public Float rating;

    public String review;

}
