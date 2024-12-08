package com.codeforall.online.c3po.persistence.managers.jpa;

import com.codeforall.online.c3po.persistence.managers.SessionManager;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import org.springframework.stereotype.Component;

@Component
public class JpaSessionManager implements SessionManager<EntityManager> {

    private EntityManagerFactory emf;
    private EntityManager em;

    /**
     * Start a session
     */
    @Override
    public void startSession() {
        if(em == null) {
            em = emf.createEntityManager();
        }
    }

    /**
     * Stop a session
     */
    @Override
    public void endSession() {
        if(em != null) {
            em.close();
        }

        em = null;
    }

    /**
     * Get the curent session
     *
     * @return the current session
     */
    @Override
    public EntityManager getCurrentSession() {
        startSession();
        return em;
    }

    /**
     * Set the entity manager factory
     * @param emf the entity manager factory to set
     */
    @PersistenceUnit
    public void setEmf(EntityManagerFactory emf) {
        this.emf = emf;
    }
}
