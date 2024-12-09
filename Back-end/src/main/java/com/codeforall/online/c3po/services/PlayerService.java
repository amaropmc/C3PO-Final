package com.codeforall.online.c3po.services;

import com.codeforall.online.c3po.exceptions.PlayerNotFoundException;
import com.codeforall.online.c3po.model.Player;

import java.util.List;

/**
 * Common interface for player services, provides methods to manage players
 */
public interface PlayerService {

    /**
     * Get the player by it's username
     *
     * @param username the username of the player
     * @return
     */
    Player getPlayer(String username) throws PlayerNotFoundException;

    /**
     * Returns a list of users
     *
     * @return a list of users
     */
    List<Player> listAllPlayers();

    /**
     * Register a new player
     *
     * @param username the username of the Player
     * @return the added player
     */
    Player registerPlayer(String username, Integer score);

    /**
     * login?
     * @param username
     * @return
     */
    boolean authenticate(String username);

    /**
     * Update the user's score
     *
     * @param username the player's username
     * @param newScore the new player's score
     * @return
     */
    boolean updatePlayerScore(String username, int newScore) throws PlayerNotFoundException;

    /**
     * Get the player score
     *
     * @param username the player's username
     * @return
     */
    int getTotalScore(String username) throws PlayerNotFoundException;

    /**
     * Removes a player from the database
     *
     * @param username the username to be removed
     * @throws PlayerNotFoundException
     */
    void deletePlayer(String username) throws PlayerNotFoundException;

}
