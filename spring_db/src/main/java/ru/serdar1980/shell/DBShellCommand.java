package ru.serdar1980.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.serdar1980.dao.BaseDao;
import ru.serdar1980.domain.Author;
import ru.serdar1980.domain.Book;
import ru.serdar1980.enumeration.DaoType;
import ru.serdar1980.service.AuthorDBService;
import ru.serdar1980.service.BookDDService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


@ShellComponent
public class DBShellCommand {

    AuthorDBService authorDBService;
    BookDDService bookDDService;

    @Autowired
    public void setBookDDService(BookDDService bookDDService) {
        this.bookDDService = bookDDService;
    }

    @Autowired
    public void setAuthorDBService(AuthorDBService authorDBService) {
        this.authorDBService = authorDBService;
    }

    @ShellMethod("Test")
    public String test() {
        BaseDao dao = authorDBService.getDao();
        return "Test";
    }

    @ShellMethod("Сменить тип доступа к БД доступны значения jdbc, jpa, repository")
    public String changeDBAcsses(
            @ShellOption String type
    ) {
        DaoType daoType = DaoType.fromString(type);
        if (daoType != null) {
            authorDBService.changeDBAcsses(daoType);
            return "Dao was change ";
        } else {
            return "Dao Type not fond ";
        }
    }

    @ShellMethod("Добавить автора ")
    public String addAuthor(
            @ShellOption String name, @ShellOption String[] bookNames
    ) {
        Author author = new Author(name);
        List<Book> books = new ArrayList<>();
        for (String bookName : bookNames
                ) {
            Book book = new Book(bookName);
            books.add(book);
        }
        author.setBooks(books);
        authorDBService.save(author);
        return "Author was added";
    }

    @ShellMethod("Добавить книгу ")
    public String addBook(
            @ShellOption String name, @ShellOption String[] authorFios
    ) {
        Book book = new Book(name);
        List<Author> authors = new ArrayList<>();
        Stream.of(authorFios).forEach(fio -> {
            Author author = new Author(fio);
            authors.add(author);
        });
        book.setAuthors(authors);
        bookDDService.save(book);
        return "Author was added";
    }
}
