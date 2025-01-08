package com.codeforall.online.c3po.services;

import com.codeforall.online.c3po.exceptions.AnswerNotFoundException;
import com.codeforall.online.c3po.exceptions.QuestionNotFoundException;
import com.codeforall.online.c3po.model.Answer;
import com.codeforall.online.c3po.model.Question;

import java.util.List;
import java.util.Set;

/**
 * Common interface for question services, provides methods to manage planets
 */
public interface QuestionService {

    /**
     * Retrieves a question by its id
     * @param questionId the question id
     * @return the question
     * @throws QuestionNotFoundException
     */
    Question getQuestionById(Long questionId) throws QuestionNotFoundException;

    /**
     * Retrieves a list of all the registered questions
     * @return a list of questions
     */
    List<Question> getAllQuestions();

    /**
     * Retrieves all questions' answers' id
     * @param questionId the question id
     * @return a set of answers' id
     * @throws QuestionNotFoundException
     */
    Set<Long> getAnswersIds(Long questionId) throws QuestionNotFoundException;

    /**
     * Adds an answer to a specified question
     * @param questionId the id of the question
     * @param answer the answer to add
     * @return the added answer
     * @throws QuestionNotFoundException
     * @throws AnswerNotFoundException
     */
    Answer addAnswer(Long questionId, Answer answer) throws QuestionNotFoundException, AnswerNotFoundException;

    /**
     * Removes an answer from a question
     * @param questionId the question id
     * @param answer the answer to be removed
     * @throws QuestionNotFoundException
     * @throws AnswerNotFoundException
     */
    void removeAnswer(Long questionId, Answer answer) throws QuestionNotFoundException, AnswerNotFoundException;
}
