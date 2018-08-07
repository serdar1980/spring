package ru.serdar1980.dao;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional
    public int saveDao(Author author) {
        repository.save(author);
        if (author.getId() != null) {
            return 1;
        } else {
            return 0;
        }

    }

    @Override
    @Transactional
    public int deleteDao(Author author) {
        repository.delete(author);
        return 1;

    }

    @Override
    @Transactional
    public Author findByIdDao(Long id) {
        Author author = null;
        try {
            author = repository.findById(id).get();
            Hibernate.initialize(author.getBooks());
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
        return author;

    }

    @Override
    public List<Author> findAllDao() {
        return repository.findAll();
    }
}
