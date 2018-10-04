package ru.serdar1980.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "book")
public class Book {
    @Id
    private String id;
    private String name;
    private List<Author> authors = new ArrayList<>();

    public Book() {
    }

    public Book(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public static Book of() {
        return new Book();
    }

    public String getName() {
        return name;
    }

    public Book setId(String id) {
        this.id = id;
        return this;
    }


    public List<Author> getAuthors() {
        return authors;
    }

    public Book setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (id != null ? !id.equals(book.id) : book.id != null) return false;
        if (name != null ? !name.equals(book.name) : book.name != null) return false;
        return authors != null ? authors.equals(book.authors) : book.authors == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (authors != null ? authors.hashCode() : 0);
        return result;
    }

    public Book setAuthors(List<Author> authors) {
        this.authors = new ArrayList<>(authors);
        return this;

    }
}
