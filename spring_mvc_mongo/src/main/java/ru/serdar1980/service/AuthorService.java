package ru.serdar1980.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.serdar1980.domain.Author;
import ru.serdar1980.repository.AuthorRepository;

import java.util.List;

@Service
public class AuthorService {
    AuthorRepository repository;

    @Autowired
    public void setRepository(AuthorRepository repository) {
        this.repository = repository;
    }

    public List<Author> findAll() {
        return repository.findAll();
    }

    public Author findById(String id) {
        return (repository.findById(id).isPresent())?
                repository.findById(id).get() : null;
    }

    public Author insert(Author book) {
        return repository.save(book);
    }

    public void update(Author book) {
        repository.save(book);
    }

    public void delete(Author book) {
        repository.delete(book);
    }
}
