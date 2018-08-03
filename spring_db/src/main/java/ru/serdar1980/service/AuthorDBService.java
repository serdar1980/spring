package ru.serdar1980.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.serdar1980.dao.AuthorJDBCDaoImpl;
import ru.serdar1980.dao.AuthorJPADaoImpl;
import ru.serdar1980.dao.BaseDao;
import ru.serdar1980.domain.Author;
import ru.serdar1980.enumeration.DaoType;

import java.util.List;

@Service
public class AuthorDBService extends DBService<Author> {

    @Autowired
    private AuthorJDBCDaoImpl jdbcDao;
    @Autowired
    private AuthorJPADaoImpl jpaDao;

    public BaseDao<Author> getDao() {
        return dao;
    }

    @Autowired
    @Qualifier("authorJDBCDaoImpl")
    public void setDao(BaseDao<Author> dao) {
        this.dao = dao;
    }

    @Override
    public void changeDBAcsses(DaoType type) {
        switch (type) {
            case JDBC:
                this.dao = jdbcDao;
                break;
            case JPA:
                this.dao = jpaDao;
                break;

        }

    }

    public void save(Author author) {
        dao.save(author);
    }

    @Override
    public void delete(Author author) {
        dao.delete(author);
    }

    @Override
    public Author findById(Long id) {
        dao.findById(id);
    }

    @Override
    public List<Author> findAll() {
        dao.findAll();
    }
}
