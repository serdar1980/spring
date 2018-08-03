package ru.serdar1980.dao;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.serdar1980.domain.Book;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
public class BookJPADaoImpl implements IBookDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public int save(Book book) {
        if (book.getId() == null) {
            em.persist(book);
        } else {
            em.merge(book);
        }
        return 1;
    }

    @Override
    public int delete(Book book) {
        em.clear();
        return 1;
    }

    @Override
    public Book findById(Long id) {
        TypedQuery<Book> query =
                em.createQuery("SELECT b FROM Book b where b.id=:id", Book.class);
        return query.getSingleResult();
    }

    @Override
    public List<Book> findAll() {
        TypedQuery<Book> query = em.createQuery(
                "SELECT b FROM Book b", Book.class);
        return query.getResultList();
    }
}
