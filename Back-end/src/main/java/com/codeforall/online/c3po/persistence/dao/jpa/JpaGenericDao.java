package com.codeforall.online.c3po.persistence.dao.jpa;

import com.codeforall.online.c3po.model.Model;
import com.codeforall.online.c3po.persistence.dao.Dao;
import com.codeforall.online.c3po.persistence.managers.jpa.JpaSessionManager;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * A generic jpa data access object to be used as a base for concrete jpa service implementations
 * @param <T> the model type
 * @see Dao
 */
@Component
public abstract class JpaGenericDao<T extends Model> implements Dao<T> {

    protected JpaSessionManager sm;
    protected Class<T> modelType;

    /**
     * Initialize a new JPA DAO instance given a model type
     * @param modelType the model type
     */
    public JpaGenericDao(Class<T> modelType) {
        this.modelType = modelType;
    }

    /**
     * @see Dao#findAll()
     */
    @Override
    public List<T> findAll() {
        EntityManager em = sm.getCurrentSession();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = cb.createQuery(modelType);
        Root<T> root = criteriaQuery.from(modelType);
        criteriaQuery.select(root).orderBy(cb.asc(root.get("id")));
        return em.createQuery(criteriaQuery).getResultList();
    }

    /**
     * @see Dao#findById(Long)
     */
    @Override
    public T findById(Long id) {
        EntityManager em = sm.getCurrentSession();

        return em.find(modelType, id);
    }

    /**
     * @see Dao#saveOrUpdate(Model)
     */
    @Override
    public T saveOrUpdate(T modelObject) {
        EntityManager em = sm.getCurrentSession();

        return em.merge(modelObject);
    }

    /**
     * @see Dao#delete(Long)
     */
    @Override
    public void delete(Long id) {
        EntityManager em = sm.getCurrentSession();

        em.remove(em.find(modelType, id));
    }

    /**
     * Set the session manager
     * @param sm the session manager to set
     */
    @Autowired
    public void setSm(JpaSessionManager sm) {
        this.sm = sm;
    }

}