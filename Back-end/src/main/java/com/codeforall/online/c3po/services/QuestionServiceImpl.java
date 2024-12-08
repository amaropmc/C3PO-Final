package com.codeforall.online.c3po.services;

import com.codeforall.online.c3po.exceptions.AnswerNotFoundException;
import com.codeforall.online.c3po.exceptions.QuestionNotFoundException;
import com.codeforall.online.c3po.model.Answer;
import com.codeforall.online.c3po.model.Question;
import com.codeforall.online.c3po.persistence.dao.AnswerDao;
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
 * A {@link QuestionService} implementation
 */
@Service
public class QuestionServiceImpl implements QuestionService {

    private TransactionManager transactionManager;
    private QuestionDao questionDao;
    private AnswerDao answerDao;

    /**
     * @see QuestionService#getQuestionById(long)
     */
    @Override
    public Question getQuestionById(long questionId) throws QuestionNotFoundException{
        return Optional.ofNullable(questionDao.findById(questionId)).orElseThrow(QuestionNotFoundException::new);
    }

    /**
     * @see QuestionService#getAllQuestions()
     */
    @Override
    public List<Question> getAllQuestions() {
        return questionDao.findAll();
    }

    /**
     * @see QuestionService#getAnswersIds(long)
     */
    @Override
    public Set<Long> getAnswersIds(long questionId) throws QuestionNotFoundException {
        Question question = getQuestionById(questionId);

        return question.getAnswers().stream()
                .map(Answer::getId)
                .collect(Collectors.toSet());
    }

    /**
     * @see QuestionService#addAnswer(long, Answer)
     */
    @Override
    public Answer addAnswer(long questionId, Answer answer) throws QuestionNotFoundException {
        Answer addedAnswer = null;

        try {
            transactionManager.beginWrite();

            Question question = getQuestionById(questionId);

            question.addAnswer(answer); // This will also set this answer question property to this question

            questionDao.saveOrUpdate(question);
            addedAnswer = answerDao.saveOrUpdate(answer);

            transactionManager.commit();

        } catch (PersistenceException e) {
            transactionManager.rollBack();
        }

        return addedAnswer;
    }

    /**
     * @see QuestionService#removeAnswer(long, long)
     */
    @Override
    public void removeAnswer(long questionId, long answerId) throws QuestionNotFoundException, AnswerNotFoundException {

        try {
            transactionManager.beginWrite();

            Question question = getQuestionById(questionId);

            Answer answerToRemove = answerDao.findById(answerId);
            if(answerToRemove == null) {
                throw new AnswerNotFoundException();
            }

            question.removeAnswer(answerToRemove); //This also sets the answer's question to null
            questionDao.saveOrUpdate(question);
            answerDao.delete(answerId);

            transactionManager.commit();

        } catch(PersistenceException e) {
            transactionManager.rollBack();
        }
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
     * Set the question data access object
     * @param questionDao the question DAO to set
     */
    @Autowired
    public void setQuestionDao(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    /**
     * Set the answer data access object
     * @param answerDao the answer DAO to set
     */
    @Autowired
    public void setAnswerDao(AnswerDao answerDao) {
        this.answerDao = answerDao;
    }
}
