package ru.serdar1980.dao;

import ru.serdar1980.domain.Book;

import java.util.List;

public class BookJPADaoImpl implements IBookDao {
    @Override
    public int save(Book book) {
        return 0;
    }

    @Override
    public int delete(Book book) {
        return 0;
    }

    @Override
    public Book findById(Long id) {
        return null;
    }

    @Override
    public List<Book> findAll() {
        return null;
    }
}
