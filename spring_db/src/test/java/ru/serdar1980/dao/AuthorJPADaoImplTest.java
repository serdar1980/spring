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
public class AuthorJPADaoImplTest {

    @MockBean
    Shell shell;
    @Autowired
    @Qualifier("authorJPADaoImpl")
    private BaseDao<Author> dao;

    @Test
    public void iWantSaveShouldResponseAuthorWithId() {

        final String fio = "Александр Сергеевич Пушкин";
        final String bookName = "СКАЗКА О ЦАРЕ САЛТАНЕ, О СЫНЕ ЕГО СЛАВНОМ И МОГУЧЕМ БОГАТЫРЕ КНЯЗЕ ГВИДОНЕ САЛТАНОВИЧЕ И О ПРЕКРАСНОЙ ЦАРЕВНЕ ЛЕБЕДИ";
        Author author = new Author(fio);
        Book book = new Book(bookName);
        List<Book> books = author.getBooks();
        books.add(book);

        author.setBooks(books);
        dao.saveDao(author);
        Author authorFromDb = dao.findByIdDao(author.getId());
        Assert.assertTrue(fio.equals(authorFromDb.getFio()));
        Assert.assertTrue(authorFromDb.getBooks().size() == 1);
        Assert.assertTrue(bookName.equals(authorFromDb.getBooks().get(0).getName()));
        dao.deleteDao(author);
    }

    @Test
    public void iWantDeleteShouldResponseAuthorWasDeleted() {
        final String fio = "Александр Сергеевич Пушкин";
        final String bookName = "СКАЗКА О ЦАРЕ САЛТАНЕ, О СЫНЕ ЕГО СЛАВНОМ И МОГУЧЕМ БОГАТЫРЕ КНЯЗЕ ГВИДОНЕ САЛТАНОВИЧЕ И О ПРЕКРАСНОЙ ЦАРЕВНЕ ЛЕБЕДИ";
        Author author = new Author(fio);
        Book book = new Book(bookName);
        List<Book> books = author.getBooks();
        books.add(book);

        author.setBooks(books);
        dao.saveDao(author);
        Author authorFromDb = dao.findByIdDao(author.getId());
        Assert.assertTrue(fio.equals(authorFromDb.getFio()));
        dao.deleteDao(author);
        authorFromDb = dao.findByIdDao(author.getId());
        Assert.assertTrue(authorFromDb == null);
    }


    @Test
    public void iSendIdShouldResponseAuthor() {
        final String fio = "Эрих Гамма";
        Author author = dao.findByIdDao(3L);
        Assert.assertTrue(fio.equals(author.getFio()));
    }

    @Test
    public void iWantGetAllShouldResponseAllAuthor() {
        int countDef = 6;
        List<Author> authors = dao.findAllDao();
        Assert.assertTrue(countDef == authors.size());
    }
}
