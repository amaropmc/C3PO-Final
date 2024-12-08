package com.codeforall.online.c3po.controller;

import com.codeforall.online.c3po.command.PlanetDto;
import com.codeforall.online.c3po.converters.PlanetToPlanetDto;
import com.codeforall.online.c3po.exceptions.PlanetNotFoundException;
import com.codeforall.online.c3po.model.Planet;
import com.codeforall.online.c3po.services.PlanetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    /**
     * Handles HTTP GET requests to retrieve a list of all planets
     *
     * @return A {@link ResponseEntity} containing a list of {@link PlanetDto} objects and an HTTP status code:
     * - HTTP 200 OK if the planet list is successfully retrieved and converted.
     * - HTTP 404 Not Found if there is an issue with retrieving planet data.
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
     * - HTTP 200 OK if the planet is successfully retrieved and converted.
     * - HTTP 404 Not Found if there is an issue with retrieving that specific planet.
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
}
