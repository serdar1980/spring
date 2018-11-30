package ru.serdar1980.dto;

public class AuthorDto {
    String id;
    String fio;
    String books;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getBooks() {
        return books;
    }

    public void setBooks(String books) {
        this.books = books;
    }
}
