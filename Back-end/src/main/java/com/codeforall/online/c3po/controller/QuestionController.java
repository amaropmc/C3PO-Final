package com.codeforall.online.c3po.controller;

import com.codeforall.online.c3po.command.QuestionDto;
import com.codeforall.online.c3po.converters.QuestionToQuestionDto;
import com.codeforall.online.c3po.exceptions.PlanetNotFoundException;
import com.codeforall.online.c3po.exceptions.QuestionNotFoundException;
import com.codeforall.online.c3po.model.Planet;
import com.codeforall.online.c3po.model.Question;
import com.codeforall.online.c3po.services.PlanetService;
import com.codeforall.online.c3po.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
/**
 * Rest controller for providing each planets quiz
 */
@CrossOrigin(origins = "*", maxAge = 360)
@RestController
@RequestMapping("/api/planet")
public class QuestionController {

    private PlanetService planetService;
    private QuestionToQuestionDto questionToQuestionDto;
    private QuestionService questionService;

    /**
     * Handles HTTP GET requests to retrieve a list of all planets' questions
     *
     * @return A {@link ResponseEntity} containing a list of {@link QuestionDto} objects and an HTTP status code:
     * - HTTP 200 OK if the planets' questions is successfully retrieved and converted.
     * - HTTP 404 Not Found if there is an issue with retrieving planets' questions.
     */
    @RequestMapping(method = RequestMethod.GET, path = {"/{planetName}/quiz"})
    public ResponseEntity<List<QuestionDto>> getPlanetQuiz(@PathVariable String planetName) {

        try {
            Planet planet = planetService.getPlanetByName(planetName);

            List<Question> planetQuestions = getPlanetQuestions(planet.getId());

            return new ResponseEntity<>(questionToQuestionDto.convert(planetQuestions), HttpStatus.OK);

        } catch (PlanetNotFoundException | QuestionNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Utilitary method to extract each planet's questions
     *
     * @param planetId the id of the planet
     * @return a list of questions
     * @throws PlanetNotFoundException
     * @throws QuestionNotFoundException
     */
    public List<Question> getPlanetQuestions(Long planetId) throws PlanetNotFoundException, QuestionNotFoundException {

        List<Long> questionsIds = planetService.getQuestionsIds(planetId)
                .stream().toList();

        List<Question> questions = new ArrayList<>();

        for(Long questionId : questionsIds) {
            questions.add(questionService.getQuestionById(questionId));
        }

        return questions;
    }

    /**
     * Set the planet service
     *
     * @param planetService the planet service to set
     */
    @Autowired
    public void setPlanetService(PlanetService planetService) {
        this.planetService = planetService;
    }

    /**
     * Set the question to question DTO
     *
     * @param questionToQuestionDto the question to question DTO to set
     */
    @Autowired
    public void setQuestionToQuizDto(QuestionToQuestionDto questionToQuestionDto) {
        this.questionToQuestionDto = questionToQuestionDto;
    }

    /**
     * Set the question service
     *
     * @param questionService the question service to set
     */
    @Autowired
    public void setQuestionService(QuestionService questionService) {
        this.questionService = questionService;
    }
}
