package ru.serdar1980.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.serdar1980.dao.BaseDao;
import ru.serdar1980.domain.Book;

import java.util.List;

@Service
public class BookDDService extends DBService<Book> {

    public BaseDao<Book> getDao() {
        return dao;
    }

    @Autowired
    @Qualifier("bookJDBCDaoImpl")
    public void setDao(BaseDao<Book> dao) {
        this.dao = dao;
    }

    @Override
    public void save(Book book) {
        dao.saveDao(book);
    }

    @Override
    public void delete(Book book) {
        dao.deleteDao(book);
    }

    @Override
    public Book findById(Long id) {
        return dao.findByIdDao(id);
    }

    @Override
    public List<Book> findAll() {
        return dao.findAllDao();
    }
}
