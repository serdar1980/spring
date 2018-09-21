package ru.serdar1980.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.serdar1980.domain.Book;
import ru.serdar1980.repository.BookRepository;

import java.util.List;

@Service
public class BookService {
    BookRepository repository;

    @Autowired
    public void setRepository(BookRepository repository) {
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
        repository.delete(book);
    }
}
