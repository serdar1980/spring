package ru.serdar1980.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.serdar1980.domain.Author;

import java.util.List;

@Component
public class AuthorRepositoryImpl implements BaseDao<Author> {
    private AuthorRepository repository;

    @Autowired
    public void setRepository(AuthorRepository repository) {
        this.repository = repository;
    }


    @Override
    public int save(Author author) {
        repository.save(author);
        if (author.getId() != null) {
            return 1;
        } else {
            return 0;
        }

    }

    @Override
    public int delete(Author author) {
        repository.delete(author);
        return 1;

    }

    @Override
    public Author findById(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public List<Author> findAll() {
        return repository.findAll();
    }
}
