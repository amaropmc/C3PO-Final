package com.codeforall.online.c3po.persistence.managers;

import jakarta.persistence.EntityManager;

/**
 * Common interface for a Session Manager
 * @param <T>
 */
public interface SessionManager<T> {

    /**
     * Start a session
     */
    void startSession();

    /**
     * Stop a session
     */
    void endSession();

    /**
     * Get the curent session
     * @return
     */
    EntityManager getCurrentSession();
}