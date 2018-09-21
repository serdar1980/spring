package ru.serdar1980.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.serdar1980.domain.Author;
import ru.serdar1980.domain.Book;
import ru.serdar1980.service.BookService;

import java.util.ArrayList;
import java.util.Arrays;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class BookServiceTest {
    BookService service;
    Book book;
    private static String FIO = "Александр Сергеевич Пушкин";

    @Autowired
    public void setService(BookService service) {
        this.service = service;
    }

    @Before
    public void authorServiceTestInit(){
        System.out.println("authorServiceTestInit");
        Author author = new Author();
        book = new Book("Сказка о золотой рыбке");
        book.setAuthors(new ArrayList<>(Arrays.asList(author)));
    }

    @Test
    public void insertAutor(){
        book = service.insert(book);
        Assert.assertNotNull("Автор не сохранился", book.getId());
    }

    @Test
    public void findById(){
        book = service.insert(book);
        Book fromBD = service.findById(book.getId());
        Assert.assertTrue(book.equals(fromBD));
    }

    @Test
    public void update(){
        final String name = "Вини Пух и все все все";
        book = service.insert(book);
        Book fromBD = service.findById(book.getId());
        fromBD.setName(name);
        service.update(fromBD);
        Book afterUpdateBD = service.findById(fromBD.getId());
        Assert.assertTrue(name.equals(afterUpdateBD.getName()));
    }

    @Test
    public void delete(){
        book = service.insert(book);
        service.delete(book);
        Book fromDb = service.findById(book.getId());
        Assert.assertNull("Не удалился", fromDb);
    }

}
