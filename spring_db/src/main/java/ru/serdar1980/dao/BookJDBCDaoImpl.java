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
import ru.serdar1980.domain.Book;
import ru.serdar1980.mapper.BookRawMapper;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BookJDBCDaoImpl implements IBookDao {
    private static final String SELECT_BY_ID_BOOK = "select * from tbl_book b left join tbl_author a on (b.id= a.id) where author_id = :id  ";
    private static final String SELECT_ALL_BOOK = "select * from tbl_book b left join tbl_author a on (b.id= a.id)";
    private static final String INSERT_BOOK = "insert into tbl_book (name) values (:name)";
    private static final String DELETE_BOOK = "delete from tbl_book where id =:id";
    private static final String UPDATE_BOOK = "update tbl_book set name = :name";
    private static final String DELETE_LINK = "delete from book_author where book_id = :id ";

    private DataSource dataSource;
    private JdbcOperations operations;
    private NamedParameterJdbcTemplate template;
    private IAuthorDao authorDao;

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
    public int save(Book book) {
        int res = 0;
        if (book.getId() == null) {
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("name", book.getName());

            KeyHolder keyHolder = new GeneratedKeyHolder();
            res = template.update(INSERT_BOOK, parameters, keyHolder, new String[]{"ID"});
            book.setId(keyHolder.getKey().longValue());
            book.getAuthors().stream().forEach(authorDao::save);
        }
        return res;
    }

    @Override
    public int delete(Book book) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("id", book.getId());
        template.update(DELETE_LINK, parameters);

        parameters = new HashMap<String, Object>();
        parameters.put("id", book.getId());
        return template.update(DELETE_BOOK, parameters);

    }

    @Override
    public Book findById(Long id) {

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("id", id);
        return (Book) template.query(SELECT_BY_ID_BOOK, parameters, new BookRawMapper());

    }

    @Override
    public List<Book> findAll() {
        return (List<Book>) template.query(SELECT_ALL_BOOK, new BookRawMapper());
    }
}
