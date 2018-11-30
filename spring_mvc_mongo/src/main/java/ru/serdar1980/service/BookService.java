package ru.serdar1980.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.serdar1980.domain.Book;
import ru.serdar1980.repository.BookRepository;

import java.util.List;

@Service
public class BookService {
    private final static Logger LOGGER = LoggerFactory.getLogger(BookService.class);
    BookRepository repository;

    @Autowired
    public BookService(BookRepository repository) {
        this.repository = repository;
    }


    public List<Book> findAll(){
         return repository.findAll();
    }

    public Book findById(String id){
         return (repository.findById(id).isPresent())?
                 repository.findById(id).get(): null;
    }

    public Book insert(Book book){
       return repository.save(book);
    }

    public void update(Book book){
        repository.save(book);
    }
    public void delete(Book book){

        try {
            repository.delete(book);
        } catch (IllegalArgumentException ex) {
            LOGGER.warn("Try to delete element which not found in DB");
        }
    }
}
