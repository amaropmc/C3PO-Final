package com.codeforall.online.c3po.services;


import com.codeforall.online.c3po.exceptions.InvalidScoreException;
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
     * @see PlayerService#getPlayer(String)
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
     * @see PlayerService#registerPlayer(String, Integer)
     */
    @Override
    public Player registerPlayer(String username, Integer score) {

        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }

        if (score == null || score < 0) {
            throw new IllegalArgumentException("Score cannot be null or negative");
        }

        player = new Player();
        player.setUsername(username);
        player.setTotalScore(score);

        try {
            transactionManager.beginWrite();

            playerDao.saveOrUpdate(player);

            transactionManager.commit();

        } catch (PersistenceException e) {
            transactionManager.rollBack();
        }

        return player;
    }

    /*
    /**
     * @see PlayerService#authenticate(String)

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
     */

    /**
     * @see PlayerService#getTotalScore(String)
     */
    @Override
    public int getTotalScore(String username) throws PlayerNotFoundException {
        player = getPlayer(username);

        return player.getTotalScore() != null ? player.getTotalScore() : 0;
    }

    /**
     * @see PlayerService#updatePlayerScore(String, Integer)
     */
    @Override
    public boolean updatePlayerScore(String username, Integer newScore) throws PlayerNotFoundException, InvalidScoreException {
        player = getPlayer(username);
        player.setTotalScore(newScore);

        try {
            transactionManager.beginWrite();

            playerDao.saveOrUpdate(player);

            transactionManager.commit();

        } catch (PersistenceException e) {
            transactionManager.rollBack();
            return false;
        }

        return true;
    }

    /**
     * @see PlayerService#deletePlayer(String)
     */
    @Override
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

    /**
     * Set the player data acess object
     * @param playerDao the player DAO to set
     */
    @Autowired
    public void setPlayerDao(PlayerDao playerDao) {
        this.playerDao = playerDao;
    }

    /**
     * Set the transaction manager
     * @param transactionManager the transaction manager to set
     */
    @Autowired
    public void setTransactionManager(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }
}