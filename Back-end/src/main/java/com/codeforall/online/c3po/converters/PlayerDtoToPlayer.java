package com.codeforall.online.c3po.converters;

import com.codeforall.online.c3po.command.PlayerDto;
import com.codeforall.online.c3po.exceptions.PlayerNotFoundException;
import com.codeforall.online.c3po.model.Player;
import com.codeforall.online.c3po.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PlayerDtoToPlayer {

    private PlayerService playerService;


    public Player convert(PlayerDto playerDto) throws PlayerNotFoundException {

        Player player;

        if (playerDto.getUsername() != null) {
            player = playerService.getPlayer(playerDto.getUsername());
        } else {
            player = new Player();
        }

        player.setUsername(playerDto.getUsername());
        player.setTotalScore(playerDto.getScore());

        return player;

    }

    @Autowired
    public void setPlayerService(PlayerService playerService) {
        this.playerService = playerService;
    }

}
