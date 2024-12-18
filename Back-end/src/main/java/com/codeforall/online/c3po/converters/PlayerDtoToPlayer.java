package com.codeforall.online.c3po.converters;

import com.codeforall.online.c3po.command.PlanetDto;
import com.codeforall.online.c3po.command.PlayerDto;
import com.codeforall.online.c3po.exceptions.PlayerNotFoundException;
import com.codeforall.online.c3po.model.Player;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * A concrete converter class that transforms an {@link PlanetDto} entity into an {@link Player}.
 */
@Component
public class PlayerDtoToPlayer {

    /**
     * @see AbstractConverter#convert(List)
     */
    public Player convert(PlayerDto playerDto) throws PlayerNotFoundException {

        Player player = new Player();

        player.setUsername(playerDto.getUsername());
        player.setTotalScore(playerDto.getScore());

        return player;

    }
}
