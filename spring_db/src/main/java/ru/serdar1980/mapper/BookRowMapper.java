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

public class BookRowMapper implements RowMapper {
    private Map<Long, Book> fromSelect = new HashMap<>();

    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        Book book;
        book = fromSelect.get(Long.valueOf(rs.getLong("id")));
        if (book == null) {
            book = new Book();
            book.setId(rs.getLong("book_id"));
            book.setName(rs.getString("book_name"));
            fromSelect.put(book.getId(), book);
        }
        List<Author> authors = book.getAuthors();
        Author author = new Author();
        author.setId(rs.getLong("author_id"));
        author.setFio(rs.getString("author_fio"));
        authors.add(author);
        book.setAuthors(authors);
        List<Book> books = author.getBooks();
        books.add(book);
        author.setBooks(books);

        return book;
    }

    public List<Book> getFromSelect() {
        return fromSelect.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());
    }
}
