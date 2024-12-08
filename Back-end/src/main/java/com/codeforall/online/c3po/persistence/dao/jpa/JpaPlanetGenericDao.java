package com.codeforall.online.c3po.persistence.dao.jpa;

import com.codeforall.online.c3po.model.Planet;
import com.codeforall.online.c3po.persistence.dao.PlanetDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

@Repository
public class JpaPlanetGenericDao extends JpaGenericDao<Planet> implements PlanetDao {

    /**
     * Initialize a new JPA DAO instance given a planet model
     */
    public JpaPlanetGenericDao() {
        super(Planet.class);
    }

    /**
     * Retrieves a planet from the database according to its name
     * @param name the name of the planet to be retrieved
     * @return the planet with the specified name
     */
    public Planet findByName(String name) {
        EntityManager em = sm.getCurrentSession();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Planet> criteriaQuery = cb.createQuery(Planet.class);
        Root<Planet> root = criteriaQuery.from(Planet.class);
        criteriaQuery.select(root);
        criteriaQuery.where(cb.equal(root.get("name"), name.toUpperCase()));

        return em.createQuery(criteriaQuery).getSingleResult();
    }
}
