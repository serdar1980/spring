package ru.serdar1980.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.serdar1980.domain.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
