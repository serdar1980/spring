package ru.serdar1980.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.serdar1980.domain.Author;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AuthorJDBCDaoImplTest {

    @Autowired
    @Qualifier("authorJDBCDaoImpl")
    private BaseDao<Author> dao;


    @Test
    public void iSendIdSouldResponceAuthor() {
        Author author = dao.findById(3L);
    }

    @Test
    public void iWantGetAllSouldResponceAllAuthor() {
        Long countDef = 6L;
        List<Author> authors = dao.findAll();
        Assert.assertTrue(countDef.equals(authors.size()));
    }
}
