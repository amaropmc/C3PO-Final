package com.codeforall.online.c3po.services;

import com.codeforall.online.c3po.exceptions.AnswerNotFoundException;
import com.codeforall.online.c3po.model.Answer;
import com.codeforall.online.c3po.persistence.dao.AnswerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnswerServiceImpl  implements AnswerService {

    private AnswerDao answerDao;

    /**
     * Retrieves an answer by its id
     *
     * @param answerId
     * @return
     * @throws AnswerNotFoundException
     */
    @Override
    public Answer getAnswerById(long answerId) throws AnswerNotFoundException {
        return Optional.ofNullable(answerDao.findById(answerId))
                .orElseThrow(AnswerNotFoundException::new);
    }

    /**
     * Retrieves a list of all registered answers
     *
     * @return list of answers
     */
    @Override
    public List<Answer> getAllAnswers() {
        return answerDao.findAll();
    }

    @Autowired
    public void setAnswerDao(AnswerDao answerDao) {
        this.answerDao = answerDao;
    }
}
