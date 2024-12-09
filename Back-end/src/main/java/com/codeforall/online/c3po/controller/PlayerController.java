package com.codeforall.online.c3po.controller;

import com.codeforall.online.c3po.command.PlanetDto;
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

/**
 * Rest controller for providing each players information
 */
@CrossOrigin(origins = "*", maxAge = 360)
@RestController
@RequestMapping("/api/player")
public class PlayerController {

    private PlayerService playerService;
    private PlayerToPlayerDto playerToPlayerDto;
    private PlayerDtoToPlayer playerDtoToPlayer;

    /**
     * Handles HTTP GET requests to retrieve a list of all players
     *
     * @return A {@link ResponseEntity} containing a list of {@link PlayerDto} objects and an HTTP status code:
     *      - '200 OK' if the player list is successfully retrieved and converted.
     *      - '404 Not Found' if there is an issue with retrieving player data.
     */
    @RequestMapping(method = RequestMethod.GET, path = {"/", ""})
    public ResponseEntity<List<PlayerDto>> listPlayers() {
        try {

            List<Player> players = playerService.listAllPlayers();

            return new ResponseEntity<>(playerToPlayerDto.convert(players), HttpStatus.OK);

        } catch (PersistenceException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Handles HTTP GET requests to retrieve a specific player
     *
     * @return A {@link ResponseEntity} containing a data transfer object {@link PlayerDto}  and an HTTP status code:
     *      - '200 OK' if the player is successfully retrieved and converted.
     *      - '404 Not Found' if there is an issue with retrieving that specific player.
     */
    @RequestMapping(method = RequestMethod.GET, path = {"/{username}"})
    public ResponseEntity<PlayerDto> getPlayerByUsername(@PathVariable String username) {
        try {
            Player player = playerService.getPlayer(username);

            return new ResponseEntity<>(playerToPlayerDto.convert(player), HttpStatus.OK);

        } catch (PlayerNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Handles HTTP POST requests to add a new player
     *
     * @param playerDto the planet to be added
     * @param bindingResult contains validation results for the `playerDto` object
     * @param uriComponentsBuilder build the URI for the newly created player
     * @return a {@link ResponseEntity} indicating the result of the operation:
     *         - `201 Created` if the player is successfully added
     *         - `400 Bad Request` if the input data is invalid or conversion fails
     *         - `409 Conflict` if a player with the same username already exists
     */
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

    /**
     * Handles HTTP PUT requests to update a player
     *
     * @param playerDto the information of the player to update
     * @param bindingResult contains validation results for the `playerDto` object
     * @return a {@link ResponseEntity} indicating the result of the operation:
     *         - '200 OK' if the player is successfully updated
     *         - `400 Bad Request` if the input data is invalid or conversion fails
     */
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

    /**
     * Handles HTTP DELETE request to delete a player
     *
     * @param username the username of the player to be deleted
     * @return a {@link ResponseEntity} indicating the result of the operation:
     *         - `204 No Content` the request was successfully processed, nothing more to add
     *         - `400 Bad Request` if the input data is invalid or conversion fails
     */
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

    /**
     * Set the player service
     * @param playerService the player service to set
     */
    @Autowired
    public void setPlayerService(PlayerService playerService) {
        this.playerService = playerService;
    }

    /**
     * Set the player data transfer object
     * @param playerToPlayerDto the player DTO to set
     */
    @Autowired
    public void setPlayerToPlayerDto(PlayerToPlayerDto playerToPlayerDto) {
        this.playerToPlayerDto = playerToPlayerDto;
    }

    /**
     * Set the player data transfer object
     * @param playerDtoToPlayer the player DTO to set
     */
    @Autowired
    public void setPlayerDtoToPlayer(PlayerDtoToPlayer playerDtoToPlayer) {
        this.playerDtoToPlayer = playerDtoToPlayer;
    }
}
