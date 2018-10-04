package ru.serdar1980.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.serdar1980.domain.Author;
import ru.serdar1980.repository.AuthorRepository;

import java.util.List;

@Service
public class AuthorService {

    private final static Logger logger = LoggerFactory.getLogger(AuthorService.class);

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

    public Author insert(Author author) {
        return repository.save(author);
    }

    public void update(Author author) {
        repository.save(author);
    }

    public void delete(Author author) {
        try {
            repository.delete(author);
        } catch (IllegalArgumentException ex) {
            logger.warn("Try to delete element which not found in DB");
        }
    }
}
