package com.codeforall.online.c3po.services;

import com.codeforall.online.c3po.exceptions.QuestionNotFoundException;
import com.codeforall.online.c3po.model.Answer;
import com.codeforall.online.c3po.model.Question;
import com.codeforall.online.c3po.persistence.dao.AnswerDao;
import com.codeforall.online.c3po.persistence.dao.QuestionDao;
import com.codeforall.online.c3po.persistence.managers.TransactionManager;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

@RunWith(JUnitParamsRunner.class)
public class QuestionServiceImplTest {
    //SUT
    private QuestionServiceImpl questionService;

    //DOC
    private TransactionManager transactionManager;
    private QuestionDao questionDao;
    private AnswerDao answerDao;

    @Before
    public void setUp() {
        questionService = spy(QuestionServiceImpl.class);

        transactionManager = mock(TransactionManager.class);
        questionDao = mock(QuestionDao.class);
        answerDao = mock(AnswerDao.class);

        questionService.setTransactionManager(transactionManager);
        questionService.setQuestionDao(questionDao);
        questionService.setAnswerDao(answerDao);
    }

    public Question setFakeQuestion(Long questionId) {
        Question fakeQuestion = new Question();
        fakeQuestion.setId(questionId);
        fakeQuestion.setDescription("Is this a question?");
        fakeQuestion.setScore(100);

        return fakeQuestion;
    }

    public Answer setFakeAnswer(Long answerId) {
        Answer fakeAnswer = new Answer();
        fakeAnswer.setId(answerId);
        fakeAnswer.setDescription("This is an answer");
        fakeAnswer.setCorrect(true);

        return fakeAnswer;
    }

    @Test
    @Parameters({"1"})
    public void testGetQuestionById(Long questionId) throws QuestionNotFoundException {
        Question fakeQuestion = setFakeQuestion(questionId);

        when(questionDao.findById(questionId)).thenReturn(fakeQuestion);

        Question resultQuestion = questionService.getQuestionById(questionId);

        assertEquals(fakeQuestion, resultQuestion);
    }

    @Test
    public void throwQuestionNotFoundExceptionOnGetQuestionById() {
        assertThrows(QuestionNotFoundException.class, () -> {
            questionService.getQuestionById(anyLong());
        });
    }

    @Test
    @Parameters({"1, 2"})
    public void testGetAllQuestions(Long question1Id, Long question2Id) throws QuestionNotFoundException {
        Question question1 = setFakeQuestion(question1Id);
        Question question2 = setFakeQuestion(question2Id);

        List<Question> questions = new ArrayList<>();
        questions.add(question1);
        questions.add(question2);

        when(questionDao.findAll()).thenReturn(questions);

        List<Question> actualQuestions = questionService.getAllQuestions();

        assertEquals(questions.size(), actualQuestions.size());
        assertEquals(question1, actualQuestions.get(0));
        assertEquals(question2, actualQuestions.get(1));
    }

    @Test
    @Parameters({"1, 1, 2"})
    public void shouldCorrectlyReturnASetOfAnswersIds(Long questionId, Long answer1Id, Long answer2Id) throws QuestionNotFoundException {
        Question fakeQuestion = setFakeQuestion(questionId);
        Answer fakeAnswer1 = setFakeAnswer(answer1Id);
        Answer fakeAnswer2 = setFakeAnswer(answer2Id);

        Set<Answer> questionFakeAnswers = new HashSet<>();
        questionFakeAnswers.add(fakeAnswer1);
        questionFakeAnswers.add(fakeAnswer2);

        Set<Long> questionFakeAnswersIds = questionFakeAnswers.stream()
                .map(Answer::getId)
                .collect(Collectors.toSet());

        fakeQuestion.setAnswers(questionFakeAnswers);

        when(questionDao.findById(questionId)).thenReturn(fakeQuestion);

        Set<Long> actualAnswers = questionService.getAnswersIds(questionId);

        assertEquals(questionFakeAnswersIds.size(), actualAnswers.size());
        assertEquals(questionFakeAnswersIds.size(), actualAnswers.size());
        assertEquals(questionFakeAnswersIds.iterator().next(), actualAnswers.iterator().next());
    }

    @Test
    @Parameters({"1"})
    public void throwsQuestionNotFoundExceptionOnGetAnswersIds(Long questionId) {
        assertThrows(QuestionNotFoundException.class, () -> {
            questionService.getAnswersIds(questionId);
        });
    }

    @Test
    @Parameters({"1, 1"})
    public void shouldCorrectlyAddAnAnswer(Long questionId, Long answerId) throws QuestionNotFoundException {
        Question fakeQuestion = setFakeQuestion(questionId);
        Answer fakeAnswer = setFakeAnswer(answerId);

        when(questionDao.findById(questionId)).thenReturn(fakeQuestion);
        when(answerDao.saveOrUpdate(fakeAnswer)).thenReturn(fakeAnswer);

        Answer resultAnswer = questionService.addAnswer(questionId, fakeAnswer);

        assertEquals(fakeAnswer, resultAnswer);

        verify(questionDao).findById(questionId);
        verify(transactionManager).beginWrite();
        verify(questionDao).saveOrUpdate(fakeQuestion);
        verify(answerDao).saveOrUpdate(fakeAnswer);
        verify(transactionManager).commit();
        verify(transactionManager, never()).rollBack();
    }

    @Test
    public void shouldThrowQuestionNotFoundExceptionOnAddAnswer() {
        assertThrows(QuestionNotFoundException.class, () -> {
            questionService.addAnswer(anyLong(), any(Answer.class));
        });

        verify(questionDao).findById(anyLong());
        verify(transactionManager, never()).beginWrite();
        verify(transactionManager, never()).commit();
        verify(transactionManager, never()).rollBack();
    }

    @Test
    @Parameters({"1, 1"})
    public void shouldCorrectlyRemoveAnAnswer(Long questionId, Long answerId) throws QuestionNotFoundException {
        Question fakeQuestion = setFakeQuestion(questionId);
        Answer fakeAnswer = setFakeAnswer(answerId);

        Set<Answer> questionFakeAnswers = new HashSet<>();
        questionFakeAnswers.add(fakeAnswer);
        fakeQuestion.setAnswers(questionFakeAnswers);

        when(questionDao.findById(questionId)).thenReturn(fakeQuestion);

        questionService.removeAnswer(questionId, fakeAnswer);

        assertEquals(0, questionFakeAnswers.size());

        verify(questionDao).findById(questionId);
        verify(transactionManager).beginWrite();
        verify(questionDao).saveOrUpdate(fakeQuestion);
        verify(answerDao).delete(answerId);
        verify(transactionManager).commit();
        verify(transactionManager, never()).rollBack();
    }

    @Test
    public void shouldThrowQuestionNotFoundExceptionOnRemoveAnswer() {
        assertThrows(QuestionNotFoundException.class, () -> {
            questionService.removeAnswer(anyLong(), any(Answer.class));
        });

        verify(questionDao).findById(anyLong());
        verify(transactionManager, never()).beginWrite();
        verify(transactionManager, never()).commit();
        verify(transactionManager, never()).rollBack();
    }

}
