package com.codeforall.online.c3po.controller;

import com.codeforall.online.c3po.command.PlanetDto;
import com.codeforall.online.c3po.converters.PlanetDtoToPlanet;
import com.codeforall.online.c3po.converters.PlanetToPlanetDto;
import com.codeforall.online.c3po.exceptions.PlanetAlreadyExistsException;
import com.codeforall.online.c3po.exceptions.PlanetNotFoundException;
import com.codeforall.online.c3po.model.Planet;
import com.codeforall.online.c3po.services.PlanetService;
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
 * Rest controller for providing each planets information
 */
@CrossOrigin(origins = "*", maxAge = 360)
@RestController
@RequestMapping("/api/planet")
public class PlanetController {

    private PlanetService planetService;
    private PlanetToPlanetDto planetToPlanetDto;
    private PlanetDtoToPlanet planetDtoToPlanet;

    /**
     * Handles HTTP GET requests to retrieve a list of all planets
     *
     * @return A {@link ResponseEntity} containing a list of {@link PlanetDto} objects and an HTTP status code:
     *      - '200 OK' if the planet list is successfully retrieved and converted.
     *      - '404 Not Found' if there is an issue with retrieving planet data.
     */
    @RequestMapping(method = RequestMethod.GET, path = {"","/"})
    public ResponseEntity<List<PlanetDto>> listPlanets() {

        try{
            List<Planet> planets = planetService.list();

            return new ResponseEntity<>(planetToPlanetDto.convert(planets), HttpStatus.OK);

        } catch (PlanetNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Handles HTTP GET requests to retrieve a specific planet
     *
     * @return A {@link ResponseEntity} containing a data transfer object {@link PlanetDto}  and an HTTP status code:
     *      - '200 OK' if the planet is successfully retrieved and converted.
     *      - '404 Not Found' if there is an issue with retrieving that specific planet.
     */
    @RequestMapping(method = RequestMethod.GET, path = {"/{planetName}"})
    public ResponseEntity<PlanetDto> getPlanet(@PathVariable String planetName) {

        try {
            Planet planet = planetService.getPlanetByName(planetName);

            return new ResponseEntity<>(planetToPlanetDto.convert(planet), HttpStatus.OK);

        } catch (PlanetNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Handles HTTP POST requests to add a new planet
     *
     * @param planetDto the planet to be added
     * @param bindingResult contains validation results for the `planetDto` object
     * @param uriComponentsBuilder build the URI for the newly created planet
     * @return a {@link ResponseEntity} indicating the result of the operation:
     *         - `201 Created` if the planet is successfully added
     *         - `400 Bad Request` if the input data is invalid or conversion fails
     *         - `409 Conflict` if a planet with the same name already exists
     */
    @RequestMapping(method = RequestMethod.POST, path = {"/add"})
    public ResponseEntity<?> addPlanet(@Valid @RequestBody PlanetDto planetDto, BindingResult bindingResult,
                                               UriComponentsBuilder uriComponentsBuilder) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            Planet planet = planetDtoToPlanet.convert(planetDto);

            if (planet == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            Planet savedPlanet = null;

            try {
                Planet planetExists = planetService.getPlanetByName(planet.getName());

                if (planetExists != null) {
                    throw new PlanetAlreadyExistsException();
                }

            } catch (PlanetNotFoundException e) {
                savedPlanet = planetService.add(planet);
            }
            UriComponents uriComponents = uriComponentsBuilder
                    .path("/api/planet/" + savedPlanet.getName()).build();

            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(uriComponents.toUri());

            return new ResponseEntity<>(headers, HttpStatus.CREATED);

        } catch (PlanetAlreadyExistsException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    /**
     * Handles HTTP PUT requests to update a planet
     *
     * @param planetDto the information of the planet to update
     * @param bindingResult contains validation results for the `planetDto` object
     * @return a {@link ResponseEntity} indicating the result of the operation:
     *         - '200 OK' if the planet is successfully updated
     *         - `400 Bad Request` if the input data is invalid or conversion fails
     */
    @RequestMapping(method = RequestMethod.PUT, path = {"/{planetName}/edit"})
    public ResponseEntity<?> editPlanet(@Valid @RequestBody PlanetDto planetDto, BindingResult bindingResult,
                                        @PathVariable String planetName) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (planetDto.getId() != null || !planetDto.getName().equals(planetName)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            Planet planet = planetDtoToPlanet.convert(planetDto);

            Planet updatedPlanet = planetService.getPlanetByName(planet.getName());

            planet = updatedPlanet;
            planetService.add(planet);

            return new ResponseEntity<>(HttpStatus.OK);

        } catch (PlanetNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Handles HTTP DELETE request to delete a planet
     *
     * @param planetName the name of the planet to be deleted
     * @return a {@link ResponseEntity} indicating the result of the operation:
     *         - `204 No Content` the request was successfully processed, nothing more to add
     *         - `400 Bad Request` if the input data is invalid or conversion fails
     */
    @RequestMapping(method = RequestMethod.DELETE, path = {"{planetName}/delete"})
    public ResponseEntity<?> deletePlanet(@PathVariable String planetName) {

        try {
            Planet planet = planetService.getPlanetByName(planetName);

            if (planet == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            planetService.remove(planet.getId());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (PlanetNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Set the planet service
     * @param planetService the planet service to set
     */
    @Autowired
    public void setPlanetService(PlanetService planetService) {
        this.planetService = planetService;
    }

    /**
     * Set the planet data transfer object
     * @param planetDto the planet DTO to set
     */
    @Autowired
    public void setPlanetToPlanetDto(PlanetToPlanetDto planetDto) {
        this.planetToPlanetDto = planetDto;
    }

    /**
     * Set the planet data transfer object
     * @param planetDtoToPlanet the planet DTO to set
     */
    @Autowired
    public void setPlanetDtoToPlanet(PlanetDtoToPlanet planetDtoToPlanet) {
        this.planetDtoToPlanet = planetDtoToPlanet;
    }
}
