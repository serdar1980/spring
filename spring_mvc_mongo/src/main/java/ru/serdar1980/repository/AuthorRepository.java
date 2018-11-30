package ru.serdar1980.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.serdar1980.domain.Author;

public interface AuthorRepository extends MongoRepository<Author, String> {
}
