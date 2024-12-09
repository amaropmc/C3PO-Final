package com.codeforall.online.c3po.services;

import com.codeforall.online.c3po.exceptions.AnswerNotFoundException;
import com.codeforall.online.c3po.model.Answer;

import java.util.List;

/**
 * Common interface for answer services, provides methods to manage answers
 */
public interface AnswerService {

    /**
     * Retrieves an answer by its id
     * @param answerId
     * @return
     * @throws AnswerNotFoundException
     */
    Answer getAnswerById(long answerId) throws AnswerNotFoundException;

    /**
     * Retrieves a list of all registered answers
     * @return list of answers
     */
    List<Answer> getAllAnswers();
}
