package com.codeforall.online.c3po.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Class that represents a player
 */
@Entity
@Table(name = "players")
public class Player extends AbstractModel{
    @Column(unique = true, nullable = false)
    private String username;
    private Integer totalScore;

    /**
     * Get the player ysername
     * @return the player username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the player ysername
     * @param username the player username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get the player total score
     * @return the player total score
     */
    public Integer getTotalScore() {
        return totalScore;
    }

    /**
     * Set the player total score
     * @param totalScore the player total score to set
     */
    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }
}
