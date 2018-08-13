package ru.serdar1980.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.serdar1980.domain.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
}
