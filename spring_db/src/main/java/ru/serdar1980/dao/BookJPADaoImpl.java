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
    public int saveDao(Book book) {
        if (book.getId() == null) {
            em.persist(book);
        } else {
            em.merge(book);
        }
        return 1;
    }

    @Override
    @Transactional
    public int deleteDao(Book book) {
        em.remove(em.contains(book) ? book : em.merge(book));
        return 1;
    }

    @Override
    public Book findByIdDao(Long id) {
        Book book = null;
        try {
            TypedQuery<Book> query =
                    em.createQuery("SELECT b FROM Book b join fetch b.authors where b.id=:id", Book.class)
                            .setParameter("id", id);
            book = query.getSingleResult();
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
        return book;
    }

    @Override
    public List<Book> findAllDao() {
        TypedQuery<Book> query = em.createQuery(
                "SELECT b FROM Book b", Book.class);
        return query.getResultList();
    }
}
