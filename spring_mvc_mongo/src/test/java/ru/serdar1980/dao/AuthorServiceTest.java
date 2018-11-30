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
import ru.serdar1980.service.AuthorService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class AuthorServiceTest {

    AuthorService service;
    Author author;
    private static String FIO = "Александр Сергеевич Пушкин";

    @Autowired
    public void setService(AuthorService service) {
        this.service = service;
    }

    @Before
    public void authorServiceTestInit(){
        System.out.println("authorServiceTestInit");
        author = new Author();
        Book book = new Book("Сказка о золотой рыбке");
        author.setFio(FIO)
              .setBooks(new ArrayList<>(Arrays.asList(book)));

    }

    @Test
    public void insertAutor(){
        List<Author> list = service.findAll();
        System.out.println(list.size());
        author = service.insert(author);
        Assert.assertNotNull("Автор не сохранился", author.getId());
    }

    @Test
    public void findById(){
        author = service.insert(author);
        Author fromBD = service.findById(author.getId());
        Assert.assertTrue(author.equals(fromBD));
    }

    @Test
    public void update(){
        final String fio = "Лермонтов Михаил Юрьевич";
        author = service.insert(author);
        Author fromBD = service.findById(author.getId());
        fromBD.setFio(fio);
        service.update(fromBD);
        Author afterUpdateBD = service.findById(fromBD.getId());
        Assert.assertTrue(fio.equals(afterUpdateBD.getFio()));
    }

    @Test
    public void delete(){
        author = service.insert(author);
        service.delete(author);
        Author fromDb = service.findById(author.getId());
        Assert.assertNull("Не удалился", fromDb);
    }

}
