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
    public int save(Author author) {
        if (author.getId() == null) {
            em.persist(author);
        } else {
            em.merge(author);
        }
        return 1;
    }

    @Override
    public int delete(Author author) {
        em.clear();
        return 1;
    }

    @Override
    public Author findById(Long id) {
        TypedQuery<Author> query =
                em.createQuery("SELECT a FROM Author a where a.id=:id", Author.class);
        return query.getSingleResult();
    }

    @Override
    public List<Author> findAll() {
        TypedQuery<Author> query = em.createQuery(
                "SELECT a FROM Author a ", Author.class);
        return query.getResultList();
    }
}
