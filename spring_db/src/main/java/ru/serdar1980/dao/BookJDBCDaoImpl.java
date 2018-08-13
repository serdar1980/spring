package ru.serdar1980.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.serdar1980.domain.Author;
import ru.serdar1980.domain.Book;
import ru.serdar1980.mapper.BookRowMapper;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BookJDBCDaoImpl implements IBookDao {
    private static final String SELECT_BY_ID_BOOK = "select a.id author_id, a.fio author_fio,  b.id book_id, b.name  book_name from tbl_book b left join book_author ba on b.id = ba.book_id left join tbl_author a  on ba.author_id = a.id  where book_id = :id  ";
    private static final String SELECT_ALL_BOOK = "select a.id author_id, a.fio author_fio,  b.id book_id, b.name  book_name from tbl_book b left join book_author ba on b.id = ba.book_id left join tbl_author a  on ba.author_id = a.id ";
    private static final String INSERT_BOOK = "insert into tbl_book (name) values (:name)";
    private static final String DELETE_BOOK = "deleteDao from tbl_book where id =:id";
    private static final String UPDATE_BOOK = "update tbl_book set name = :name";
    private static final String DELETE_LINK = "deleteDao from book_author where book_id = :id ";
    private static final String INSERT_LINK = "insert into book_author (book_id, author_id) values(:book, :author);";

    private DataSource dataSource;
    private JdbcOperations operations;
    private NamedParameterJdbcTemplate template;
    private IAuthorDao authorDao;

    @Resource
    @Qualifier("bookJDBCDaoImpl")
    private BookJDBCDaoImpl self;


    public IAuthorDao getAuthorDao() {
        return authorDao;
    }

    @Autowired
    @Qualifier("authorJDBCDaoImpl")
    public void setAuthorDao(IAuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public JdbcOperations getOperations() {
        return operations;
    }

    @Autowired
    public void setOperations(JdbcOperations operations) {
        this.operations = operations;
        this.template = new NamedParameterJdbcTemplate(operations);
    }

    public NamedParameterJdbcTemplate getTemplate() {
        return template;
    }

    public void setTemplate(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public int saveDao(Book book) {
        int res = 0;
        if (book.getId() == null) {
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("name", book.getName());

            KeyHolder keyHolder = new GeneratedKeyHolder();
            res = template.update(INSERT_BOOK, parameters, keyHolder, new String[]{"ID"});
            book.setId(keyHolder.getKey().longValue());
            book.getAuthors().stream().forEach(author -> {
                if (author.getId() == null) {
                    authorDao.saveDao(author);
                    self.insertLink(book, author);
                }
            });
        }
        return res;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void insertLink(Book book, Author author) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("book", book.getId());
        parameters.put("author", author.getId());
        String[] test = {INSERT_LINK};
        parameters.entrySet().stream().forEach(entry -> {
            test[0] = test[0].replace(":" + entry.getKey(), entry.getValue().toString());
        });
        System.out.println(test[0]);

        int res = template.update(INSERT_LINK, parameters);
        System.out.println(res);
    }

    @Override
    public int deleteDao(Book book) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("id", book.getId());
        template.update(DELETE_LINK, parameters);

        parameters = new HashMap<String, Object>();
        parameters.put("id", book.getId());
        return template.update(DELETE_BOOK, parameters);

    }

    @Override
    public Book findByIdDao(Long id) {

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("id", id);
        BookRowMapper mapper = new BookRowMapper();
        template.query(SELECT_BY_ID_BOOK, parameters, mapper);
        List<Book> books = mapper.getFromSelect();
        if (books.size() != 1) {
            return null;
        }
        return books.get(0);
    }

    @Override
    public List<Book> findAllDao() {
        BookRowMapper mapper = new BookRowMapper();
        template.query(SELECT_ALL_BOOK, mapper);
        return mapper.getFromSelect();
    }
}
