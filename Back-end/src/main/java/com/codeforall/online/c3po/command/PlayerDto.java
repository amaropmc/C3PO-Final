package com.codeforall.online.c3po.command;

import com.codeforall.online.c3po.model.Player;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * A class that represents the data transfer object to {@link Player}
 */
public class PlayerDto {
    private Long id;
    @NotNull
    @NotBlank
    @Size(min = 3, max = 64)
    private String username;
    private Integer score;

    /**
     * Get the player id
     * @return the player id
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the player id
     * @param id the player id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the player username
     * @return the player username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the player username
     * @param username the player username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get the player score
     * @return the player score
     */
    public Integer getScore() {
        return score;
    }

    /**
     * Set the player score
     * @param score the score to set
     */
    public void setScore(Integer score) {
        this.score = score;
    }
}