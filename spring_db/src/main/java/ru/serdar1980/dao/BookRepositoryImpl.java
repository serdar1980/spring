package ru.serdar1980.dao;

import ru.serdar1980.domain.Book;

import java.util.List;

public class BookRepositoryImpl implements BaseDao<Book> {

    private BookRepository repository;


    @Override
    public int save(Book book) {
        repository.save(book);
        if (book.getId() != null) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public int delete(Book book) {
        repository.delete(book);
        return 1;
    }

    @Override
    public Book findById(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public List<Book> findAll() {
        return repository.findAll();
    }
}
