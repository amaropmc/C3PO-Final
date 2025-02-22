package com.codeforall.online.c3po.services;

import com.codeforall.online.c3po.exceptions.PlanetAlreadyExistsException;
import com.codeforall.online.c3po.exceptions.PlanetNotFoundException;
import com.codeforall.online.c3po.exceptions.PlayerNotFoundException;
import com.codeforall.online.c3po.exceptions.QuestionNotFoundException;
import com.codeforall.online.c3po.model.Planet;
import com.codeforall.online.c3po.model.Question;
import com.codeforall.online.c3po.persistence.dao.PlanetDao;
import com.codeforall.online.c3po.persistence.dao.QuestionDao;
import com.codeforall.online.c3po.persistence.managers.TransactionManager;
import jakarta.persistence.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A {@link PlanetService} implementation
 */
@Service
public class PlanetServiceImpl implements PlanetService {

    private TransactionManager transactionManager;
    private PlanetDao planetDao;
    private QuestionDao questionDao;

    /**
     * @see PlanetService#getPlanetById(Long id)
     */
    @Override
    public Planet getPlanetById(Long id) throws PlanetNotFoundException {
        return Optional.ofNullable(planetDao.findById(id)).orElseThrow(PlanetNotFoundException::new);
    }


    public Planet getPlanetByName(String name) throws PlanetNotFoundException {
        return Optional.ofNullable(planetDao.findByName(name)).orElseThrow(PlanetNotFoundException::new);
    }

    /**
     * @see PlanetService#list()
     */
    @Override
    public List<Planet> list() {
        return planetDao.findAll();
    }

    /**
     * @see PlanetService#add(Planet)
     */
    @Override
    public Planet add(Planet planet) {
        Planet savedPlanet = null;

        if (planet == null) {
            throw new IllegalArgumentException("Planet cannot be null");
        }

        try {
            transactionManager.beginWrite();

            savedPlanet = planetDao.saveOrUpdate(planet);

            transactionManager.commit();

        } catch (PersistenceException e) {
            transactionManager.rollBack();
        }

        return savedPlanet;
    }

    /**
     * @see PlanetService#remove(Long)
     */
    @Override
    public void remove(Long planetId) throws PlanetNotFoundException {
        
        Planet planet = getPlanetById(planetId);

        try {
            transactionManager.beginWrite();

            planetDao.delete(planetId);

            transactionManager.commit();

        } catch (PersistenceException e) {
            transactionManager.rollBack();
        }
    }

    /**
     * @see PlanetService#addQuestion(Long, Question)
     */
    @Override
    public Question addQuestion(Long planetId, Question question) throws PlanetNotFoundException {
        Question addedQuestion = null;

        Planet planet = getPlanetById(planetId);

        if(planet == null) {
            throw new PlanetNotFoundException();
        }

        try {
            transactionManager.beginWrite();

            planet.addQuestion(question); //This will also set this question planet property to this planet

            planetDao.saveOrUpdate(planet);
            addedQuestion = questionDao.saveOrUpdate(question);

            transactionManager.commit();

        } catch (PersistenceException e) {
            transactionManager.rollBack();
        }

        return addedQuestion;
    }

    /**
     * @see PlanetService#removeQuestion(Long, Long)
     */
    @Override
    public void removeQuestion(Long planetId, Long questionId) throws PlanetNotFoundException, QuestionNotFoundException {

        Planet planet = getPlanetById(planetId);

        Question questionToRemove = questionDao.findById(questionId);
        if (questionToRemove == null) {
            throw new QuestionNotFoundException();
        }

        try {
            transactionManager.beginWrite();

            planet.removeQuestion(questionToRemove); //This will also set the question's planet to null
            planetDao.saveOrUpdate(planet);
            questionDao.delete(questionId);

            transactionManager.commit();

        } catch(PersistenceException e) {
            transactionManager.rollBack();
        }
    }

    /**
     * @see PlanetService#getQuestionsIds(Long)
     */
    @Override
    public Set<Long> getQuestionsIds(Long planetId) throws PlanetNotFoundException {
        Planet planet = getPlanetById(planetId);

        return planet.getQuestions().stream()
                .map(Question::getId)
                .collect(Collectors.toSet());
    }

    /**
     * Set the transaction manager
     * @param transactionManager the transaction manager to set
     */
    @Autowired
    public void setTransactionManager(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    /**
     * Set the planet data access object
     * @param planetDao the planet DAO to set
     */
    @Autowired
    public void setPlanetDao(PlanetDao planetDao) {
        this.planetDao = planetDao;
    }

    /**
     * Set the question data access object
     * @param questionDao the question DAO to set
     */
    @Autowired
    public void setQuestionDao(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }
}
