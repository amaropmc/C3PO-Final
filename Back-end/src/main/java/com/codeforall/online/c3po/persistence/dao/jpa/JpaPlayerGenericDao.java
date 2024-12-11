package com.codeforall.online.c3po.persistence.dao.jpa;

import com.codeforall.online.c3po.exceptions.PlayerNotFoundException;
import com.codeforall.online.c3po.model.Player;
import com.codeforall.online.c3po.persistence.dao.PlayerDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JpaPlayerGenericDao extends JpaGenericDao<Player> implements PlayerDao {

    /**
     * Initialize a new JPA Dao instance given a player model
     */
    public JpaPlayerGenericDao() {
        super(Player.class);
    }

    @Override
    public List<Player> findAll() {
        EntityManager em = sm.getCurrentSession();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Player> criteriaQuery = cb.createQuery(Player.class);
        Root<Player> root = criteriaQuery.from(Player.class);
        criteriaQuery.select(root).orderBy(cb.desc(root.get("totalScore")));
        return em.createQuery(criteriaQuery).getResultList();
    }


    /**
     * Retrieves a player form the database by its username
     *
     * @param username the username of the player
     * @return the player with that specific username
     * @throws PlayerNotFoundException
     */
    public Player findByUsername(String username) throws PlayerNotFoundException {
        EntityManager em = sm.getCurrentSession();

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Player> criteriaQuery = cb.createQuery(Player.class);
            Root<Player> root = criteriaQuery.from(Player.class);
            criteriaQuery.select(root);
            criteriaQuery.where(cb.equal(root.get("username"), username));


            return em.createQuery(criteriaQuery).getSingleResult();

        } catch (NoResultException e) {
            throw new PlayerNotFoundException();
        }
    }
}