package ru.serdar1980.dao;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ru.serdar1980.domain.Book;

import java.util.List;

public class BookRepositoryImpl implements BaseDao<Book> {

    private BookRepository repository;

    @Autowired
    public void setRepository(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public int saveDao(Book book) {
        repository.save(book);
        if (book.getId() != null) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    @Transactional
    public int deleteDao(Book book) {
        repository.delete(book);
        return 1;
    }

    @Override
    @Transactional
    public Book findByIdDao(Long id) {
        Book book = null;
        try {
            book = repository.findById(id).get();
            Hibernate.initialize(book.getAuthors());
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
        return book;
    }

    @Override
    public List<Book> findAllDao() {
        return repository.findAll();
    }
}
