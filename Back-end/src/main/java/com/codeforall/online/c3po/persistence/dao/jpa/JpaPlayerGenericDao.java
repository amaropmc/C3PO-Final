package com.codeforall.online.c3po.persistence.dao.jpa;

import com.codeforall.online.c3po.model.Player;
import com.codeforall.online.c3po.persistence.dao.PlayerDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

@Repository
public class JpaPlayerGenericDao extends JpaGenericDao<Player> implements PlayerDao {

    /**
     * Initialize a new JPA Dao instance given a player model
     */

    public JpaPlayerGenericDao() {
        super(Player.class);
    }

    public Player findByUsername(String username) {
        EntityManager em = sm.getCurrentSession();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Player> criteriaQuery = cb.createQuery(Player.class);
        Root<Player> root = criteriaQuery.from(Player.class);
        criteriaQuery.select(root);
        criteriaQuery.where(cb.equal(root.get("username"), username));

        return em.createQuery(criteriaQuery).getSingleResult();
    }
}