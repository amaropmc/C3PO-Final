package com.codeforall.online.c3po.persistence.dao;

import com.codeforall.online.c3po.exceptions.PlayerNotFoundException;
import com.codeforall.online.c3po.model.Player;

public interface PlayerDao extends Dao<Player> {

    Player findByUsername(String username) throws PlayerNotFoundException;
}
