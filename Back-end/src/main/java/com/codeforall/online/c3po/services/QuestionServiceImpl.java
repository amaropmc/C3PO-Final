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
     * @see QuestionService#getQuestionById(Long)
     */
    @Override
    public Question getQuestionById(Long questionId) throws QuestionNotFoundException{
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
     * @see QuestionService#getAnswersIds(Long)
     */
    @Override
    public Set<Long> getAnswersIds(Long questionId) throws QuestionNotFoundException {
        Question question = getQuestionById(questionId);

        return question.getAnswers().stream()
                .map(Answer::getId)
                .collect(Collectors.toSet());
    }

    /**
     * @see QuestionService#addAnswer(Long, Answer)
     */
    @Override
    public Answer addAnswer(Long questionId, Answer answer) throws QuestionNotFoundException {
        Answer addedAnswer = null;

        Question question = getQuestionById(questionId);

        try {
            transactionManager.beginWrite();

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
     * @see QuestionService#removeAnswer(Long, Long)
     */
    @Override
    public void removeAnswer(Long questionId, Answer answer) throws QuestionNotFoundException {
        Question question = getQuestionById(questionId);

        try {
            transactionManager.beginWrite();

            question.removeAnswer(answer); //This also sets the answer's question to null
            questionDao.saveOrUpdate(question);
            answerDao.delete(answer.getId());

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
