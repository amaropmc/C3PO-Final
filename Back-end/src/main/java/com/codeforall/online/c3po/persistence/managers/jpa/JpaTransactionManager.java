package com.codeforall.online.c3po.persistence.managers.jpa;

import com.codeforall.online.c3po.persistence.managers.TransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JpaTransactionManager implements TransactionManager {

    private JpaSessionManager sm;

    /**
     * @see TransactionManager#beginRead()
     */
    @Override
    public void beginRead() {
        sm.startSession();
    }

    /**
     * @see TransactionManager#beginWrite()
     */
    @Override
    public void beginWrite() {
        if(!isTransactionActive()) {
            sm.getCurrentSession().getTransaction().begin();
        }
    }

    /**
     * @see TransactionManager#commit()
     */
    @Override
    public void commit() {
        if(!isTransactionActive()) {
            sm.getCurrentSession().getTransaction().commit();
        }
    }

    /**
     * @see TransactionManager#rollBack()
     */
    @Override
    public void rollBack() {
        if(!isTransactionActive()) {
            sm.getCurrentSession().getTransaction().rollback();
        }
    }

    /**
     * Check if a transaction is active
     * @see TransactionManager#isTransactionActive()
     * @return
     */
    @Override
    public boolean isTransactionActive() {
        return sm.getCurrentSession().getTransaction().isActive();
    }

    /**
     * set the session manager
     * @param sm
     */
    @Autowired
    public void setSm(JpaSessionManager sm) {
        this.sm = sm;
    }
}
