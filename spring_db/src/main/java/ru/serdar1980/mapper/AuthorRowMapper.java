package ru.serdar1980.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.serdar1980.domain.Author;
import ru.serdar1980.domain.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AuthorRowMapper implements RowMapper {
    Map<Long, Author> fromSelect = new HashMap<>();
    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        Author author;
        author = fromSelect.get(Long.valueOf(rs.getLong("id")));
        if (author == null) {
            author = new Author();
            author.setId(rs.getLong("author_id"));
            author.setFio(rs.getString("author_fio"));
            fromSelect.put(author.getId(), author);
        }
        List<Book> books = author.getBooks();
        Book book = new Book();
        book.setId(rs.getLong("book_id"));
        book.setName(rs.getString("book_name"));
        books.add(book);
        author.setBooks(books);
        List<Author> authors = book.getAuthors();
        authors.add(author);
        book.setAuthors(authors);

        return author;
    }

    public List<Author> getFromSelect() {
        return fromSelect.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());
    }
}
