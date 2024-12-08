package com.codeforall.online.c3po.model;

/**
 * Common interface for a model, provides methods to get and set ids
 */
public interface Model {

    /**
     * Gets the model id
     * @return the model id
     */
    long getId();

    /**
     * Sets the model id
     * @param id the id to set
     */
    void setId(long id);
}