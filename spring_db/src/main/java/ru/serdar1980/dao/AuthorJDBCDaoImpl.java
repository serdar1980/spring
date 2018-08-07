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
import ru.serdar1980.mapper.AuthorRowMapper;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AuthorJDBCDaoImpl implements IAuthorDao {

    private static final String SELECT_BY_ID_AUTHOR = "select a.id author_id, a.fio author_fio,  b.id book_id, b.name  book_name from tbl_author a left join book_author ba on a.id = ba.author_id left join tbl_book b  on ba.book_id = b.id where a.id = :id";
    private static final String SELECT_ALL_AUTHOR = "select a.id author_id, a.fio author_fio,  b.id book_id, b.name book_name from tbl_author a left join book_author ba on a.id = ba.author_id left join tbl_book b  on ba.book_id = b.id";
    private static final String INSERT_AUTHOR = "insert into tbl_author (fio) values (:fio)";
    private static final String DELETE_AUTHOR = "deleteDao from tbl_author where id =:id";
    private static final String UPDATE_AUTHOR = "update tbl_author set fio = :fio";
    private static final String DELETE_LINK = "deleteDao from book_author where author_id = :id ";
    private static final String INSERT_LINK = "insert into book_author (book_id, author_id) values(:book, :author);";


    private DataSource dataSource;
    private JdbcOperations operations;
    private NamedParameterJdbcTemplate template;
    private IBookDao bookDao;

    @Resource
    @Qualifier("authorJDBCDaoImpl")
    private AuthorJDBCDaoImpl self;

    public IBookDao getBookDao() {
        return bookDao;
    }

    @Autowired
    @Qualifier("bookJDBCDaoImpl")
    public void setBookDao(IBookDao bookDao) {
        this.bookDao = bookDao;
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
    public int saveDao(Author author) {
        int res = 0;
        if (author.getId() == null) {
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("fio", author.getFio());
            KeyHolder keyHolder = new GeneratedKeyHolder();
            res = template.update(INSERT_AUTHOR, parameters, keyHolder, new String[]{"ID"});
            author.setId(keyHolder.getKey().longValue());

            author.getBooks().stream().forEach(book -> {
                if (book.getId() == null) {
                    bookDao.saveDao(book);
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

        template.update(INSERT_LINK, parameters);
    }

    @Override
    public int deleteDao(Author author) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("id", author.getId());
        template.update(DELETE_LINK, parameters);

        parameters = new HashMap<String, Object>();
        parameters.put("id", author.getId());
        return template.update(DELETE_AUTHOR, parameters);

    }

    @Override
    public Author findByIdDao(Long id) {

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("id", id);
        AuthorRowMapper mapper = new AuthorRowMapper();
        template.query(SELECT_BY_ID_AUTHOR, parameters, mapper);
        List<Author> authors = mapper.getFromSelect();
        if (authors.size() != 1) {
            return null;
        }
        return authors.get(0);

    }

    @Override
    public List<Author> findAllDao() {
        AuthorRowMapper mapper = new AuthorRowMapper();
        template.query(SELECT_ALL_AUTHOR, mapper);
        return mapper.getFromSelect();
    }
}
