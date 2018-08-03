package ru.serdar1980.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.serdar1980.dao.BaseDao;
import ru.serdar1980.domain.Author;

import java.util.List;

@Service
public class AuthorDBService extends DBService<Author> {

    public BaseDao<Author> getDao() {
        return dao;
    }

    @Autowired
    @Qualifier("authorJDBCDaoImpl")
    public void setDao(BaseDao<Author> dao) {
        this.dao = dao;
    }

    @Override
    public void save(Author author) {
        dao.save(author);
    }

    @Override
    public void delete(Author author) {
        dao.delete(author);
    }

    @Override
    public Author findById(Long id) {
        return dao.findById(id);
    }

    @Override
    public List<Author> findAll() {
        return dao.findAll();
    }
}
