package model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "books")
public class Book extends PanacheEntity {

    public String title;

    @ManyToMany(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    @JoinTable(
        name = "book_authors",
        joinColumns = @JoinColumn(name = "book_id"),
        inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    public List<Author> authors;

    @ManyToMany(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    @JoinTable(
        name = "book_publishers",
        joinColumns = @JoinColumn(name = "book_id"),
        inverseJoinColumns = @JoinColumn(name = "publisher_id")
    )
    public List<Publisher> publishers;

    public LocalDate publicationDate;

    @Enumerated(EnumType.STRING)
    public Genre genre;

    public Integer pages;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "language_id")
    public Language language;

    public String isbn;

    public String summary;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "cover_art_id")
    public CoverArt coverArt;

    @ManyToMany(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    @JoinTable(
        name = "book_awards",
        joinColumns = @JoinColumn(name = "book_id"),
        inverseJoinColumns = @JoinColumn(name = "award_id")
    )
    public List<Award> awards;

    @OneToMany(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    public List<Review> reviews;

    ///////////////// Helper methods

    public void addAuthor(Author author) {
        if (this.authors == null) {
            this.authors = new ArrayList<>();
        }
        this.authors.add(author);
    }

    public void addPublisher(Publisher publisher) {
        if (this.publishers == null) {
            this.publishers = new ArrayList<>();
        }
        this.publishers.add(publisher);
    }

    /////////////// Static query methods

    public static List<Book> findByAuthors(List<Author> authors){
        return find("authors in ?1", authors).list();
    }

}
