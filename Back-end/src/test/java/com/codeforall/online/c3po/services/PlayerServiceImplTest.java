package com.codeforall.online.c3po.services;

import com.codeforall.online.c3po.exceptions.InvalidScoreException;
import com.codeforall.online.c3po.exceptions.PlayerNotFoundException;
import com.codeforall.online.c3po.model.Player;
import com.codeforall.online.c3po.persistence.dao.PlayerDao;
import com.codeforall.online.c3po.persistence.managers.TransactionManager;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import junitparams.converters.Param;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(JUnitParamsRunner.class)
public class PlayerServiceImplTest {
    // SUT
    private PlayerServiceImpl playerService;

    // DOC
    private TransactionManager transactionManager;
    private PlayerDao playerDao;

    @Before
    public void setUp() {
        playerService = spy(PlayerServiceImpl.class);

        transactionManager = mock(TransactionManager.class);
        playerDao = mock(PlayerDao.class);

        playerService.setTransactionManager(transactionManager);
        playerService.setPlayerDao(playerDao);
    }

    public Player setUpFakePlayer(Long id, String username) {
        return setUpFakePlayer(id, username, 100);
    }

    public Player setUpFakePlayer(Long id, String username, Integer score) {
        Player player = new Player();

        player.setId(id);
        player.setUsername(username);
        player.setTotalScore(score);

        return player;
    }

    public void assertPlayer(Player fakePlayer, Player actualPlayer) {

        if(actualPlayer.getId() != null) {
            assertEquals(fakePlayer.getId(), actualPlayer.getId());
        }

        assertEquals(fakePlayer.getUsername(), actualPlayer.getUsername());
        assertEquals(fakePlayer.getTotalScore(), actualPlayer.getTotalScore());
    }

    @Test
    @Parameters({"1, Pedro"})
    public void testGetPlayer(Long id, String username) throws PlayerNotFoundException {
        Player fakePlayer = setUpFakePlayer(id, username);
        when(playerDao.findByUsername(username)).thenReturn(fakePlayer);

        Player resultPlayer = playerService.getPlayer(username);

        verify(playerDao).findByUsername(username);
        assertEquals(fakePlayer, resultPlayer);
    }

    @Test
    @Parameters({"Pedro"})
    public void throwsPlayerNotFoundIfPlayerNotPersisted(String username) throws PlayerNotFoundException {
        when(playerDao.findByUsername(username)).thenReturn(null);

        assertThrows(PlayerNotFoundException.class, () ->{
            playerService.getPlayer(username);
        });

        verify(playerDao).findByUsername(username);
    }

    @Test
    @Parameters({"1, Pedro, 2, Patrícia"})
    public void testListAllPlayers(Long fakePlayer1Id, String fakePlayer1Username,
                                  Long fakePlayer2Id, String fakePlayer2Username) {

        Player fakePlayer1 = setUpFakePlayer(fakePlayer1Id, fakePlayer1Username);
        Player fakePlayer2 = setUpFakePlayer(fakePlayer2Id, fakePlayer2Username);
        List<Player> fakePlayerList = Arrays.asList(fakePlayer1, fakePlayer2);
        when(playerDao.findAll()).thenReturn(fakePlayerList);

        List<Player> result = playerService.listAllPlayers();

        verify(playerDao).findAll();
        assertEquals(2, result.size());
        assertEquals(fakePlayer1, result.get(0));
        assertEquals(fakePlayer2, result.get(1));
        assertEquals(fakePlayerList, result);
    }

    @Test
    public void shouldReturnAnEmptyListOfPlayers() {
        when(playerDao.findAll()).thenReturn(Collections.EMPTY_LIST);

        List<Player> result = playerService.listAllPlayers();

        verify(playerDao).findAll();
        assertTrue(result.isEmpty());
    }

    @Test
    @Parameters({"1, Pedro, 50"})
    public void shouldCorrectlyAddANewPlayer(Long id, String username, Integer score) {
        Player fakePlayer = setUpFakePlayer(id, username, score);
        when(playerDao.saveOrUpdate(any(Player.class))).thenReturn(fakePlayer);

        Player savedPlayer = playerService.registerPlayer(username, score);

        assertPlayer(fakePlayer, savedPlayer);

        verify(transactionManager).beginWrite();
        verify(playerDao).saveOrUpdate(any(Player.class));
        verify(transactionManager).commit();
        verify(transactionManager, never()).rollBack();
    }

    @Test
    @Parameters({"1, 50"})
    public void shouldThrowIllegalArgumentExceptionIfUsernameIsNull(Long id, Integer score) {
        String username = null;
        Player fakePlayer = setUpFakePlayer(id, username, score);

        assertThrows(IllegalArgumentException.class, () -> {
            playerService.registerPlayer(username, score);
        });
    }

    @Test
    @Parameters({"1, 50"})
    public void shouldThrowIllegalArgumentExceptionIfUsernameIsEmpty(Long id, Integer score) {
        String username = "";
        Player fakePlayer = setUpFakePlayer(id, username, score);

        assertThrows(IllegalArgumentException.class, () -> {
            playerService.registerPlayer(username, score);
        });
    }

    @Test
    @Parameters({"1, Pedro, 50"})
    public void shouldCorrectlyReturnThePlayerScoreIfPositive(Long id, String username, Integer score) throws PlayerNotFoundException {
        Player fakePlayer = setUpFakePlayer(id, username, score);
        when(playerDao.findByUsername(username)).thenReturn(fakePlayer);

        Integer actualScore = playerService.getTotalScore(username);

        assertEquals(score, actualScore);
    }

    @Test
    @Parameters({"1, Pedro"})
    public void shouldCorrectlyReturnThePlayerScoreIfNull(Long id, String username) throws PlayerNotFoundException {
        Integer score = null;
        Player fakePlayer = setUpFakePlayer(id, username, score);
        when(playerDao.findByUsername(username)).thenReturn(fakePlayer);

        Integer actualScore = playerService.getTotalScore(username);

        assertEquals(Integer.valueOf(0), actualScore);
    }

    @Test
    @Parameters({"1, Pedro, 50"})
    public void throwsPlayerNotFoundOnGetTotalScoreIfPlayerNotPersisted(Long id, String username, Integer score) throws PlayerNotFoundException {
        when(playerDao.findByUsername(username)).thenReturn(null);

        assertThrows(PlayerNotFoundException.class, () -> {
            playerService.getTotalScore(username);
        });
    }

    @Test
    @Parameters({"1, Pedro, 50, 100"})
    public void shouldCorrectlyUpdatePlayerScore(Long id, String username, Integer score, Integer newScore) throws PlayerNotFoundException, InvalidScoreException {
        Player fakePlayer = setUpFakePlayer(id, username, score);
        Player fakePlayerWithNewScore = setUpFakePlayer(id, username, newScore);

        when(playerDao.findByUsername(username)).thenReturn(fakePlayer);
        when(playerDao.saveOrUpdate(fakePlayer)).thenReturn(fakePlayerWithNewScore);

        assertTrue(playerService.updatePlayerScore(username, newScore));

        verify(playerDao).findByUsername(username);
        verify(transactionManager).beginWrite();
        verify(playerDao).saveOrUpdate(fakePlayerWithNewScore);
        verify(transactionManager).commit();
        verify(transactionManager, never()).rollBack();
    }

    @Test
    @Parameters({"1, Pedro, 50"})
    public void throwsPlayerNotFoundOnUpdateScoreIfPlayerNotPersisted(Long id, String username, Integer score) throws PlayerNotFoundException {
        when(playerDao.findByUsername(username)).thenReturn(null);

        assertThrows(PlayerNotFoundException.class, () -> {
            playerService.updatePlayerScore(username, score);
        });
    }

    @Test
    @Parameters({"1, Pedro"})
    public void shouldCorrectlyDeleteAPlayer(Long id, String username) throws PlayerNotFoundException {
        Player fakePlayer = setUpFakePlayer(id, username);
        when(playerDao.findByUsername(username)).thenReturn(fakePlayer);

        playerService.deletePlayer(username);

        verify(playerDao).findByUsername(username);
        verify(transactionManager).beginWrite();
        verify(playerDao).delete(id);
        verify(transactionManager).commit();
        verify(transactionManager, never()).rollBack();
    }

    @Test
    @Parameters({"1, Pedro"})
    public void throwsPlayerNotFoundOnDeleteIfPlayerNotPersisted(Long id, String username) throws PlayerNotFoundException {
        Player fakePlayer = setUpFakePlayer(id, username);
        when(playerDao.findByUsername(username)).thenReturn(null);

        assertThrows(PlayerNotFoundException.class, () -> {
            playerService.deletePlayer(username);
        });

        verify(playerDao).findByUsername(username);
        verify(transactionManager, never()).beginWrite();
        verify(playerDao, never()).delete(id);
        verify(transactionManager, never()).commit();
        verify(transactionManager, never()).rollBack();
    }
}
