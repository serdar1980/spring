package ru.serdar1980.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.serdar1980.dao.*;
import ru.serdar1980.enumeration.DaoType;

@Service
public class ChangeDBAcsses {
    private AuthorDBService authorDBService;
    private AuthorJDBCDaoImpl authorJDBCDao;
    private AuthorJPADaoImpl authorJPADao;
    private AuthorRepositoryImpl authorRepository;

    private BookDDService bookDDService;
    private BookJDBCDaoImpl bookJDBCDao;
    private BookJPADaoImpl bookJPADao;
    private BookRepositoryImpl bookRepository;

    @Autowired
    public void setAuthorDBService(AuthorDBService authorDBService) {
        this.authorDBService = authorDBService;
    }

    @Autowired
    public void setBookDDService(BookDDService bookDDService) {
        this.bookDDService = bookDDService;
    }

    @Autowired
    public void setAuthorJDBCDao(AuthorJDBCDaoImpl authorJDBCDao) {
        this.authorJDBCDao = authorJDBCDao;
    }

    @Autowired
    public void setAuthorJPADao(AuthorJPADaoImpl authorJPADao) {
        this.authorJPADao = authorJPADao;
    }

    @Autowired
    public void setAuthorRepository(AuthorRepositoryImpl authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Autowired
    public void setBookJDBCDao(AuthorJDBCDaoImpl bookJDBCDao) {
        this.authorJDBCDao = bookJDBCDao;
    }

    @Autowired
    public void setBookJPADao(AuthorJPADaoImpl bookJPADao) {
        this.authorJPADao = bookJPADao;
    }

    @Autowired
    public void setBookRepository(AuthorRepositoryImpl bookRepository) {
        this.authorRepository = bookRepository;
    }

    public void changeDBAcsses(DaoType type) {
        switch (type) {
            case JDBC:
                authorDBService.setDao(authorJDBCDao);
                bookDDService.setDao(bookJDBCDao);
                break;
            case JPA:
                authorDBService.setDao(authorJPADao);
                bookDDService.setDao(bookJPADao);
                break;
            case REPOSITORY:
                authorDBService.setDao(authorRepository);
                bookDDService.setDao(bookRepository);
                break;
        }
    }

}
