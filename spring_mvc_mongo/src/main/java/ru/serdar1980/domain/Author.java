package ru.serdar1980.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "author")
public class Author {

    List<Book> books = new ArrayList<>();
    @Id
    private String id;
    private String fio;

    public Author() {
    }

    public Author(String fio) {
        this.fio = fio;
    }

    public String getId() {
        return id;
    }

    public Author setId(String id) {
        this.id = id;
        return this;
    }

    public String getFio() {
        return fio;
    }

    public Author setFio(String fio) {
        this.fio = fio;
        return this;
    }

    public List<Book> getBooks() {
        return books;
    }

    public Author setBooks(List<Book> books) {
        this.books = books;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Author author = (Author) o;

        if (books != null ? !books.equals(author.books) : author.books != null) return false;
        if (id != null ? !id.equals(author.id) : author.id != null) return false;
        return fio != null ? fio.equals(author.fio) : author.fio == null;
    }

    @Override
    public int hashCode() {
        int result = books != null ? books.hashCode() : 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (fio != null ? fio.hashCode() : 0);
        return result;
    }

    public static Author of() {
        return new Author();
    }
}
