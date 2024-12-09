package com.codeforall.online.c3po.controller;

import com.codeforall.online.c3po.command.PlayerDto;
import com.codeforall.online.c3po.converters.PlayerDtoToPlayer;
import com.codeforall.online.c3po.converters.PlayerToPlayerDto;
import com.codeforall.online.c3po.exceptions.PlayerAlreadyExistsException;
import com.codeforall.online.c3po.exceptions.PlayerNotFoundException;
import com.codeforall.online.c3po.model.Player;
import com.codeforall.online.c3po.services.PlayerService;
import jakarta.persistence.PersistenceException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 360)
@RestController
@RequestMapping("/api/player")
public class PlayerController {

    private PlayerService playerService;
    private PlayerToPlayerDto playerToPlayerDto;
    private PlayerDtoToPlayer playerDtoToPlayer;

    @RequestMapping(method = RequestMethod.GET, path = {"/", ""})
    public ResponseEntity<List<PlayerDto>> listPlayers() {
        try {

            List<Player> players = playerService.listAllPlayers();

            return new ResponseEntity<>(playerToPlayerDto.convert(players), HttpStatus.OK);

        } catch (PersistenceException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = {"/{username}"})
    public ResponseEntity<PlayerDto> getPlayerByUsername(@PathVariable String username) {
        try {
            Player player = playerService.getPlayer(username);

            return new ResponseEntity<>(playerToPlayerDto.convert(player), HttpStatus.OK);

        } catch (PlayerNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.POST, path = {"/add"})
    public ResponseEntity<?> addPlayer(@Valid @RequestBody PlayerDto playerDto, BindingResult bindingResult,
                                       UriComponentsBuilder uriComponentsBuilder) {

        Player savedPlayer = null;

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            Player player =  playerDtoToPlayer.convert(playerDto);

            if(player == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            try {
                Player playerExists = playerService.getPlayer(player.getUsername());

                if(playerExists != null) {
                    throw new PlayerAlreadyExistsException();
                }

            } catch (PlayerNotFoundException e) {
                savedPlayer = playerService.registerPlayer(player.getUsername(), player.getTotalScore());
            }

            UriComponents uriComponents = uriComponentsBuilder
                    .path("/api/player/" + savedPlayer.getUsername()).build();

            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(uriComponents.toUri());

            return new ResponseEntity<>(headers, HttpStatus.CREATED);

        } catch (PlayerNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } catch (PlayerAlreadyExistsException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(method = RequestMethod.PUT, path = {"/{username}/edit"})
    public ResponseEntity<?> editPlayer(@Valid @RequestBody PlayerDto playerDto, BindingResult bindingResult, @PathVariable String username) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (playerDto.getId() != null || !playerDto.getUsername().equals(username)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            Player player = playerDtoToPlayer.convert(playerDto);

            playerService.updatePlayerScore(player.getUsername(), player.getTotalScore());

            return new ResponseEntity<>(HttpStatus.OK);

        } catch (PlayerNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, path = {"{username}/delete"})
    public ResponseEntity<?> deletePlayer(@PathVariable String username) throws PlayerNotFoundException {

        Player player = playerService.getPlayer(username);

        if (player == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        try {
            playerService.deletePlayer(username);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (PlayerNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Autowired
    public void setPlayerService(PlayerService playerService) {
        this.playerService = playerService;
    }

    @Autowired
    public void setPlayerToPlayerDto(PlayerToPlayerDto playerToPlayerDto) {
        this.playerToPlayerDto = playerToPlayerDto;
    }

    @Autowired
    public void setPlayerDtoToPlayer(PlayerDtoToPlayer playerDtoToPlayer) {
        this.playerDtoToPlayer = playerDtoToPlayer;
    }
}
