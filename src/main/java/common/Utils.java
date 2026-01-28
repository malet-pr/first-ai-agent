package common;

import dto.CatalogResponseData;
import jakarta.enterprise.context.ApplicationScoped;
import model.Author;
import model.Book;

@ApplicationScoped
public class Utils {

    public String cleanIsbn(String isbn) {
        if (isbn == null) {
            return "";
        }
        return isbn.replaceAll("[^0-9X]", "");
    }

    public String toStringForTools(Book book) {
        StringBuilder sb = new StringBuilder();
        sb.append("ISBN: ").append(book.isbn).append("\n");
        sb.append("title: ").append(book.title).append("\n");
        sb.append("authors: ");
        if (book.authors != null) {
            for (Author author : book.authors) {
                sb.append(author.lastName).append(", ").append(author.firstName).append("; ");
            }
        }
        return sb.toString();
    }


}
