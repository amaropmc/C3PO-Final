package com.codeforall.online.c3po.converters;

import com.codeforall.online.c3po.command.PlayerDto;
import com.codeforall.online.c3po.model.Player;
import org.springframework.stereotype.Component;

@Component
public class PlayerToPlayerDto extends AbstractConverter<Player, PlayerDto> {

    @Override
    public PlayerDto convert(Player player) {
        PlayerDto playerDto = new PlayerDto();

        playerDto.setId(player.getId());
        playerDto.setUsername(player.getUsername());
        playerDto.setScore(player.getTotalScore());

        return playerDto;
    }
}
