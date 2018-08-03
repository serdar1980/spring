package ru.serdar1980.dao;

import org.springframework.stereotype.Component;
import ru.serdar1980.domain.Author;

import java.util.List;

@Component
public class AuthorJPADaoImpl implements IAuthorDao {
    @Override
    public int save(Author author) {
        return 0;
    }

    @Override
    public int delete(Author author) {
        return 0;
    }

    @Override
    public Author findById(Long id) {
        return null;
    }

    @Override
    public List<Author> findAll() {
        return null;
    }
}
