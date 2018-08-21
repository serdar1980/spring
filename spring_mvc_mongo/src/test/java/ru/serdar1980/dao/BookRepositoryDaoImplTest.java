package ru.serdar1980.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.Shell;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ru.serdar1980.domain.Author;
import ru.serdar1980.domain.Book;

import java.util.List;


@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class BookRepositoryDaoImplTest {

    @MockBean
    Shell shell;
    @Autowired
    @Qualifier("bookRepositoryImpl")
    private BaseDao<Book> dao;

    @Test
    public void iWantSaveShouldResponseBookWithId() {

        final String fio = "Александр Сергеевич Пушкин";
        final String bookName = "СКАЗКА О ЦАРЕ САЛТАНЕ, О СЫНЕ ЕГО СЛАВНОМ И МОГУЧЕМ БОГАТЫРЕ КНЯЗЕ ГВИДОНЕ САЛТАНОВИЧЕ И О ПРЕКРАСНОЙ ЦАРЕВНЕ ЛЕБЕДИ";
        Author author = new Author(fio);
        Book book = new Book(bookName);
        List<Book> books = author.getBooks();
        List<Author> authors = book.getAuthors();
        books.add(book);
        authors.add(author);
        author.setBooks(books);
        book.setAuthors(authors);
        dao.saveDao(book);
        Book fromDb = dao.findByIdDao(book.getId());
        Assert.assertTrue(bookName.equals(fromDb.getName()));
        Assert.assertTrue(fromDb.getAuthors().size() == 1);
        Assert.assertTrue(fio.equals(fromDb.getAuthors().get(0).getFio()));
        dao.deleteDao(book);
    }

    @Test
    public void iWantDeleteShouldResponseBookWasDeleted() {
        final String fio = "Александр Сергеевич Пушкин";
        final String bookName = "СКАЗКА О ЦАРЕ САЛТАНЕ, О СЫНЕ ЕГО СЛАВНОМ И МОГУЧЕМ БОГАТЫРЕ КНЯЗЕ ГВИДОНЕ САЛТАНОВИЧЕ И О ПРЕКРАСНОЙ ЦАРЕВНЕ ЛЕБЕДИ";
        Author author = new Author(fio);
        Book book = new Book(bookName);
        List<Book> books = author.getBooks();
        List<Author> authors = book.getAuthors();
        books.add(book);
        authors.add(author);
        author.setBooks(books);
        book.setAuthors(authors);

        author.setBooks(books);
        dao.saveDao(book);
        Book fromDb = dao.findByIdDao(book.getId());
        Assert.assertTrue(bookName.equals(fromDb.getName()));
        dao.deleteDao(book);
        fromDb = dao.findByIdDao(book.getId());
        Assert.assertTrue(fromDb == null);
    }


    @Test
    public void iSendIdShouldResponseBook() {
        final String bookName = "Совершенный код";
        Book book = dao.findByIdDao(2L);
        Assert.assertTrue(bookName.equals(book.getName()));
    }

    @Test
    public void iWantGetAllShouldResponseAllBook() {
        int countDef = 3;
        List<Book> book = dao.findAllDao();
        Assert.assertTrue(countDef == book.size());
    }
}
