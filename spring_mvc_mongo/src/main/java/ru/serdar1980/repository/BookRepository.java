package ru.serdar1980.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.serdar1980.domain.Book;

public interface BookRepository extends MongoRepository<Book, String> {
}
