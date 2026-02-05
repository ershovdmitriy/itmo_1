package org.example.web3.dao;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.example.web3.entities.ResultEntity;

import java.util.List;

@Stateless
public class ResultRepository {

    @PersistenceContext(unitName = "default")
    private EntityManager entityManager;

    public void save(ResultEntity result) {
        entityManager.persist(result);
    }

    public List<ResultEntity> findAll() {
        TypedQuery<ResultEntity> query = entityManager.createQuery(
                "SELECT r FROM ResultEntity r ORDER BY r.id DESC", ResultEntity.class
        );
        return query.getResultList();
    }

    public void clear() {
        entityManager.createQuery("DELETE FROM ResultEntity").executeUpdate();
    }

    public Long count() {
        return entityManager.createQuery("SELECT COUNT(r) FROM ResultEntity r", Long.class)
                .getSingleResult();
    }
}