package com.codeforall.online.c3po.services;


import com.codeforall.online.c3po.exceptions.PlayerNotFoundException;
import com.codeforall.online.c3po.model.Player;
import com.codeforall.online.c3po.persistence.dao.PlayerDao;
import com.codeforall.online.c3po.persistence.managers.TransactionManager;
import jakarta.persistence.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * A {@link PlayerService}implementation
 */
@Service
public class PlayerServiceImpl implements PlayerService {

    private TransactionManager transactionManager;
    private PlayerDao playerDao;
    private Player player;

    /**
     * Get the user by username
     *
     * @param username
     * @return
     */
    @Override
    public Player getPlayer(String username) throws PlayerNotFoundException {
        player = playerDao.findByUsername(username);

        if (player == null) {
            throw new PlayerNotFoundException();
        }

        return player;
    }

    public List<Player> listAllPlayers() {

        return playerDao.findAll();
    }

    /**
     * register the user with username
     *
     * @param username
     * @return
     */
    @Override
    public Player registerPlayer(String username, Integer score) {

        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }

        try {
            transactionManager.beginWrite();

            player = new Player();

            player.setUsername(username);
            player.setTotalScore(score);

            playerDao.saveOrUpdate(player);

            transactionManager.commit();

        } catch (PersistenceException e) {
            transactionManager.rollBack();
        }

        return player;
    }

    /**
     * login?
     *
     * @param username
     * @return
     */
    @Override
    public boolean authenticate(String username) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }

        try {

            transactionManager.beginWrite();

            Player player = playerDao.findByUsername(username);

            if (player == null) {
                transactionManager.rollBack();
                System.out.println("Username not found");
                return false;
            }

            transactionManager.commit();
            return true;
        } catch (Exception e) {
            transactionManager.rollBack();
            return false;
        }
    }

    /**
     * @param username
     * @return
     */
    @Override
    public int getTotalScore(String username) throws PlayerNotFoundException {
        Player player = playerDao.findByUsername(username);

        if(player == null) {
            throw new PlayerNotFoundException();
        }

        return player.getTotalScore() != null ? player.getTotalScore() : 0;
    }

    /**
     * update the user's score
     *
     * @param username
     * @param newScore
     * @return
     */
    @Override
    public boolean updatePlayerScore(String username, int newScore) throws PlayerNotFoundException {
        player = playerDao.findByUsername(username);

        if(player == null) {
            throw new PlayerNotFoundException();
        }

        try {
            transactionManager.beginWrite();

            if(newScore > (player.getTotalScore() != null ? player.getTotalScore() : 0)) {
                player.setTotalScore(newScore);
                playerDao.saveOrUpdate(player);
            }

            transactionManager.commit();

        } catch (PersistenceException e) {
            transactionManager.rollBack();
            return false;
        }

        return true;
    }

    public void deletePlayer(String username) throws PlayerNotFoundException {
        player = playerDao.findByUsername(username);

        if(player == null) {
            throw new PlayerNotFoundException();
        }

        try {
            transactionManager.beginWrite();

            playerDao.delete(player.getId());

            transactionManager.commit();
        } catch (PersistenceException e) {
            transactionManager.rollBack();
        }
    }

    @Autowired
    public void setPlayerDao(PlayerDao playerDao) {
        this.playerDao = playerDao;
    }

    @Autowired
    public void setTransactionManager(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }
}