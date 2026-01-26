package model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "cover_arts")
public class CoverArt extends PanacheEntity {

    public String artist;

    public String description;

    public String imageUrl;

}