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
import ru.serdar1980.mapper.AuthorRowMapper;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AuthorJDBCDaoImpl implements IAuthorDao {

    private static final String SELECT_BY_ID_AUTHOR = "select * from tbl_author a left join tbl_book b on (a.id= b.id) where author_id = :id  ";
    private static final String SELECT_ALL_AUTHOR = "select * from tbl_author a left join tbl_book b on (a.id= b.id)";
    private static final String INSERT_AUTHOR = "insert into tbl_author (fio) values (:fio)";
    private static final String DELETE_AUTHOR = "delete from tbl_author where id =:id";
    private static final String UPDATE_AUTHOR = "update tbl_author set fio = :fio";
    private static final String DELETE_LINK = "delete from book_author where author_id = :id ";

    private DataSource dataSource;
    private JdbcOperations operations;
    private NamedParameterJdbcTemplate template;
    private IBookDao bookDao;

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
    public int save(Author author) {
        int res = 0;
        if (author.getId() == null) {
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("fio", author.getFio());
            KeyHolder keyHolder = new GeneratedKeyHolder();
            res = template.update(INSERT_AUTHOR, parameters, keyHolder, new String[]{"ID"});
            author.setId(keyHolder.getKey().longValue());
            author.getBooks().stream().forEach(bookDao::save);
        }
        return res;
    }

    @Override
    public int delete(Author author) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("id", author.getId());
        template.update(DELETE_LINK, parameters);

        parameters = new HashMap<String, Object>();
        parameters.put("id", author.getId());
        return template.update(DELETE_AUTHOR, parameters);

    }

    @Override
    public Author findById(Long id) {

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("id", id);
        return (Author) template.query(SELECT_BY_ID_AUTHOR, parameters, new AuthorRowMapper());

    }

    @Override
    public List<Author> findAll() {
        return (List<Author>) template.query(SELECT_ALL_AUTHOR, new AuthorRowMapper());
    }
}
