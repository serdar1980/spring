package ru.serdar1980.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import ru.serdar1980.dao.BaseDao;
import ru.serdar1980.dao.BookJDBCDaoImpl;
import ru.serdar1980.dao.BookJPADaoImpl;
import ru.serdar1980.domain.Book;
import ru.serdar1980.enumeration.DaoType;

import java.util.List;

public class BookDDService extends DBService<Book> {
    @Autowired
    private BookJDBCDaoImpl jdbcDao;
    @Autowired
    private BookJPADaoImpl jpaDao;

    public BaseDao<Book> getDao() {
        return dao;
    }

    @Autowired
    @Qualifier("bookJDBCDaoImpl")
    public void setDao(BaseDao<Book> dao) {
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

    @Override
    public void save(Book book) {
        dao.save(book);
    }

    @Override
    public void delete(Book book) {
        dao.delete(book);
    }

    @Override
    public Book findById(Long id) {
        return dao.findById(id);
    }

    @Override
    public List<Book> findAll() {
        return dao.findAll();
    }
}
