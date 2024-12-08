package com.codeforall.online.c3po.persistence.managers;


/**
 * Interface for a transaction manager
 */
public interface TransactionManager {

    /**
     * Begin a session to perform a read operation
     */
    void beginRead();

    /**
     * Begin a session to perform a write operation
     */
    void beginWrite();

    /**
     * Commit a transaction
     */
    void commit();

    /**
     * Rollback a transaction
     */
    void rollBack();

    /**
     * Check if a transaction is active
     * @return
     */
    boolean isTransactionActive();

}