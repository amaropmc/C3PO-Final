package com.codeforall.online.c3po.services;

import com.codeforall.online.c3po.exceptions.PlanetNotFoundException;
import com.codeforall.online.c3po.exceptions.QuestionNotFoundException;
import com.codeforall.online.c3po.model.Planet;
import com.codeforall.online.c3po.model.Question;

import java.util.List;
import java.util.Set;

/**
 * Common interface for planet services, provides methods to manage planets
 */
public interface PlanetService {

    /**
     * Retrieves a planet by its id
     * @param id the planet id
     * @return the planet
     * @throws PlanetNotFoundException
     */
    Planet getPlanetById(Long id) throws PlanetNotFoundException;

    /**
     * Retrieves a planet by its name
     * @param planetName the planet name
     * @return the planet
     * @throws PlanetNotFoundException
     */
    Planet getPlanetByName(String planetName) throws PlanetNotFoundException;

    /**
     * Retrieves a list of all the registered planets
     * @return a list of planets
     * @throws PlanetNotFoundException
     */
    List<Planet> list() throws PlanetNotFoundException;

    /**
     * Add a new planet
     * @param planet the planet to add
     * @return the added planet
     */
    Planet add(Planet planet);

    /**
     * Removes a planet
     * @param planetId the id of the planet to be removed
     */
    void remove(Long planetId);

    /**
     * Retrieves all the planet's questions' Ids
     * @param planetId the planet id
     * @return a set of questions' id
     * @throws PlanetNotFoundException
     */
    Set<Long> getQuestionsIds(Long planetId) throws PlanetNotFoundException;

    /**
     * Adds a question to one planet
     * @param planetId the planet id
     * @param question the question to add
     * @return the added question
     * @throws PlanetNotFoundException
     */
    Question addQuestion(Long planetId, Question question) throws PlanetNotFoundException;

    /**
     * Removes a question from a planet
     * @param planetId the planet id
     * @param questionId the id of the question to be removed
     * @throws PlanetNotFoundException
     * @throws QuestionNotFoundException
     */
    void removeQuestion(Long planetId, Long questionId) throws PlanetNotFoundException, QuestionNotFoundException;
}
