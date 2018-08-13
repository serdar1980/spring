package ru.serdar1980.dao;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.serdar1980.domain.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
public class AuthorJPADaoImpl implements IAuthorDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public int saveDao(Author author) {
        if (author.getId() == null) {
            em.persist(author);
        } else {
            em.merge(author);
        }
        em.flush();
        return 1;
    }

    @Override
    @Transactional
    public int deleteDao(Author author) {
        em.remove(em.contains(author) ? author : em.merge(author));
        return 1;
    }

    @Override
    public Author findByIdDao(Long id) {
        Author author = null;
        try {
            TypedQuery<Author> query =
                    em.createQuery("SELECT a FROM Author a join fetch a.books  where a.id=:id", Author.class)
                            .setParameter("id", id);
            author = query.getSingleResult();
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
        return author;
    }

    @Override
    public List<Author> findAllDao() {
        TypedQuery<Author> query = em.createQuery(
                "SELECT a FROM Author a ", Author.class);
        return query.getResultList();
    }
}
